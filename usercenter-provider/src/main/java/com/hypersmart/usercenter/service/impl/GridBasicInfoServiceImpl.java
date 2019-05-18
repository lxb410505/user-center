package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.GridRangeBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.constant.GridTypeConstants;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
import com.hypersmart.usercenter.dto.RangeDTO;
import com.hypersmart.usercenter.mapper.GridApprovalRecordMapper;
import com.hypersmart.usercenter.mapper.GridBasicInfoMapper;
import com.hypersmart.usercenter.mapper.UcOrgParamsMapper;
import com.hypersmart.usercenter.mapper.UcOrgUserMapper;
import com.hypersmart.usercenter.mapper.UcUserMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.*;
import com.hypersmart.usercenter.util.GridOperateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 网格基础信息表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */
@Service("gridBasicInfoServiceImpl")
public class GridBasicInfoServiceImpl extends GenericService<String, GridBasicInfo> implements GridBasicInfoService {

	@Resource
	private GridBasicInfoMapper gridBasicInfoMapper;

	@Resource
	private UcOrgParamsMapper ucOrgParamsMapper;

	@Autowired
	UcOrgUserMapper ucOrgUserMapper;
		
	@Autowired
	UcOrgService ucOrgService;

	@Autowired
	GridRangeService gridRangeService;

	@Autowired
	private GridApprovalRecordService gridApprovalRecordService;

	@Resource
	private GridApprovalRecordMapper gridApprovalRecordMapper;

	@Resource
	private DashBoardFeignService dashBoardFeignService;
	
	@Autowired
    private UcUserMapper ucUserMapper;
	
	public GridBasicInfoServiceImpl(GridBasicInfoMapper mapper) {
		super(mapper);
	}

	/**
	 * 网格分页查询
	 *
	 * @param queryFilter
	 * @return
	 */
	@Override
	public PageList<Map<String, Object>> quertList(QueryFilter queryFilter, int type) {
		/**
		 * 1、查询当前登录人所在的所有分期（uc_org.LEVEL_ = 4）的管家信息：
		 *    接口返回以下信息：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
		 *    支持排序的字段：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
		 */
		// 地块id从前台传入，无须在后台过滤数据权限
		Object orgId = ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);
		if (orgId != null) {
			queryFilter.getParams().put("massifId", orgId.toString());
		} else {
			PageList<Map<String, Object>> pageList = new PageList();
			pageList.setTotal(0);
			pageList.setPage(1);
			pageList.setPageSize(10);
			pageList.setRows(new ArrayList<>());
			return pageList;
		}

		//根据创建时间倒叙排序
		List<FieldSort> fieldSortList = queryFilter.getSorter();
		FieldSort fieldSort = new FieldSort();
		fieldSort.setDirection(Direction.DESC);
		fieldSort.setProperty("creationDate");
		fieldSortList.add(fieldSort);
		PageBean pageBean = queryFilter.getPageBean();
		if (BeanUtils.isEmpty(pageBean)) {
			PageHelper.startPage(1, Integer.MAX_VALUE, false);
		} else {
			PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
					pageBean.showTotal());
		}

		List<Map<String, Object>> query = new ArrayList<>();
		if (type == 0) {
			query = this.gridBasicInfoMapper.quertList(queryFilter.getParams());
		}
		if (type == 1) {
			query = this.gridBasicInfoMapper.queryAssociateList(queryFilter.getParams());
		}
		return new PageList<>(query);
	}

	/**
	 * 根据id获取网格
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> getGridById(String id) {
		Map<String, Object> objectMap = gridBasicInfoMapper.getGridById(id);
		objectMap.put("allRange", null);
		if (objectMap.get("id") != null && objectMap.get("stagingId") != null) {
			GridRangeBO gridRangeBO = new GridRangeBO();
			gridRangeBO.setGridId(objectMap.get("id").toString());
			gridRangeBO.setStagingId(objectMap.get("stagingId").toString());
			List<String> rangeList = gridRangeService.getAllRange(gridRangeBO);
			objectMap.put("allRange", rangeList);
		}
		return objectMap;
	}

	/**
	 * 新增网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@Override
	public GridErrorCode create(GridBasicInfoDTO gridBasicInfoDTO) {
		GridErrorCode gridErrorCode = GridErrorCode.SUCCESS;
		Integer num = 0;
		if (gridBasicInfoDTO != null) {
			//判断房产是否已经被覆盖
			if (StringUtil.isNotEmpty(gridBasicInfoDTO.getGridRange())) {
				boolean flag = gridRangeService.judgeExistHouse(gridBasicInfoDTO.getGridRange(), null, gridBasicInfoDTO.getStagingId());
				if (flag) {
					gridErrorCode = GridErrorCode.INSERT_EXCEPTION;
					gridErrorCode.setMessage("存在已被覆盖的房产信息！");
					return gridErrorCode;
				}
			}
			GridBasicInfo gridBasicInfo = new GridBasicInfo();
			gridBasicInfo.setId(UUID.randomUUID().toString());
			gridBasicInfo.setAreaId(gridBasicInfoDTO.getAreaId());
			gridBasicInfo.setCityId(gridBasicInfoDTO.getCityId());
			gridBasicInfo.setProjectId(gridBasicInfoDTO.getProjectId());
//            gridBasicInfo.setMassifId(gridBasicInfoDTO.getMassifId());
			gridBasicInfo.setStagingId(gridBasicInfoDTO.getStagingId());
			gridBasicInfo.setGridCode(gridBasicInfoDTO.getGridCode());
			gridBasicInfo.setGridName(gridBasicInfoDTO.getGridName());
			if (GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoDTO.getGridType())) {
				gridBasicInfo.setGridRange(gridBasicInfoDTO.getGridRange());
			}
			gridBasicInfo.setGridRemark(gridBasicInfoDTO.getGridRemark());
			gridBasicInfo.setGridType(gridBasicInfoDTO.getGridType());
			if ("".equals(gridBasicInfoDTO.getHousekeeperId())) {
				gridBasicInfo.setHousekeeperId(null);
			} else {
				gridBasicInfo.setHousekeeperId(gridBasicInfoDTO.getHousekeeperId());
			}
			gridBasicInfo.setFormatAttribute(gridBasicInfoDTO.getFormatAttribute());
			gridBasicInfo.setSecondFormatAttribute(gridBasicInfoDTO.getSecondFormatAttribute());
			gridBasicInfo.setThirdFormatAttribute(gridBasicInfoDTO.getThirdFormatAttribute());
			gridBasicInfo.setCreationDate(new Date());
			gridBasicInfo.setUpdationDate(new Date());
			gridBasicInfo.setCreatedBy(ContextUtil.getCurrentUser().getUserId());
			gridBasicInfo.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
			//网格范围
			gridBasicInfo.setGridRange(gridBasicInfoDTO.getGridRange());
			//新增
			num = this.insertSelective(gridBasicInfo);
			//网格覆盖范围表记录
			if (GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoDTO.getGridType()) && !StringUtils.isEmpty(gridBasicInfoDTO.getGridRange())) {
				//记录数据
				gridRangeService.recordRange(gridBasicInfoDTO.getGridRange(), gridBasicInfo.getId());
			}

			// 调用K2
			gridBasicInfoDTO.setId(gridBasicInfo.getId());
			gridBasicInfoDTO.setCreationDate(gridBasicInfo.getCreationDate());
			gridApprovalRecordService.callApproval(GridOperateEnum.NEW_GRID.getOperateType(), gridBasicInfo.getId(), gridBasicInfoDTO);
			if(num>0){
				//增加gridRange 字段的缓存
				handChangeRange(gridBasicInfo.getId(),gridBasicInfo.getGridRange(),1);//1 新增
			}
		}
		if (num < 1) {
			gridErrorCode = GridErrorCode.INSERT_EXCEPTION;
		}


		return gridErrorCode;
	}

	/**
	 * 修改网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@Override
	public GridErrorCode edit(GridBasicInfoDTO gridBasicInfoDTO) {
		GridErrorCode gridErrorCode = GridErrorCode.SUCCESS;
		Integer num = 0;
		if (gridBasicInfoDTO != null && !StringUtils.isEmpty(gridBasicInfoDTO.getId())) {

			//获取对应id旧的的网格数据
			GridBasicInfo gridBasicInfoOld = this.get(gridBasicInfoDTO.getId());
			/*//历史数据存储(变更前) Todo
		    //历史数据存储(变更后) Todo
            gridBasicInfoHistoryService.saveGridBasicInfoHistory(gridBasicInfoDTO);*/
			//修改网格
			GridBasicInfo gridBasicInfo = new GridBasicInfo();
			gridBasicInfo.setId(gridBasicInfoDTO.getId());
			gridBasicInfo.setGridName(gridBasicInfoDTO.getGridName());
			gridBasicInfo.setGridRemark(gridBasicInfoDTO.getGridRemark());
			gridBasicInfo.setFormatAttribute(gridBasicInfoDTO.getFormatAttribute());
			if (StringUtil.isEmpty(gridBasicInfoDTO.getSecondFormatAttribute())) {
				gridBasicInfo.setSecondFormatAttribute("");
			} else {
				gridBasicInfo.setSecondFormatAttribute(gridBasicInfoDTO.getSecondFormatAttribute());
			}
			if (StringUtil.isEmpty(gridBasicInfoDTO.getThirdFormatAttribute())) {
				gridBasicInfo.setThirdFormatAttribute("");
			} else {
				gridBasicInfo.setThirdFormatAttribute(gridBasicInfoDTO.getThirdFormatAttribute());
			}
			//只要记录历史记录updateTimes都加1
			gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
			gridBasicInfo.setUpdationDate(new Date());
			gridBasicInfo.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
			num = this.updateSelective(gridBasicInfo);
		}
		if (num < 1) {
			gridErrorCode = GridErrorCode.UPDATE_EXCEPTION;
		}
		return gridErrorCode;
	}

	/**
	 * 变更管家
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@Override
	public GridErrorCode changeHousekeeper(GridBasicInfoDTO gridBasicInfoDTO) {
		GridErrorCode gridErrorCode = GridErrorCode.SUCCESS;
		// 调用K2
		gridApprovalRecordService.callApproval(GridOperateEnum.CHANGE_HOUSEKEEPER.getOperateType(), gridBasicInfoDTO.getId(), gridBasicInfoDTO);
		return gridErrorCode;
	}

	/**
	 * 变更映射楼栋
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@Override
	public GridErrorCode changeRange(GridBasicInfoDTO gridBasicInfoDTO) {
		GridErrorCode gridErrorCode = GridErrorCode.SUCCESS;
		//获取对应id旧的的网格数据
		GridBasicInfo gridBasicInfoOld = this.get(gridBasicInfoDTO.getId());
		//判断房产是否已经被覆盖
		if (StringUtil.isNotEmpty(gridBasicInfoDTO.getGridRange())) {
			boolean flag = gridRangeService.judgeExistHouse(gridBasicInfoDTO.getGridRange(), gridBasicInfoDTO.getId(), gridBasicInfoOld.getStagingId());
			if (flag) {
				gridErrorCode = GridErrorCode.INSERT_EXCEPTION;
				gridErrorCode.setMessage("存在已被覆盖的房产信息！");
				return gridErrorCode;
			}
		}

		// 调用K2
		gridApprovalRecordService.callApproval(GridOperateEnum.CHANGE_SCOPE.getOperateType(), gridBasicInfoDTO.getId(), gridBasicInfoDTO);
		return gridErrorCode;
	}

	/**
	 * 批量禁用网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@Override
	public GridErrorCode disableGridList(GridBasicInfoDTO gridBasicInfoDTO) {
		GridErrorCode gridErrorCode = GridErrorCode.SUCCESS;
		// 调用K2
		String[] ids = gridBasicInfoDTO.getIds();
		List<GridBasicInfo> gridBasicInfos = gridApprovalRecordMapper.getGridInfoByIds(ids);
		if (!CollectionUtils.isEmpty(gridBasicInfos)) {
			for (GridBasicInfo gridInfo : gridBasicInfos) {
				GridBasicInfoDTO dto = new GridBasicInfoDTO();
				dto.setId(gridInfo.getId());
				dto.setGridCode(gridInfo.getGridCode());
				dto.setGridName(gridInfo.getGridName());
				dto.setGridType(gridInfo.getGridType());
				dto.setFormatAttribute(gridInfo.getFormatAttribute());
				dto.setHousekeeperId(gridInfo.getHousekeeperId());
				dto.setGridRemark(gridInfo.getGridRemark());
				dto.setPostId(gridBasicInfoDTO.getPostId());
				dto.setPostName(gridBasicInfoDTO.getPostName());
				dto.setReason(gridBasicInfoDTO.getReason());
				dto.setGridRange(gridInfo.getGridRange());
				dto.setCreationDate(gridInfo.getCreationDate());
				dto.setAreaName(gridBasicInfoDTO.getAreaName());
				dto.setCityName(gridBasicInfoDTO.getCityName());
				dto.setProjectName(gridBasicInfoDTO.getProjectName());
				dto.setStagingName(gridBasicInfoDTO.getStagingName());
				gridApprovalRecordService.callApproval(GridOperateEnum.DISABLE_GRID.getOperateType(), gridInfo.getId(), dto);
			}
		}
		return gridErrorCode;
	}

	/**
	 * 批量删除网格
	 *
	 * @param gridBasicInfoBO
	 * @return
	 */
	@Override
	public GridErrorCode deleteGridList(GridBasicInfoBO gridBasicInfoBO) {
		GridErrorCode gridErrorCode = GridErrorCode.SUCCESS;
		Integer num = 0;
		gridBasicInfoBO.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
		//主表更新
		num = gridBasicInfoMapper.deleteGridInfo(gridBasicInfoBO);
		//范围表更新
		gridRangeService.deleteRangeByGridIds(gridBasicInfoBO.getIds());
		if (num < 1) {
			gridErrorCode = GridErrorCode.DELETE_EXCEPTION;
		}else{
			//增加gridRange 字段的缓存
			handChangeRange(gridBasicInfoBO.getId(),null,3);//3 删除
		}
		return gridErrorCode;
	}

	@Override
	public List<GridBasicInfoSimpleDTO> getGridBasicInfoByHouseKeeperIds(List<HouseKeeperBO> houseKeeperBO) {
		return gridBasicInfoMapper.getGridBasicInfoByHouseKeeperIds(houseKeeperBO);
	}

	/**
	 * 根据分期id获得该分期下未关联的分期
	 *
	 * @param divideId
	 * @return
	 */
	@Override
	public List<GridBasicInfo> getDisassociatedGridByDivideId(String divideId) {
		Example example = new Example(GridBasicInfo.class);
		example.createCriteria().andEqualTo("stagingId", divideId).andIsNull("housekeeperId");
		return gridBasicInfoMapper.selectByExample(example);
	}

	/**
	 * 根据地块id，获取地块下的楼栋网格
	 *
	 * @param massifId
	 * @return
	 */
	@Override
	public List<GridBasicInfo> getGridsBymassifId(String massifId) {
		Example exampleP = new Example(UcOrgParams.class);
		exampleP.createCriteria().andEqualTo("value", massifId)
		                         //.andEqualTo("code", "SAPgsdm")
				                 .andEqualTo("isDele", 0);
		List<UcOrgParams> list = ucOrgParamsMapper.selectByExample(exampleP);
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		List<String> stagingId = list.stream().map(UcOrgParams::getOrgId).collect(Collectors.toList());   
				
		if (CollectionUtils.isEmpty(stagingId)) {
			return new ArrayList<>();
		}
		
		return gridBasicInfoMapper.getGridBasicInfoByHouseKeeperID(stagingId);
	}

	/**
	 * 管家关联网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@Override
	public GridErrorCode associatedGrid(GridBasicInfoDTO gridBasicInfoDTO) {
		List<GridBasicInfoBO> gridBasicInfoBOList = gridBasicInfoDTO.getGridBasicInfoBOList();
		List<String> IdList = gridBasicInfoBOList.stream().map(map -> map.getId()).collect(Collectors.toList());
		String housekeeperId = gridBasicInfoBOList.get(0).getHousekeeperId();

		// 调用K2
		if (!CollectionUtils.isEmpty(IdList)) {
			for (String id : IdList) {
				GridBasicInfoDTO dto = new GridBasicInfoDTO();
				dto.setId(id);
				dto.setPostId(gridBasicInfoDTO.getPostId());
				dto.setPostName(gridBasicInfoDTO.getPostName());
				dto.setReason(gridBasicInfoDTO.getReason());
				dto.setHousekeeperId(housekeeperId);
				dto.setAreaName(gridBasicInfoDTO.getAreaName());
				dto.setCityName(gridBasicInfoDTO.getCityName());
				dto.setProjectName(gridBasicInfoDTO.getProjectName());
				dto.setStagingName(gridBasicInfoDTO.getStagingName());
				dto.setStagingId(gridBasicInfoDTO.getStagingId());
				gridApprovalRecordService.callApproval(GridOperateEnum.LINK_HOUSEKEEPER.getOperateType(), id, dto);
			}
		}
		return GridErrorCode.SUCCESS;
	}

	/**
	 * 根据地块id，获取地块下的楼栋网格
	 *
	 * @param massifId
	 * @return
	 */
	@Override
	public List<GridBasicInfo> getGridsBySmcloudmassifId(String massifId) {
		Example example = new Example(GridBasicInfo.class);
		example.createCriteria().andEqualTo("stagingId", massifId)
				.andEqualTo("gridType", "building_grid")
				.andEqualTo("isDeleted", 0)
				.andEqualTo("enabledFlag", 1);

		return gridBasicInfoMapper.selectByExample(example);
	}

	@Override
	public PageInfo<GridBasicInfo> getGridsBySmcloudmassifIdPage(String massifId, Integer page, Integer pageSize) {
		Example example = new Example(GridBasicInfo.class);
		if(org.apache.commons.lang3.StringUtils.isEmpty(massifId)) {
			List<GridBasicInfo> infos = new ArrayList<>();
			example.createCriteria()
					.andEqualTo("gridType", "building_grid")
					.andEqualTo("isDeleted", 0)
					.andEqualTo("enabledFlag", 1);

			return doPage(page,pageSize, example);

		}
		example.createCriteria().andEqualTo("stagingId", massifId)
				.andEqualTo("gridType", "building_grid")
				.andEqualTo("isDeleted", 0)
				.andEqualTo("enabledFlag", 1);

		return new PageInfo<>(gridBasicInfoMapper.selectByExample(example));
	}

public  PageInfo<GridBasicInfo> doPage(int pageNum,int pageSize,Example example){
    PageHelper.startPage(pageNum, pageSize);
    PageInfo pageInfo = new PageInfo(gridBasicInfoMapper.selectByExample(example));
    return pageInfo;
}

	/**
	 * 管家解除关联网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@Override
	public GridErrorCode disassociatedGrid(GridBasicInfoDTO gridBasicInfoDTO) {
		List<GridBasicInfoBO> gridBasicInfoBOList = gridBasicInfoDTO.getGridBasicInfoBOList();
		List<String> IdList = gridBasicInfoBOList.stream().map(map -> map.getId()).collect(Collectors.toList());
		String[] ids = (String[]) IdList.toArray(new String[IdList.size()]);
		gridBasicInfoDTO.setIds(ids);

		// 调用K2
		gridApprovalRecordService.callApproval(GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getOperateType(), "", gridBasicInfoDTO);
		return GridErrorCode.SUCCESS;
	}

	@Override
	public List<RangeDTO> getGridsHouseBymassifId(String massifId) {
		List<RangeDTO> returnList = new ArrayList<>();
        List<GridBasicInfo> gridBasicInfos = this.getGridsBySmcloudmassifId(massifId);
        for(int i=0;i<gridBasicInfos.size();i++){
        	List<RangeDTO> listObjectFir = (List<RangeDTO>) JSONArray.parse(gridBasicInfos.get(i).getGridRange());
			returnList.addAll(listObjectFir);
		}
		/*gridBasicInfos.forEach(grid -> {
			List<Map<String,Object>> listObjectFir = (List<Map<String,Object>>) JSONArray.parse(grid.getGridRange());
			returnList.addAll(listObjectFir);
			*//*returnList.addAll(JsonUtil.to)*//*
		});*/
		Set<RangeDTO> set = new HashSet<>(returnList);
		return new ArrayList<>(set);
	}

	@Override
	public List<Map<String,Object>> getGridsHouseBymassifIdPage(String massifId,Integer page,Integer pageSize) {
		List<Map<String,Object>> returnList = new ArrayList<>();
		PageInfo<GridBasicInfo> pageInfo = this.getGridsBySmcloudmassifIdPage(massifId, page, pageSize);
		List<GridBasicInfo> gridBasicInfos =pageInfo.getList();
		gridBasicInfos.forEach(grid -> {
			List<Map<String,Object>> listObjectFir = (List<Map<String,Object>>) JSONArray.parse(grid.getGridRange());
			if(!CollectionUtils.isEmpty(listObjectFir)) {
                returnList.addAll(listObjectFir);
            }
		});

		if(org.apache.commons.lang3.StringUtils.isEmpty(massifId)) {
			Map<String, Object> map = new HashMap<>(16);
			Map<String, Object> resultMap = new HashMap<>(16);
			map.put("pages", pageInfo.getPages());
			map.put("total", pageInfo.getTotal());
			map.put("pageNum", pageInfo.getPageNum());
			map.put("pageSize", pageInfo.getPageSize());
			map.put("nextPage", pageInfo.getNextPage());
			map.put("prePage", pageInfo.getPrePage());
			resultMap.put("pageInfo", map);
			returnList.add(resultMap);
		}
		return returnList;
	}



	@Override
	public List<GridBasicInfo> getByGridRange(String gridRange) {
		List<GridBasicInfo> list = gridBasicInfoMapper.getByGridRange(gridRange);
		return list;
	}

	/*public static void main(String[] args){
		String a = "[{\"id\":\"d3d6affe-841a-11e8-940f-7cd30adaaf52\",\"name\":\"27栋\",\"code\":\"027\",\"parentId\":0,\"checked\":0,\"level\":1},{\"id\":\"ce314fa8-841f-11e8-940f-7cd30adaaf52\",\"name\":\"1单元\",\"code\":\"01\",\"parentId\":\"d3d6affe-841a-11e8-940f-7cd30adaaf52\",\"checked\":0,\"level\":2},{\"id\":\"1cf45506-88a8-11e8-940f-7cd30adaaf52\",\"name\":\"027-01-0201\",\"code\":\"0201\",\"parentId\":\"ce314fa8-841f-11e8-940f-7cd30adaaf52\",\"checked\":1,\"level\":3},{\"id\":\"1cf45563-88a8-11e8-940f-7cd30adaaf52\",\"name\":\"027-01-0202\",\"code\":\"0202\",\"parentId\":\"ce314fa8-841f-11e8-940f-7cd30adaaf52\",\"checked\":1,\"level\":3}]";
		List<Map<String,Object>> listObjectFir = (List<Map<String,Object>>) JSONArray.parse(a);
		System.out.println("利用JSONArray中的parse方法来解析json数组字符串");
		for(Map<String,Object> mapList : listObjectFir){
			for (Map.Entry entry : mapList.entrySet()){
				System.out.println( entry.getKey()  + "  " +entry.getValue());
			}
		}
	}*/
	@Override
	public  void handChangeRange(String gridId,String gridRange,Integer action){
		new Thread(new Runnable() {
			@Override
			public void run() {
				dashBoardFeignService.handChangeRange(gridId,gridRange,action);
			}
		});
	}

	@Override
	public List<Map<String, Object>> getHouseByCondition(String divideId,String id,Integer pageNum,Integer pageSize) {
		List<Map<String, Object>> gridsHouseBymassifId = getGridsHouseBymassifIdPage(divideId, pageNum, pageSize);

		List<Map<String, Object>> parentId = gridsHouseBymassifId.stream().filter(e -> String.valueOf(e.get("parentId")).equals(id)).collect(Collectors.toList());

		if(org.apache.commons.lang3.StringUtils.isEmpty(divideId)) {
			List<Map<String, Object>> pageInfo = gridsHouseBymassifId.parallelStream().filter(e -> e.get("pageInfo") != null).collect(Collectors.toList());
			Map<String, Object> map = (Map<String, Object>) pageInfo.get(0).get("pageInfo");
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(map));
			parentId.add(0, jsonObject);
		}
		return parentId;
	}

}