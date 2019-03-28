package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.util.JsonUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridRangeInfo;
import com.hypersmart.usercenter.mapper.GridApprovalRecordMapper;
import com.hypersmart.usercenter.mapper.GridBasicInfoMapper;
import com.hypersmart.usercenter.model.GridApprovalRecord;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.K2Result;
import com.hypersmart.usercenter.service.GridApprovalRecordService;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryService;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.GridRangeService;
import com.hypersmart.usercenter.util.GridOperateEnum;
import com.hypersmart.usercenter.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service("gridApprovalRecordServiceImpl")
public class GridApprovalRecordServiceImpl extends GenericService<String, GridApprovalRecord> implements GridApprovalRecordService {

	public GridApprovalRecordServiceImpl(GridApprovalRecordMapper mapper) {
		super(mapper);
	}

	@Resource
	private GridApprovalRecordMapper gridApprovalRecordMapper;

	@Resource
	private GridBasicInfoMapper gridBasicInfoMapper;

	@Autowired
	private GridBasicInfoService gridBasicInfoService;

	@Autowired
	GridRangeService gridRangeService;

	@Autowired
	private GridBasicInfoHistoryService gridBasicInfoHistoryService;

	/**
	 * 流程发起地址
	 */
	@Value("${flow.url}")
	private String flowUrl;

	/**
	 * 流程使用数据库：portal
	 */
	@Value("${flow.db.portal}")
	private String flowDbPortal;

	/**
	 * 网格类型
	 */
	@Value("${flow.const.grid-type}")
	private String flowConstGridType;

	/**
	 * 业态属性类型
	 */
	@Value("${flow.const.format-attribute}")
	private String flowConstFormatAttribute;

	/**
	 * 调用审批流程
	 *
	 * @param approvalType    审批类型
	 * @param gridId          网格id
	 * @param approvalContent 审批内容
	 */
	@Override
	public void callApproval(String approvalType, String gridId, Object approvalContent) {
		GridApprovalRecord record = new GridApprovalRecord();
		try {
			String recordId = UUID.randomUUID().toString();
			record.setGridId(gridId);
			record.setId(recordId);
			record.setApprovalType(approvalType);
			record.setApprovalStatus(1);
			record.setCreateDate(new Date());
			record.setApprovalContent(JsonUtil.toJson(approvalContent));
			record.setSubmitterId(ContextUtil.getCurrentUserId());
			record.setSubmitterName(ContextUtil.getCurrentUser().getFullname());
			// 数据字典
			List<Map<String, String>> gridTypes = gridApprovalRecordMapper.getSysDicsByTypeKey(flowDbPortal, flowConstGridType);
			List<Map<String, String>> formatAttributeTypes = gridApprovalRecordMapper.getSysDicsByTypeKey(flowDbPortal, flowConstFormatAttribute);

			// 处理上报流程的内容
			String flowContent = "";
			if (approvalType.equals(GridOperateEnum.NEW_GRID.getOperateType())) {
				flowContent = constructingFlowContentForNewGrid(GridOperateEnum.NEW_GRID.getDescription(), approvalContent, gridTypes, formatAttributeTypes);
			}

			// 网格覆盖范围变更
			if (approvalType.equals(GridOperateEnum.CHANGE_SCOPE.getOperateType())) {
				flowContent = constructingFlowContentForChangeScope(approvalContent, gridTypes, formatAttributeTypes);
			}

			// 管家变更
			if (approvalType.equals(GridOperateEnum.CHANGE_HOUSEKEEPER.getOperateType())) {
				flowContent = constructingFlowContentForChangeHousekeeper(approvalContent, gridTypes, formatAttributeTypes);
			}

			// 网格停用
			if (approvalType.equals(GridOperateEnum.DISABLE_GRID.getOperateType())) {
				flowContent = constructingFlowContentForNewGrid(GridOperateEnum.DISABLE_GRID.getDescription(), approvalContent, gridTypes, formatAttributeTypes);
			}

			// 解除网格管家关联
			if (approvalType.equals(GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getOperateType())) {
				flowContent = constructingFlowContentForHousekeeperDisassociated(approvalContent, gridTypes, formatAttributeTypes);
			}
			record.setCallFlowContent(flowContent);

			// 调用K2流程
			HttpClientUtils httpClientUtils = HttpClientUtils.getInstance();
			String resultContent = httpClientUtils.httpPost(flowUrl, flowContent, null);
			JsonNode resultNode = JsonUtil.toJsonNode(resultContent);
			if (null != resultNode) {
				if (!"0".equals(resultNode.get("CODE").asText())) {
					// 调用K2失败
					record.setCallStatus(2);
					record.setCallErrorMessage(resultNode.get("RESULT").asText());
				} else {
					record.setCallStatus(1);
					record.setProcInstId(resultNode.get("PROCINSTID").asText());
				}
			} else {
				record.setCallStatus(2);
				record.setCallErrorMessage("调用K2审批流程失败：未能收到K2任何反馈信息");
			}
		} catch (IOException e) {
			e.printStackTrace();
			record.setCallStatus(2);
			record.setCallErrorMessage(e.getMessage());
		} finally {
			gridApprovalRecordMapper.insert(record);
		}
	}

	/**
	 * 流程结果处理
	 *
	 * @param k2Result
	 */
	@Override
	public void processFlowResult(K2Result k2Result) {
		GridApprovalRecord record = gridApprovalRecordMapper.getGridApprovalRecordByProcInstId(k2Result.getProcInstId());
		try {
			if ("1".equals(k2Result.getResultCode())) {
				// 审批通过
				record.setApprovalStatus(2);
				record.setApprovalDate(new Date());
				GridBasicInfoDTO dto = JsonUtil.toBean(record.getApprovalContent(), GridBasicInfoDTO.class);
				String gridId = record.getGridId();

				// 针对不同的审核数据，做后续处理操作
				if ((GridOperateEnum.CHANGE_SCOPE.getOperateType()).equals(record.getApprovalType())) {
					// 变更覆盖范围>>>记录历史>>>更新数据
					GridBasicInfo grid = gridBasicInfoService.get(gridId);
					gridBasicInfoHistoryService.saveGridBasicInfoHistory(grid, 1);
					grid.setGridRange(dto.getGridRange());
					grid.setUpdateTimes(grid.getUpdateTimes() + 1);
					grid.setUpdationDate(new Date());
					grid.setUpdatedBy(record.getSubmitterId());
					gridBasicInfoService.updateSelective(grid);
					String[] ids = {gridId};
					gridRangeService.deleteRangeByGridIds(ids);
					gridRangeService.recordRange(dto.getGridRange(), gridId);
				}

				if ((GridOperateEnum.CHANGE_HOUSEKEEPER.getOperateType()).equals(record.getApprovalType())) {
					// 变更管家>>>记录历史>>>更新数据
					GridBasicInfo grid = gridBasicInfoService.get(gridId);
					gridBasicInfoHistoryService.saveGridBasicInfoHistory(grid, 0);
					if ("".equals(dto.getHousekeeperId())) {
						grid.setHousekeeperId(null);
					} else {
						grid.setHousekeeperId(dto.getHousekeeperId());
					}
					grid.setUpdateTimes(grid.getUpdateTimes() + 1);
					grid.setUpdationDate(new Date());
					grid.setUpdatedBy(record.getSubmitterId());
					gridBasicInfoService.updateSelective(grid);
				}

				if ((GridOperateEnum.DISABLE_GRID.getOperateType()).equals(record.getApprovalType())) {
					// 网格禁用
					GridBasicInfoBO bo = new GridBasicInfoBO();
					String[] ids = {dto.getId()};
					bo.setIds(ids);
					bo.setUpdatedBy(record.getSubmitterId());
					gridBasicInfoMapper.disableGridInfo(bo);
					gridRangeService.deleteRangeByGridIds(ids);
				}

				if ((GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getOperateType()).equals(record.getApprovalType())) {
					// 管家解除关联网格>>>记录历史>>>更新数据
					String[] ids = dto.getIds();
					List<GridBasicInfo> gridBasicInfoList = gridBasicInfoService.getByIds(ids);
					if (gridBasicInfoList != null && gridBasicInfoList.size() > 0) {
						for (GridBasicInfo grid : gridBasicInfoList) {
							grid.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
							grid.setHousekeeperId(null);
							gridBasicInfoHistoryService.saveGridBasicInfoHistory(grid, 0);
						}
					}
					gridBasicInfoService.updateBatch(gridBasicInfoList);
				}
			} else {
				// 审批不通过
				record.setApprovalStatus(3);
				record.setApprovalOpinion(k2Result.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			record.setCreateDate(null);
			this.updateSelective(record);
		}
	}

	/**
	 * 构建 K2流程消息体（新建网格）
	 *
	 * @param gridDescription
	 * @param approvalContent
	 * @return
	 * @throws IOException
	 */
	private String constructingFlowContentForNewGrid(String gridDescription, Object approvalContent,
	                                                 List<Map<String, String>> gridTypes, List<Map<String, String>> formatAttributeTypes) throws IOException {
		/**
		 * 1.流程信息体
		 * 2.流程信息主体
		 * 3.申请人信息
		 * 5.网格覆盖范围
		 */
		GridBasicInfoDTO gridBasicInfo = (GridBasicInfoDTO) approvalContent;
		Map<String, Object> flowMap = new ConcurrentHashMap(),
				body = new ConcurrentHashMap(),
				detail = new ConcurrentHashMap();
		List<Map<String, Object>> coverageAreaDetails = new ArrayList<>();

		detail.put("TYPE", gridDescription);
		detail.put("GRIDCODE", gridBasicInfo.getGridCode());
		detail.put("GRIDNAME", gridBasicInfo.getGridName());
		detail.put("GRIDTYPE", getDicByType(gridBasicInfo.getGridType(), gridTypes));
		detail.put("FORMATATTRIBUTE", getDicByType(gridBasicInfo.getFormatAttribute(), formatAttributeTypes));
		if (StringUtils.isNotRealEmpty(gridBasicInfo.getHousekeeperId())) {
			detail.put("HOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridBasicInfo.getHousekeeperId()));
		} else {
			detail.put("HOUSEKEEPER", "");
		}
		detail.put("CREATIONTIME", gridBasicInfo.getCreationDate());
		detail.put("FOUNDER", ContextUtil.getCurrentUser().getFullname());
		detail.put("REMARKS", gridBasicInfo.getGridRemark());

		constructingGridRanges(coverageAreaDetails, gridBasicInfo.getGridRange());
		detail.put("COVERAGEAREADETAILS", coverageAreaDetails);
		detail.put("APPLICATIONREASON", gridBasicInfo.getReason());

		body.put("CODE", "SMPT0001");
		body.put("PROPOSERDETAIL", getProposer(gridBasicInfo));
		body.put("DETAIL", detail);
		flowMap.put("DATA", body);

		return JsonUtil.toJson(flowMap);
	}

	/**
	 * 构建 K2流程消息体（网格覆盖范围变更）
	 *
	 * @param approvalContent
	 * @return
	 * @throws IOException
	 */
	private String constructingFlowContentForChangeScope(Object approvalContent, List<Map<String, String>> gridTypes, List<Map<String, String>> formatAttributeTypes) throws IOException {

		/**
		 * 1.流程信息体
		 * 2.流程信息主体
		 * 3.申请人信息
		 * 4.事项信息
		 * 5.网格覆盖范围
		 */
		GridBasicInfoDTO gridBasicInfo = (GridBasicInfoDTO) approvalContent;
		Map<String, Object> flowMap = new ConcurrentHashMap(),
				body = new ConcurrentHashMap(),
				detail = new ConcurrentHashMap();
		List<Map<String, Object>> beforeDetails = new ArrayList<>(), afterDetails = new ArrayList<>();
		detail.put("TYPE", GridOperateEnum.CHANGE_SCOPE.getDescription());
		detail.put("GRIDCODE", gridBasicInfo.getGridCode());
		detail.put("GRIDNAME", gridBasicInfo.getGridName());
		detail.put("GRIDTYPE", getDicByType(gridBasicInfo.getGridType(), gridTypes));
		detail.put("FORMATATTRIBUTE", getDicByType(gridBasicInfo.getFormatAttribute(), formatAttributeTypes));
		if (StringUtils.isNotRealEmpty(gridBasicInfo.getHousekeeperId())) {
			detail.put("HOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridBasicInfo.getHousekeeperId()));
		} else {
			detail.put("HOUSEKEEPER", "");
		}
		detail.put("REMARKS", gridBasicInfo.getGridRemark());

		// 变更后网格覆盖范围
		constructingGridRanges(afterDetails, gridBasicInfo.getGridRange());
		detail.put("AFTERCOVERAGEAREADETAILS", afterDetails);

		// 变更前网格覆盖范围
		GridBasicInfo beforeGridInfo = gridApprovalRecordMapper.getBeforeGridInfo(gridBasicInfo.getId());
		if (null != beforeGridInfo.getGridRange()) {
			constructingGridRanges(beforeDetails, beforeGridInfo.getGridRange());
		}
		detail.put("BEFORECOVERAGEAREADETAILS", beforeDetails);
		detail.put("APPLICATIONREASON", gridBasicInfo.getReason());

		body.put("CODE", "SMPT0002");
		body.put("PROPOSERDETAIL", getProposer(gridBasicInfo));
		body.put("DETAIL", detail);
		flowMap.put("DATA", body);

		return JsonUtil.toJson(flowMap);
	}

	/**
	 * 构建 K2流程消息体（网格管家变更）
	 *
	 * @param approvalContent
	 * @return
	 * @throws IOException
	 */
	private String constructingFlowContentForChangeHousekeeper(Object approvalContent, List<Map<String, String>> gridTypes, List<Map<String, String>> formatAttributeTypes) throws IOException {

		/**
		 * 1.流程信息体
		 * 2.流程信息主体
		 * 3.申请人信息
		 * 4.事项信息
		 * 5.网格覆盖范围
		 */
		GridBasicInfoDTO gridBasicInfoDTO = (GridBasicInfoDTO) approvalContent;
		Map<String, Object> flowMap = new ConcurrentHashMap(),
				body = new ConcurrentHashMap(),
				detail = new ConcurrentHashMap();
		List<Map<String, Object>> details = new ArrayList<>();
		GridBasicInfo gridInfo = gridApprovalRecordMapper.getBeforeGridInfo(gridBasicInfoDTO.getId());
		detail.put("TYPE", GridOperateEnum.CHANGE_HOUSEKEEPER.getDescription());
		detail.put("GRIDCODE", gridInfo.getGridCode());
		detail.put("GRIDNAME", gridInfo.getGridName());
		detail.put("GRIDTYPE", getDicByType(gridInfo.getGridType(), gridTypes));
		detail.put("FORMATATTRIBUTE", getDicByType(gridInfo.getFormatAttribute(), formatAttributeTypes));
		if (StringUtils.isNotRealEmpty(gridInfo.getHousekeeperId())) {
			detail.put("BEFOREHOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridInfo.getHousekeeperId()));
		} else {
			detail.put("BEFOREHOUSEKEEPER", "");
		}

		if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getHousekeeperId())) {
			detail.put("AFTERHOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridBasicInfoDTO.getHousekeeperId()));
		} else {
			detail.put("AFTERHOUSEKEEPER", "");
		}
		detail.put("REMARKS", gridInfo.getGridRemark());

		// 网格范围
		constructingGridRanges(details, gridInfo.getGridRange());
		detail.put("COVERAGEAREADETAILS", details);
		detail.put("APPLICATIONREASON", gridBasicInfoDTO.getReason());

		body.put("CODE", "SMPT0003");
		body.put("PROPOSERDETAIL", getProposer(gridBasicInfoDTO));
		body.put("DETAIL", detail);
		flowMap.put("DATA", body);

		return JsonUtil.toJson(flowMap);
	}

	/**
	 * 构建 K2流程消息体（管家解除关联网格）
	 *
	 * @param approvalContent
	 * @return
	 * @throws IOException
	 */
	private String constructingFlowContentForHousekeeperDisassociated(Object approvalContent, List<Map<String, String>> gridTypes, List<Map<String, String>> formatAttributeTypes) throws IOException {
		GridBasicInfoDTO gridBasicInfoDTO = (GridBasicInfoDTO) approvalContent;

		String[] ids = gridBasicInfoDTO.getIds();
		List<GridBasicInfo> gridBasicInfos = gridApprovalRecordMapper.getGridInfoByIds(ids);
		if (CollectionUtils.isEmpty(gridBasicInfos)) {
			return "";
		}

		/**
		 * 1.流程信息体
		 * 2.流程信息主体
		 * 3.申请人信息
		 * 4.事项信息
		 * 5.网格覆盖范围
		 */
		Map<String, Object> flowMap = new ConcurrentHashMap(),
				body = new ConcurrentHashMap(),
				detail = new ConcurrentHashMap();
		List<Map<String, Object>> removeGridDetails = new ArrayList<>();

		String houseKeeperId = "", orgId = "";
		for (GridBasicInfo gridInfo : gridBasicInfos) {
			houseKeeperId = gridInfo.getHousekeeperId();
			orgId = gridInfo.getStagingId();

			Map<String, Object> gridMap = new ConcurrentHashMap();
			gridMap.put("GRIDCODE", gridInfo.getGridCode());
			gridMap.put("GRIDNAME", gridInfo.getGridName());
			gridMap.put("GRIDTYPE", getDicByType(gridInfo.getGridType(), gridTypes));
			gridMap.put("FORMATATTRIBUTE", getDicByType(gridInfo.getFormatAttribute(), formatAttributeTypes));
			gridMap.put("REMARKS", gridInfo.getGridRemark());

			// 获取网格覆盖范围
			List<Map<String, Object>> areaDetails = new ArrayList<>();
			constructingGridRanges(areaDetails, gridInfo.getGridRange());
			gridMap.put("COVERAGEAREADETAILS", areaDetails);
			removeGridDetails.add(gridMap);
		}

		// TODO 根据管家id，获取管家信息
		//  (ORG_ID_,USER_ID_)uc_org_user(POS_ID_) >>> (POS_ID_)uc_org_post(POST_KEY_)   >>> (POST_KEY_)portal.portal_sys_dic(NAME_)
		Map<String, String> housekeeper = gridApprovalRecordMapper.getHousekeeperInfoById(flowDbPortal, houseKeeperId, orgId);
		if (null != housekeeper) {
			detail.put("NAME", housekeeper.get("FULLNAME_"));
			detail.put("LEVEL", housekeeper.get("NAME_"));
			detail.put("PHONE", housekeeper.get("MOBILE_"));
		} else {
			detail.put("NAME", "");
			detail.put("LEVEL", "");
			detail.put("PHONE", "");
		}
		detail.put("TYPE", GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getDescription());
		detail.put("REMOVEGRIDDETAILS", removeGridDetails);
		detail.put("APPLICATIONREASON", gridBasicInfoDTO.getReason());

		body.put("CODE", "SMPT0005");
		body.put("PROPOSERDETAIL", getProposer(gridBasicInfoDTO));
		body.put("DETAIL", detail);
		flowMap.put("DATA", body);

		return JsonUtil.toJson(flowMap);
	}

	/**
	 * 统一构建网格覆盖范围
	 *
	 * @param areaDetails
	 * @param gridRange
	 */
	private void constructingGridRanges(List<Map<String, Object>> areaDetails, String gridRange) {
		JSONArray jsonArray = JSONArray.parseArray(gridRange);
		List<GridRangeInfo> gridRangeInfos = jsonArray.toJavaList(GridRangeInfo.class);
		if (!CollectionUtils.isEmpty(gridRangeInfos)) {
			// 判断类型，获取对应的楼栋单元房产信息
			List<GridRangeInfo> buidlingList = gridRangeInfos.stream().filter(m -> m.getLevel() == 1).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GridRangeInfo::getId))), ArrayList::new));
			for (GridRangeInfo building : buidlingList) {
				List<GridRangeInfo> unitList = gridRangeInfos.stream().filter(m -> m.getLevel() == 2 && m.getParentId().equals(building.getId())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GridRangeInfo::getId))), ArrayList::new));
				for (GridRangeInfo unit : unitList) {
					List<GridRangeInfo> houseList = gridRangeInfos.stream().filter(m -> m.getLevel() == 3 && m.getParentId().equals(unit.getId())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(GridRangeInfo::getId))), ArrayList::new));
					for (GridRangeInfo house : houseList) {
						Map<String, Object> coverageAreaDetail = new ConcurrentHashMap();
						coverageAreaDetail.put("STORIEDBUILDING", building.getName());
						coverageAreaDetail.put("UNIT", unit.getName());
						coverageAreaDetail.put("HOUSEPROPERTY", house.getName());
						areaDetails.add(coverageAreaDetail);
					}
				}
			}
		}
	}

	/**
	 * 获取申请人信息
	 *
	 * @param proposer
	 * @return
	 */
	private Map<String, Object> getProposer(GridBasicInfoDTO proposer) {
		// TODO 需要传入更多信息（区域id、城区id、项目id、地块id、申请人页面选择信息）
		Map<String, Object> map = new ConcurrentHashMap();
		map.put("NAME", ContextUtil.getCurrentUser().getFullname());
		map.put("DATE", new Date());
		map.put("PLANS", proposer.getPostId());
		map.put("ROLE", proposer.getPostName());
		map.put("REGION", proposer.getAreaName());
		map.put("CITYPROPER", proposer.getCityName());
		map.put("PROJECT", proposer.getProjectName());
		map.put("BLOCK", proposer.getStagingName());
		return map;
	}

	/**
	 * 根据分类，获取对应的字典
	 *
	 * @param typeKey
	 * @param dicList
	 * @return
	 */
	private String getDicByType(String typeKey, List<Map<String, String>> dicList) {
		List<String> formatAttributeList = dicList.stream().filter(g -> g.get("KEY_").equals(typeKey)).map(map -> map.get("NAME_")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(formatAttributeList)) {
			return formatAttributeList.get(0);
		} else {
			return "";
		}
	}
}
