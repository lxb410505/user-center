package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.JsonUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.constant.GridTypeConstants;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridRangeInfo;
import com.hypersmart.usercenter.mapper.GridApprovalRecordMapper;
import com.hypersmart.usercenter.mapper.GridBasicInfoMapper;
import com.hypersmart.usercenter.mapper.StageServiceGirdRefMapper;
import com.hypersmart.usercenter.model.GridApprovalRecord;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.K2Result;
import com.hypersmart.usercenter.service.GridApprovalRecordService;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryService;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.GridRangeService;
import com.hypersmart.usercenter.util.GridOperateEnum;
import com.hypersmart.usercenter.util.GridTypeEnum;
import com.hypersmart.usercenter.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
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

	@Resource
	private StageServiceGirdRefMapper stageServiceGirdRefMapper;

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
	public CommonResult<String> callApproval(String approvalType, String gridId, Object approvalContent) {
		GridApprovalRecord record = new GridApprovalRecord();
		CommonResult<String> commonResult=new CommonResult<>();
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

			//网格新增
			if (approvalType.equals(GridOperateEnum.NEW_GRID.getOperateType())) {
				flowContent = constructingFlowContentForNewGrid("NEW_GRID",GridOperateEnum.NEW_GRID.getDescription(), approvalContent, gridTypes, formatAttributeTypes);
			}

			// 网格覆盖范围变更
			if (approvalType.equals(GridOperateEnum.CHANGE_SCOPE.getOperateType())) {
				flowContent = constructingFlowContentForChangeScope(approvalContent, gridTypes, formatAttributeTypes);
			}

			// 管家变更
			if (approvalType.equals(GridOperateEnum.CHANGE_HOUSEKEEPER.getOperateType())) {
				flowContent = constructingFlowContentForChangeHousekeeper(approvalContent, gridTypes, formatAttributeTypes);
			}

			// 管家绑定网格
			if (approvalType.equals(GridOperateEnum.LINK_HOUSEKEEPER.getOperateType())) {
				flowContent = constructingFlowContentForLinkHousekeeper(approvalContent, gridTypes, formatAttributeTypes);
			}

			// 网格停用
			if (approvalType.equals(GridOperateEnum.DISABLE_GRID.getOperateType())) {
				flowContent = constructingFlowContentForNewGrid("DISABLE_GRID",GridOperateEnum.DISABLE_GRID.getDescription(), approvalContent, gridTypes, formatAttributeTypes);
			}

			// 解除网格管家关联
			if (approvalType.equals(GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getOperateType())) {
				flowContent = constructingFlowContentForHousekeeperDisassociated(approvalContent, gridTypes, formatAttributeTypes);
			}
			record.setCallFlowContent(flowContent);

			// TODO 调用K2流程
			HttpClientUtils httpClientUtils = HttpClientUtils.getInstance();
			String resultContent = httpClientUtils.httpPost(flowUrl, flowContent, null);
			JsonNode resultNode = JsonUtil.toJsonNode(resultContent);
			if (null != resultNode) {
				String code = "";
				if (resultNode.get("code") != null) {
					code = resultNode.get("code").asText();
				}else {
					code = resultNode.get("CODE").asText();
				}
				if (!"0".equals(code)) {
					// 调用K2失败
					record.setCallStatus(2);
					String message = "";
					if (resultNode.get("message") != null) {
						message = resultNode.get("message").asText();
					}else {
						message = resultNode.get("RESULT").asText();
					}
					record.setCallErrorMessage(message);
					commonResult.setMessage(message);
					commonResult.setState(false);
					if ((GridOperateEnum.NEW_GRID.getOperateType()).equals(record.getApprovalType())) {
						GridBasicInfoDTO gridBasicInfoDTO=(GridBasicInfoDTO)approvalContent;
						if(gridBasicInfoDTO!=null){
							gridBasicInfoService.realDeleteGridList(gridBasicInfoDTO.getId(),gridBasicInfoDTO.getGridType());
						}
					}
				} else {
					String procinstid = "";
					if (resultNode.get("procinstid") != null) {
						procinstid = resultNode.get("procinstid").asText();
					}else {
						procinstid = resultNode.get("PROCINSTID").asText();
					}
					record.setCallStatus(1);
					record.setProcInstId(procinstid);
					commonResult.setMessage("成功！");
					commonResult.setState(true);
				}
			} else {
				record.setCallStatus(2);
				record.setCallErrorMessage("调用K2审批流程失败：未能收到K2任何反馈信息");
				commonResult.setMessage("调用K2审批流程失败：未能收到K2任何反馈信息");
				commonResult.setState(false);
				if ((GridOperateEnum.NEW_GRID.getOperateType()).equals(record.getApprovalType())) {
					GridBasicInfoDTO gridBasicInfoDTO=(GridBasicInfoDTO)approvalContent;
					if(gridBasicInfoDTO!=null){
						gridBasicInfoService.realDeleteGridList(gridBasicInfoDTO.getId(),gridBasicInfoDTO.getGridType());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			record.setCallStatus(2);
			record.setCallErrorMessage(e.getMessage());
			commonResult.setMessage(e.getMessage());
			commonResult.setState(false);
		} finally {
			gridApprovalRecordMapper.insert(record);

			// TODO 待调整（删除此段代码，使用上面代码）
//			record.setCallStatus(1);
//			String procInstId = UUID.randomUUID().toString();
//			record.setProcInstId(procInstId);
//			gridApprovalRecordMapper.insert(record);
//
//			K2Result k2Result = new K2Result();
//			k2Result.setMessage("审批通过");
//			k2Result.setProcInstId(procInstId);
//			k2Result.setResultCode("1");
//			processFlowResult(k2Result);
		}
		return commonResult;
	}

	/**
	 * 流程结果处理
	 *
	 * @param k2Result
	 */
	@Override
	public CommonResult<String> processFlowResult(K2Result k2Result) {
		boolean importState = true;
		String message = "0";
		GridApprovalRecord record = gridApprovalRecordMapper.getGridApprovalRecordByProcInstId(k2Result.getProcInstId());
		GridBasicInfoDTO dto = JsonUtil.toBean(record.getApprovalContent(), GridBasicInfoDTO.class);
		try {
			if ("1".equals(k2Result.getResultCode())) {
				// 审批通过
				record.setApprovalStatus(2);
				record.setApprovalDate(new Date());
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
					int i = gridBasicInfoService.updateSelective(grid);
					if (i > 0) {
						if(GridTypeConstants.BUILDING_GRID.equals(dto.getGridType())){
							gridBasicInfoService.handChangeRange(gridId, dto.getGridRange(), 2);//2 修改
						}
					}
					String[] ids = {gridId};
					gridRangeService.deleteRangeByGridIds(ids);
					gridRangeService.recordRange(dto.getGridRange(), gridId);
				}
				// TODO
				if ((GridOperateEnum.CHANGE_HOUSEKEEPER.getOperateType()).equals(record.getApprovalType()) || (GridOperateEnum.LINK_HOUSEKEEPER.getOperateType()).equals(record.getApprovalType())) {
					// 变更管家>>>记录历史>>>更新数据
					if(GridTypeEnum.SERVICE_CENTER_GRID.getGridType().equals(dto.getGridType())){
						List<GridBasicInfo> gridBasicInfoLists = getGridBasicInfoByServiceGridId(gridId);
						if(!CollectionUtils.isEmpty(gridBasicInfoLists)){
							boolean saveHistory=true;
							for(GridBasicInfo info:gridBasicInfoLists){
								updateHouseKeeperId(info,record.getSubmitterId(),dto,saveHistory);
								saveHistory=false;
							}
						}
					}else{
						GridBasicInfo grid = gridBasicInfoService.get(gridId);
						updateHouseKeeperId(grid,record.getSubmitterId(),dto,true);
					}

				}

				if ((GridOperateEnum.DISABLE_GRID.getOperateType()).equals(record.getApprovalType())) {
					// 网格禁用
					disableGrid(record,dto);
				}

				if ((GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getOperateType()).equals(record.getApprovalType())) {
					// 管家解除关联网格>>>记录历史>>>更新数据
					dto.setHousekeeperId(null);
					if(GridTypeEnum.SERVICE_CENTER_GRID.getGridType().equals(dto.getGridType())){
						List<GridBasicInfo> gridBasicInfoLists = getGridBasicInfoByServiceGridId(gridId);
						if(!CollectionUtils.isEmpty(gridBasicInfoLists)){
							boolean saveHistory=true;
							for (GridBasicInfo info : gridBasicInfoLists) {
								updateHouseKeeperId(info,record.getSubmitterId(),dto,saveHistory);
								saveHistory=false;
							}
						}
					}else{
						String[] ids = dto.getIds();
						List<GridBasicInfo> gridBasicInfoList = gridBasicInfoService.getByIds(ids);
						if (gridBasicInfoList != null && gridBasicInfoList.size() > 0) {
							for (GridBasicInfo grid : gridBasicInfoList) {
								updateHouseKeeperId(grid,record.getSubmitterId(),dto,true);
							}
						}
					}


				}
			} else {
				// 审批不通过
				//新增网格审批驳回
				if ((GridOperateEnum.NEW_GRID.getOperateType()).equals(record.getApprovalType())) {
					disableGrid(record,dto);
				}
				record.setApprovalStatus(3);
				record.setApprovalOpinion(k2Result.getMessage());
				importState = false;
				message = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			record.setCreateDate(null);
			this.updateSelective(record);
		}
		return new CommonResult(importState, message);
	}
	//禁用网格
	private void disableGrid(GridApprovalRecord record,GridBasicInfoDTO dto){
		// 禁用网格
		GridBasicInfoBO bo = new GridBasicInfoBO();
		String[] ids = {dto.getId()};
		bo.setIds(ids);
		bo.setUpdatedBy(record.getSubmitterId());
		//服务中心网格
		if(GridTypeEnum.SERVICE_CENTER_GRID.getGridType().equals(dto.getGridType())){
			stageServiceGirdRefMapper.disableServiceCenterGrid(bo);
			List<GridBasicInfo> gridBasicInfoLists = getGridBasicInfoByServiceGridId(dto.getId());
			if(!CollectionUtils.isEmpty(gridBasicInfoLists)){
				for (GridBasicInfo grid : gridBasicInfoLists) {
					grid.setUpdatedBy(record.getSubmitterId());
					grid.setHousekeeperId(null);
					grid.setGridRemark(null);
				}
				gridBasicInfoService.updateBatch(gridBasicInfoLists);

			}
		}else{
			//楼栋、公区网格
			gridBasicInfoMapper.disableGridInfo(bo);
			gridRangeService.deleteRangeByGridIds(ids);
		}
	}
	//根据服务中心网格id 获取覆盖地块的网格
	private List<GridBasicInfo> getGridBasicInfoByServiceGridId(String gridId){
		List<Map<String, Object>> serviceGridByGridId = stageServiceGirdRefMapper.getServiceGridByGridId(gridId);
		List<GridBasicInfo> gridBasicInfos=new ArrayList<>();
		if(!CollectionUtils.isEmpty(serviceGridByGridId)){
			List<String> stagingIds=new ArrayList<>();
			for(Map<String, Object> serviceGrid:serviceGridByGridId){
				String stagingId = serviceGrid.get("stagingId") == null ? null : String.valueOf(serviceGrid.get("stagingId"));
				stagingIds.add(stagingId);
			}
			QueryFilter queryFilter=QueryFilter.build(GridBasicInfo.class);
			queryFilter.addFilter("grid_type",GridTypeEnum.SERVICE_CENTER_GRID.getGridType(), QueryOP.IN, FieldRelation.AND);
			queryFilter.addFilter("is_deleted",0, QueryOP.EQUAL, FieldRelation.AND);
			queryFilter.addFilter("enabled_flag",1, QueryOP.EQUAL, FieldRelation.AND);
			queryFilter.addFilter("staging_id",stagingIds, QueryOP.IN, FieldRelation.AND);
			PageList<GridBasicInfo> query = gridBasicInfoService.query(queryFilter);
			if(query!=null){
				gridBasicInfos=query.getRows();
			}
		}
		return gridBasicInfos;
	}
	//更新网格管家信息
	private void updateHouseKeeperId(GridBasicInfo grid,String submitterId,GridBasicInfoDTO dto,boolean saveHistory){
		if(saveHistory){
			gridBasicInfoHistoryService.saveGridBasicInfoHistory(grid, 0);
		}
		if ("".equals(dto.getHousekeeperId())) {
			grid.setHousekeeperId(null);
		} else {
			grid.setHousekeeperId(dto.getHousekeeperId());
		}
		grid.setUpdateTimes(grid.getUpdateTimes() + 1);
		grid.setUpdationDate(new Date());
		grid.setUpdatedBy(submitterId);
		gridBasicInfoService.update(grid);
	}
	/**
	 * 构建 K2流程消息体（新建网格）
	 *
	 * @param gridDescription
	 * @param approvalContent
	 * @return
	 * @throws IOException
	 */
	private String constructingFlowContentForNewGrid(String type,String gridDescription, Object approvalContent,
	                                                 List<Map<String, String>> gridTypes, List<Map<String, String>> formatAttributeTypes) throws IOException {
		/**
		 * 1.流程信息体
		 * 2.流程信息主体
		 * 3.申请人信息
		 * 5.网格覆盖范围
		 */
		GridBasicInfoDTO gridBasicInfo = (GridBasicInfoDTO) approvalContent;
		Map<String, Object> flowMap = new HashMap(),
				body = new HashMap();
//		List<Map<String, Object>> coverageAreaDetails = new ArrayList<>();
		Map<String,Object> coverageAreaDetails = new HashMap<>();
		if ("NEW_GRID".equals(type)) {
			body.put("CODE", "SMPT0001");
		}else {
			body.put("CODE", "SMPT0004");
		}
		getProposer(body,gridBasicInfo);
		body.put("TYPE", gridDescription);
		body.put("GRIDCODE", gridBasicInfo.getGridCode());
		body.put("GRIDNAME", gridBasicInfo.getGridName());
		body.put("GRIDTYPE", getDicByType(gridBasicInfo.getGridType(), gridTypes));
		body.put("FORMATATTRIBUTE", getDicByType(gridBasicInfo.getFormatAttribute(), formatAttributeTypes));
		if (StringUtils.isNotRealEmpty(gridBasicInfo.getHousekeeperId())) {
			body.put("HOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridBasicInfo.getHousekeeperId()));
		} else {
			body.put("HOUSEKEEPER", "");
		}
		body.put("REMARKS", gridBasicInfo.getGridRemark());
		body.put("CREATIONTIME", gridBasicInfo.getCreationDate());
		body.put("FOUNDER", ContextUtil.getCurrentUser().getFullname());
		constructingGridRanges(coverageAreaDetails, gridBasicInfo.getGridRange());
		body.put("COVERAGEAREADETAILS", coverageAreaDetails);

//		body.put("APPLICATIONREASON", gridBasicInfo.getReason());
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
		Map<String, Object> flowMap = new HashMap(),
				body = new HashMap();
		body.put("CODE", "SMPT0002");
		getProposer(body,gridBasicInfo);
		body.put("TYPE", GridOperateEnum.CHANGE_SCOPE.getDescription());
		body.put("GRIDCODE", gridBasicInfo.getGridCode());
		body.put("GRIDNAME", gridBasicInfo.getGridName());
		body.put("GRIDTYPE", getDicByType(gridBasicInfo.getGridType(), gridTypes));
		body.put("FORMATATTRIBUTE", getDicByType(gridBasicInfo.getFormatAttribute(), formatAttributeTypes));
		if (StringUtils.isNotRealEmpty(gridBasicInfo.getHousekeeperId())) {
			body.put("HOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridBasicInfo.getHousekeeperId()));
		} else {
			body.put("HOUSEKEEPER", "");
		}
		body.put("REMARKS", gridBasicInfo.getGridRemark());

		// 变更后网格覆盖范围
		constructingGridRanges(body, gridBasicInfo.getGridRange());

		// 变更前网格覆盖范围
		GridBasicInfo beforeGridInfo = gridApprovalRecordMapper.getBeforeGridInfo(gridBasicInfo.getId());
		if (null != beforeGridInfo.getGridRange()) {
			constructingGridRanges(body, beforeGridInfo.getGridRange());
		}
		body.put("APPLICATIONREASON", gridBasicInfo.getReason());
//		body.put("PROPOSERDETAIL", getProposer(gridBasicInfo));
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
		Map<String, Object> flowMap = new HashMap(),
				body = new HashMap();
		GridBasicInfo gridInfo = gridApprovalRecordMapper.getBeforeGridInfo(gridBasicInfoDTO.getId());
		body.put("CODE", "SMPT0003");
		getProposer(body,gridBasicInfoDTO);
		body.put("TYPE", GridOperateEnum.CHANGE_HOUSEKEEPER.getDescription());
		body.put("GRIDCODE", gridInfo.getGridCode());
		body.put("GRIDNAME", gridInfo.getGridName());
		body.put("GRIDTYPE", getDicByType(gridInfo.getGridType(), gridTypes));
		body.put("FORMATATTRIBUTE", getDicByType(gridInfo.getFormatAttribute(), formatAttributeTypes));
		body.put("REMARKS", gridInfo.getGridRemark());
		if (StringUtils.isNotRealEmpty(gridInfo.getHousekeeperId())) {
			body.put("BEFOREHOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridInfo.getHousekeeperId()));
		} else {
			body.put("BEFOREHOUSEKEEPER", "");
		}

		if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getHousekeeperId())) {
			body.put("AFTERHOUSEKEEPER", gridApprovalRecordMapper.getUserNameById(gridBasicInfoDTO.getHousekeeperId()));
		} else {
			body.put("AFTERHOUSEKEEPER", "");
		}

		// 网格范围
		constructingGridRanges(body, gridInfo.getGridRange());
		body.put("APPLICATIONREASON", gridBasicInfoDTO.getReason());

		flowMap.put("DATA", body);

		return JsonUtil.toJson(flowMap);
	}

	/**
	 * 构建 K2流程消息体（管家关联网格）
	 *
	 * @param approvalContent
	 * @return
	 * @throws IOException
	 */
	private String constructingFlowContentForLinkHousekeeper(Object approvalContent, List<Map<String, String>> gridTypes, List<Map<String, String>> formatAttributeTypes) throws IOException {

		/**
		 * 1.流程信息体
		 * 2.流程信息主体
		 * 3.申请人信息
		 * 4.事项信息
		 * 5.网格覆盖范围
		 */
		GridBasicInfoDTO gridBasicInfoDTO = (GridBasicInfoDTO) approvalContent;
		Map<String, Object> flowMap = new HashMap(),
				body = new HashMap();
		GridBasicInfo gridInfo = gridApprovalRecordMapper.getBeforeGridInfo(gridBasicInfoDTO.getId());
		body.put("CODE", "SMPT0005");
		getProposer(body,gridBasicInfoDTO);
		body.put("TYPE", GridOperateEnum.LINK_HOUSEKEEPER.getDescription());
		Map<String, String> housekeeper = gridApprovalRecordMapper.getHousekeeperInfoById(flowDbPortal, gridBasicInfoDTO.getHousekeeperId(), gridBasicInfoDTO.getStagingId());
		if (null != housekeeper) {
			body.put("HOUSEKEEPERNAME", housekeeper.get("FULLNAME_"));
			body.put("LEVEL", housekeeper.get("NAME_"));
			body.put("PHONE", housekeeper.get("MOBILE_"));
		} else {
			body.put("HOUSEKEEPERNAME", "");
			body.put("LEVEL", "");
			body.put("PHONE", "");
		}
		List<Map<String,Object>> gridDetail = new ArrayList<>();
		Map<String,Object> gridDetailChild = new HashMap<>();
		gridDetailChild.put("GRIDCODE", gridInfo.getGridCode());
		gridDetailChild.put("GRIDNAME", gridInfo.getGridName());
		gridDetailChild.put("GRIDTYPE", getDicByType(gridInfo.getGridType(), gridTypes));
		gridDetailChild.put("FORMATATTRIBUTE", getDicByType(gridInfo.getFormatAttribute(), formatAttributeTypes));
		gridDetailChild.put("REMARKS", gridInfo.getGridRemark());

		// 网格范围
		constructingGridRangesLink(gridDetailChild, gridInfo.getGridRange());
		Map<String,List> griddetails = new HashMap<>();
		gridDetail.add(gridDetailChild);
		griddetails.put("GRIDDETAIL",gridDetail);
		body.put("GRIDDETAILS",griddetails);
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
		Map<String, Object> flowMap = new HashMap(),
				body = new HashMap();
		List<Map<String, Object>> removeGridDetails = new ArrayList<>();

		String houseKeeperId = "", orgId = "";
		for (GridBasicInfo gridInfo : gridBasicInfos) {
			houseKeeperId = gridInfo.getHousekeeperId();
			orgId = gridInfo.getStagingId();

			Map<String, Object> gridMap = new HashMap();
			gridMap.put("GRIDCODE", gridInfo.getGridCode());
			gridMap.put("GRIDNAME", gridInfo.getGridName());
			gridMap.put("GRIDTYPE", getDicByType(gridInfo.getGridType(), gridTypes));
			gridMap.put("FORMATATTRIBUTE", getDicByType(gridInfo.getFormatAttribute(), formatAttributeTypes));
			gridMap.put("REMARKS", gridInfo.getGridRemark());

			// 获取网格覆盖范围
			Map<String,Object> areaDetails = new HashMap<>();
			constructingGridRanges(areaDetails, gridInfo.getGridRange());
			gridMap.put("COVERAGEAREADETAILS", areaDetails);
			removeGridDetails.add(gridMap);
		}

		// TODO 根据管家id，获取管家信息
		//  (ORG_ID_,USER_ID_)uc_org_user(POS_ID_) >>> (POS_ID_)uc_org_post(POST_KEY_)   >>> (POST_KEY_)portal.portal_sys_dic(NAME_)
		body.put("CODE", "SMPT0006");
		getProposer(body,gridBasicInfoDTO);
		body.put("TYPE", GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getDescription());
		Map<String, String> housekeeper = gridApprovalRecordMapper.getHousekeeperInfoById(flowDbPortal, houseKeeperId, orgId);
		if (null != housekeeper) {
			body.put("HOUSEKEEPERNAME", housekeeper.get("FULLNAME_"));
			body.put("LEVEL", housekeeper.get("NAME_"));
			body.put("PHONE", housekeeper.get("MOBILE_"));
		} else {
			body.put("HOUSEKEEPERNAME", "");
			body.put("LEVEL", "");
			body.put("PHONE", "");
		}
		Map<String,List> map = new HashMap<>();
		map.put("REMOVEGRIDDETAIL",removeGridDetails);
		body.put("REMOVEGRIDDETAILS", map);
		body.put("APPLICATIONREASON", gridBasicInfoDTO.getReason());

		flowMap.put("DATA", body);

		return JsonUtil.toJson(flowMap);
	}

	/**
	 * 统一构建网格覆盖范围
	 *
	 * @param body
	 * @param gridRange
	 */
	private void constructingGridRanges(Map<String,Object> body, String gridRange) {
		if (StringUtils.isRealEmpty(gridRange)) {
			return;
		}
		Map<String,List<Map<String,String>>> coverageareadetails = new HashMap<>();
		List<Map<String,String>> list = new ArrayList<>();
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
						Map<String,String> child = new HashMap<>();
						child.put("STORIEDBUILDING", building.getName());
						child.put("UNIT", unit.getName());
						child.put("HOUSEPROPERTY", house.getName());
						list.add(child);
					}
				}
			}
		}
		coverageareadetails.put("COVERAGEAREADETAIL",list);
		body.put("COVERAGEAREADETAILS",coverageareadetails);
	}

	/**
	 * 统一构建网格覆盖范围
	 *
	 * @param gridDetail
	 * @param gridRange
	 */
	private void constructingGridRangesLink(Map<String,Object> gridDetail, String gridRange) {
		if (StringUtils.isRealEmpty(gridRange)) {
			return;
		}
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,List<Map<String,String>>> coverageareadetails = new HashMap<>();
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
						Map<String,String> child = new HashMap<>();
						child.put("STORIEDBUILDING", building.getName());
						child.put("UNIT", unit.getName());
						child.put("HOUSEPROPERTY", house.getName());
						list.add(child);
					}
				}
			}
		}
		coverageareadetails.put("ITEM",list);
		gridDetail.put("COVERAGEAREADETAILS",coverageareadetails);
	}

	/**
	 * 获取申请人信息
	 *
	 * @param proposer
	 * @return
	 */
	private void getProposer(Map body,GridBasicInfoDTO proposer) {
		// TODO 需要传入更多信息（区域id、城区id、项目id、地块id、申请人页面选择信息）
		body.put("JURISDICTION", "0");
//		body.put("ID", "wy_xiaoxiong_zhang");
//		body.put("NAME", "张晓雄");
		body.put("ID", proposer.getAccount());
		body.put("NAME", ContextUtil.getCurrentUser().getFullname());
		body.put("DATE", new Date());
		body.put("PLANS", proposer.getPostId());
		body.put("ROLE", proposer.getPostName());
		body.put("REGION", proposer.getAreaName());
		body.put("CITYPROPER", proposer.getCityName());
		body.put("PROJECT", proposer.getProjectName());
		body.put("BLOCK", proposer.getStagingName());
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
