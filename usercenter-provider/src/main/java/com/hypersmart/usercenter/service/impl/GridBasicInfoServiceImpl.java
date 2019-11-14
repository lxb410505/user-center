package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.base.util.UniqueIdUtil;
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
import com.hypersmart.usercenter.mapper.*;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.usercenter.service.*;
import com.hypersmart.usercenter.util.GridOperateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Resource
    private GridBasicInfoService gridBasicInfoService;

    @Resource
    private GridBasicInfoHistoryService gridBasicInfoHistoryService;

    @Resource
    private PublicGirdPercentService publicGirdPercentService;

    @Resource
    private StageServiceGirdRefService stageServiceGirdRefService;
    @Resource
    private StageServiceGirdRefMapper stageServiceGirdRefMapper;

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
        boolean flag = false;
        Object orgId = null;
        boolean isIn = false;
        Iterator<QueryField> iterator = queryFilter.getQuerys().iterator();
        while (iterator.hasNext()) {
            QueryField next = iterator.next();
            if ("massifId".equals(next.getProperty()) && null != next.getValue()) {
                flag = true;
                orgId = next.getValue();
                isIn = "IN".equals(next.getOperation().value()) ? true : false;
                break;
            }
        }
        Map<String, Object> params = queryFilter.getParams();
        params.put("isIn",isIn);
        if (flag) {
            params.put("massifId", isIn ? new ArrayList<>(Arrays.asList(orgId.toString().split(","))):orgId.toString());
            flag = true;
        }

        orgId = ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);
        if (orgId != null && !flag) {
            params.put("massifId", isIn ? Arrays.asList(orgId.toString()):orgId.toString() );
        }
		/*else {
			PageList<Map<String, Object>> pageList = new PageList();
			pageList.setTotal(0);
			pageList.setPage(1);
			pageList.setPageSize(10);
			pageList.setRows(new ArrayList<>());
			return pageList;
		}*/

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
            query = this.gridBasicInfoMapper.quertList(params);
            for (Map<String, Object> map : query) {
                List<JSONObject> listObjectFir = JSONArray.parseArray((String) map.get("gridRange"), JSONObject.class);
                List<JSONObject> set = new ArrayList<>();
                Map<String, Object> map1 = new HashMap<>(16);

                if (!CollectionUtils.isEmpty(listObjectFir)) {
                    for (JSONObject o : listObjectFir) {
                        String key = o.get("parentId").toString() + o.get("name").toString();
                        if (3 != Integer.valueOf((Integer) o.get("level")) && !map1.containsKey(key)) {
                            set.add(o);
                            map1.put(key, null);
                        }
                    }
                    // 对楼栋单元房产进行排序
                    Map<JSONObject, Integer> name = set.stream().collect(Collectors.toMap(o -> o, e -> sortListByNum((String) e.get("name"))));
                    List<Map.Entry<JSONObject, Integer>> collect = name.entrySet().stream().sorted(this::compare).collect(Collectors.toList());
                    List<JSONObject> objects = collect.stream().map(e -> e.getKey()).collect(Collectors.toList());
                    map.put("gridRange", JSONArray.toJSONString(objects));
                }
                if (map.get("projectId") == null) {
                    UcOrg ucOrg = ucOrgService.get(orgId.toString());
                    if (ucOrg != null) {
                        String[] split = ucOrg.getPath().split("\\.");
                        if (ucOrg.getLevel() == 3) {
                            map.put("areaId", split[2]);
                            map.put("projectId", split[3]);
                        } else {
                            map.put("areaId", split[2]);
                            map.put("cityId", split[3]);
                            map.put("projectId", split[4]);
                        }
                        UcOrg areaId = null;
                        if (map.get("areaId") != null) {
                            areaId = ucOrgService.get(map.get("areaId").toString());
                        }
                        UcOrg cityId = null;
                        if (map.get("cityId") != null) {
                            cityId = ucOrgService.get(map.get("cityId").toString());
                        }
                        UcOrg projectId = null;
                        if (map.get("projectId") != null) {
                            projectId = ucOrgService.get(map.get("projectId").toString());
                        }
                        if (areaId != null) {
                            map.put("areaName", areaId.getName());
                        }
                        if (cityId != null) {
                            map.put("cityName", cityId.getName());
                        }
                        if (projectId != null) {
                            map.put("projectName", projectId.getName());
                        }
                    }
                }
            }
			/*Map<String,Object> checkMap = new HashMap<>();
			Iterator<Map<String, Object>> mapIterator = query.iterator();
			while (mapIterator.hasNext()){
				Map<String, Object> next = mapIterator.next();
				if(!checkMap.containsKey(next.get("gridName"))) {
					checkMap.put((String) next.get("gridName"), null);
				}else{
					mapIterator.remove();
				}
			}*/
        } else if (type == 1) {
            query = this.gridBasicInfoMapper.queryAssociateList(queryFilter.getParams());
        }
        if (CollectionUtils.isEmpty(query)) {
            PageList<Map<String, Object>> pageList = new PageList();
            pageList.setTotal(0);
            pageList.setPage(1);
            pageList.setPageSize(10);
            pageList.setRows(new ArrayList<>());
            return pageList;
        }
        return new PageList<>(query);
    }

    private int compare(Map.Entry<JSONObject, Integer> map1, Map.Entry<JSONObject, Integer> map2) {
        return map1.getValue() - map2.getValue();
    }

    /**
     * 根据id获取网格
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getGridById(String id, String stagingId) {
        Map<String, Object> objectMap = gridBasicInfoMapper.getGridById(id, stagingId);
        objectMap.put("allRange", null);
        if (objectMap.get("id") != null && objectMap.get("stagingId") != null) {
            GridRangeBO gridRangeBO = new GridRangeBO();
            gridRangeBO.setGridId(objectMap.get("id").toString());
            gridRangeBO.setStagingId(objectMap.get("stagingId").toString());
            List<String> rangeList = gridRangeService.getAllRange(gridRangeBO);

            objectMap.put("allRange", rangeList);
            if (GridTypeConstants.SERVICE_CENTER_GRID.equals(objectMap.get("gridType"))) {
                List<Map<String, Object>> list = stageServiceGirdRefMapper.getServiceGridByGridId(id);
                if (!CollectionUtils.isEmpty(list)) {
                    List<Object> stagingIds = list.stream().map(o -> o.get("stagingId")).collect(Collectors.toList());
                    List<Object> stagingNames = list.stream().map(o -> o.get("stagingName")).collect(Collectors.toList());
                    objectMap.put("stagingIds", stagingIds);
                    objectMap.put("orgNames", stagingNames);
                }
            }

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
    public CommonResult<String> create(GridBasicInfoDTO gridBasicInfoDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        Integer num = 0;
        if (gridBasicInfoDTO != null) {
            //判断房产是否已经被覆盖
            if (StringUtil.isNotEmpty(gridBasicInfoDTO.getGridRange())) {
                boolean flag = gridRangeService.judgeExistHouse(gridBasicInfoDTO.getGridRange(), null, gridBasicInfoDTO.getStagingId());
                if (flag) {
                    commonResult.setMessage("存在已被覆盖的房产信息！");
                    commonResult.setState(false);
                    return commonResult;
                }
            }
            GridBasicInfo gridBasicInfo = new GridBasicInfo();
            if (GridTypeConstants.SERVICE_CENTER_GRID.equals(gridBasicInfoDTO.getGridType())) {
                num = handleServiceCenterGrid(gridBasicInfoDTO, 1);
            } else {
                gridBasicInfo.setAreaId(gridBasicInfoDTO.getAreaId());
                gridBasicInfo.setCityId(gridBasicInfoDTO.getCityId());
                gridBasicInfo.setProjectId(gridBasicInfoDTO.getProjectId());
//            gridBasicInfo.setMassifId(gridBasicInfoDTO.getMassifId());
                gridBasicInfo.setStagingId(gridBasicInfoDTO.getStagingId());
                gridBasicInfo.setId(UniqueIdUtil.getSuid());
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


                gridBasicInfoDTO.setId(gridBasicInfo.getId());
                gridBasicInfoDTO.setCreationDate(gridBasicInfo.getCreationDate());

                if (num > 0) {
                    //增加gridRange 字段的缓存
                    if (GridTypeConstants.BUILDING_GRID.equals(gridBasicInfo.getGridType())) {
                        handChangeRange(gridBasicInfo.getId(), gridBasicInfo.getGridRange(), 1);//1 新增
                    }
                }
            }
            // 调用K2
            // commonResult = gridApprovalRecordService.callApproval(GridOperateEnum.NEW_GRID.getOperateType(), gridBasicInfoDTO.getId(), gridBasicInfoDTO);

            if (num > 0) {
                if (GridTypeConstants.PUBLIC_AREA_GRID.equals(gridBasicInfoDTO.getGridType())) {//公区网格
                    addPublicGirdPercent(gridBasicInfo.getGridCode(), gridBasicInfo.getId(), gridBasicInfo.getGridName(),
                            gridBasicInfo.getStagingId());
                }
            }

        }
        if (num < 1) {
            commonResult.setMessage("存在已被覆盖的房产信息！");
            commonResult.setState(false);
            return commonResult;
        }


        return commonResult;
    }

    private void addPublicGirdPercent(String gridCode, String gridId, String gridName, String stagingId) {
        PublicGirdPercent publicGirdPercent = new PublicGirdPercent();
        publicGirdPercent.setAreaPercent(new BigDecimal(100));
        publicGirdPercent.setPublicGridCode(gridCode);
        publicGirdPercent.setPublicGridId(gridId);
        UcOrg ucOrg = ucOrgService.get(stagingId);
        publicGirdPercent.setPublicGridName(gridName);
        publicGirdPercent.setId(UniqueIdUtil.getSuid());
        publicGirdPercent.setStagingId(stagingId);
        publicGirdPercent.setStagingName(ucOrg != null ? ucOrg.getName() : "");
        publicGirdPercent.setCreationDate(new Date());
        publicGirdPercent.setUpdationDate(new Date());
        publicGirdPercent.setCreatedBy(ContextUtil.getCurrentUser().getUserId());
        publicGirdPercent.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
        publicGirdPercent.setEnabledFlag(1);
        publicGirdPercent.setIsDeleted(0);
        publicGirdPercentService.insertSelective(publicGirdPercent);
    }

    private Integer handleServiceCenterGrid(GridBasicInfoDTO gridBasicInfoDTO, Integer action) {
        int num = 0;
        //查询所传地块 查询服务网格
        if (!CollectionUtils.isEmpty(gridBasicInfoDTO.getStagingIds())) {
            QueryFilter queryFilter = QueryFilter.build(GridBasicInfo.class);
            queryFilter.addFilter("enabledFlag", 1, QueryOP.EQUAL, FieldRelation.AND);
            queryFilter.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
            queryFilter.addFilter("stagingId", gridBasicInfoDTO.getStagingIds(), QueryOP.IN, FieldRelation.AND);
            queryFilter.addFilter("gridType", GridTypeConstants.SERVICE_CENTER_GRID, QueryOP.EQUAL, FieldRelation.AND);
            PageList<GridBasicInfo> query = gridBasicInfoService.query(queryFilter);
            if (query != null && !CollectionUtils.isEmpty(query.getRows())) {
                for (GridBasicInfo gridBasicInfo : query.getRows()) {
                    gridBasicInfo.setHousekeeperId(gridBasicInfoDTO.getHousekeeperId());
                    gridBasicInfo.setGridRemark(gridBasicInfoDTO.getGridRemark());
                }
                int i = gridBasicInfoService.updateBatch(query.getRows());
                Integer isShare = 0;
                if (!CollectionUtils.isEmpty(gridBasicInfoDTO.getStagingIds()) && gridBasicInfoDTO.getStagingIds().size() > 1) {
                    isShare = 1;
                }
                if (i > 0) {
                    if (action.equals(1)) {
                        String uuId = UniqueIdUtil.getSuid();
                        gridBasicInfoDTO.setId(uuId);
                        gridBasicInfoDTO.setCreationDate(new Date());
                        return addStageServiceGirdRef(gridBasicInfoDTO.getGridCode(), gridBasicInfoDTO.getGridName(), uuId, gridBasicInfoDTO.getStagingIds(), isShare);
                    } else {
                        QueryFilter qf = QueryFilter.build(StageServiceGirdRef.class);
                        qf.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
                        qf.addFilter("enabledFlag", 1, QueryOP.EQUAL, FieldRelation.AND);
                        qf.addFilter("serviceGridId", gridBasicInfoDTO.getServiceGridId(), QueryOP.EQUAL, FieldRelation.AND);
                        PageList<StageServiceGirdRef> stageServiceGirdRefPageList = stageServiceGirdRefService.query(qf);
                        if (!CollectionUtils.isEmpty(stageServiceGirdRefPageList.getRows()) && stageServiceGirdRefPageList.getRows().size() > 0) {
                            List<StageServiceGirdRef> updateRef = new ArrayList<>();
                            List<String> updateStagingIds = new ArrayList<>();
                            List<StageServiceGirdRef> rows = stageServiceGirdRefPageList.getRows();
                            List<String> stagingIds = gridBasicInfoDTO.getStagingIds();
                            for (StageServiceGirdRef ref : rows) {
                                for (String staginId : stagingIds) {
                                    if (staginId.equals(ref.getStagingId())) {
                                        updateStagingIds.add(staginId);
                                        updateRef.add(ref);
                                    }
                                }
                            }

                            //更新
                            if (updateRef.size() > 0) {
                                for (StageServiceGirdRef ref : updateRef) {
                                    ref.setServiceGridName(gridBasicInfoDTO.getGridName());
                                    ref.setServiceGridCode(gridBasicInfoDTO.getGridCode());
                                    ref.setIsShared(isShare);
                                    num = stageServiceGirdRefService.update(ref);
                                }
                            }
                            //删除
                            rows.removeAll(updateRef);
                            if (rows.size() > 0) {
                                for (StageServiceGirdRef ref : rows) {
                                    ref.setIsDeleted(1);
                                    num = stageServiceGirdRefService.update(ref);
                                    QueryFilter queryFilter1 = QueryFilter.build(GridBasicInfo.class);
                                    queryFilter.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
                                    queryFilter.addFilter("enabledFlag", 1, QueryOP.EQUAL, FieldRelation.AND);
                                    queryFilter.addFilter("stagingId", ref.getStagingId(), QueryOP.EQUAL, FieldRelation.AND);
                                    queryFilter.addFilter("gridType", GridTypeConstants.SERVICE_CENTER_GRID, QueryOP.EQUAL, FieldRelation.AND);
                                    PageList<GridBasicInfo> query1 = gridBasicInfoService.query(queryFilter);
                                    if (query1 != null && !CollectionUtils.isEmpty(query1.getRows())) {
                                        GridBasicInfo gridBasicInfo = query1.getRows().get(0);
                                        gridBasicInfo.setHousekeeperId(null);
                                        gridBasicInfo.setGridRemark("");
                                        gridBasicInfoService.update(gridBasicInfo);
                                    }
                                }
                            }
                            //新增
                            stagingIds.removeAll(updateStagingIds);
                            stagingIds = stagingIds.stream().distinct().collect(Collectors.toList());
                            if (stagingIds.size() > 0) {
                                num = addStageServiceGirdRef(gridBasicInfoDTO.getGridCode(), gridBasicInfoDTO.getGridName(), gridBasicInfoDTO.getServiceGridId()
                                        , stagingIds, isShare);
                            }
                        }
                    }

                }
            }
        }
        return num;
    }

    //新增
    private Integer addStageServiceGirdRef(String gridCode, String gridName, String serviceGridId, List<String> stagingIds, Integer isShare) {
        int num = 0;
        if (!CollectionUtils.isEmpty(stagingIds)) {
            List<StageServiceGirdRef> stageServiceGirdRefs = new ArrayList<>();

            for (String stagingId : stagingIds) {
                StageServiceGirdRef stageServiceGirdRef = new StageServiceGirdRef();
                stageServiceGirdRefs.add(stageServiceGirdRef);
                stageServiceGirdRef.setServiceGridCode(gridCode);
                stageServiceGirdRef.setServiceGridId(serviceGridId);
                stageServiceGirdRef.setServiceGridName(gridName);
                stageServiceGirdRef.setId(UniqueIdUtil.getSuid());
                stageServiceGirdRef.setStagingId(stagingId);
                UcOrg ucOrg = ucOrgService.get(stagingId);
                stageServiceGirdRef.setStagingName(ucOrg != null ? ucOrg.getName() : "");
                stageServiceGirdRef.setCreationDate(new Date());
                stageServiceGirdRef.setUpdationDate(new Date());
                stageServiceGirdRef.setCreatedBy(ContextUtil.getCurrentUser().getUserId());
                stageServiceGirdRef.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
                stageServiceGirdRef.setEnabledFlag(1);
                stageServiceGirdRef.setIsDeleted(0);
                stageServiceGirdRef.setIsShared(isShare);
            }
            return stageServiceGirdRefService.insertBatch(stageServiceGirdRefs);
        }
        return num;
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
            if (GridTypeConstants.SERVICE_CENTER_GRID.equals(gridBasicInfoDTO.getGridType())) {
                num = handleServiceCenterGrid(gridBasicInfoDTO, 2);
            } else {
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
                Integer updateTimes = gridBasicInfoOld.getUpdateTimes();
                gridBasicInfo.setUpdateTimes(updateTimes == null ? 0 : updateTimes + 1);
                gridBasicInfo.setUpdationDate(new Date());
                gridBasicInfo.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
                num = this.updateSelective(gridBasicInfo);
            }
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
        //TODO 暂时不走k2审批
        GridBasicInfo grid = gridBasicInfoService.get(gridBasicInfoDTO.getId());
        if (grid != null) {
            //服务中心网格
            if (GridTypeConstants.SERVICE_CENTER_GRID.equals(grid.getGridType())) {
                List<GridBasicInfo> infos = getGridBasicInfo(grid.getStagingId());
                if (!CollectionUtils.isEmpty(infos)) {
                    boolean saveHistory = true;
                    for (GridBasicInfo gridBasicInfo : infos) {
                        if (saveHistory) {
                            gridBasicInfoHistoryService.saveGridBasicInfoHistory(gridBasicInfo, 0);
                        }
                        saveHistory = false;
                        if (StringUtils.isEmpty(gridBasicInfoDTO.getHousekeeperId())) {
                            gridBasicInfo.setHousekeeperId(null);
                        } else {
                            gridBasicInfo.setHousekeeperId(gridBasicInfoDTO.getHousekeeperId());
                        }
                        Integer updateTimes = grid.getUpdateTimes();
                        gridBasicInfo.setUpdateTimes(updateTimes == null ? 0 : updateTimes + 1);
                        gridBasicInfo.setUpdationDate(new Date());
                        gridBasicInfo.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
                        gridBasicInfoService.update(gridBasicInfo);
                    }
                }
            } else {
                gridBasicInfoHistoryService.saveGridBasicInfoHistory(grid, 0);
                if ("".equals(gridBasicInfoDTO.getHousekeeperId())) {
                    grid.setHousekeeperId(null);
                } else {
                    grid.setHousekeeperId(gridBasicInfoDTO.getHousekeeperId());
                }
                Integer updateTimes = grid.getUpdateTimes();
                grid.setUpdateTimes(updateTimes == null ? 0 : updateTimes + 1);
                grid.setUpdationDate(new Date());
                grid.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
                gridBasicInfoService.update(grid);
            }

        } else {
            gridErrorCode = GridErrorCode.UPDATE_EXCEPTION;
        }

//		// 调用K2
//		gridApprovalRecordService.callApproval(GridOperateEnum.CHANGE_HOUSEKEEPER.getOperateType(), gridBasicInfoDTO.getId(), gridBasicInfoDTO);
        return gridErrorCode;
    }

    /**
     * 变更映射楼栋
     *
     * @param gridBasicInfoDTO
     * @return
     */
    @Override
    public CommonResult<String> changeRange(GridBasicInfoDTO gridBasicInfoDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        //获取对应id旧的的网格数据
        GridBasicInfo gridBasicInfoOld = this.get(gridBasicInfoDTO.getId());
        //判断房产是否已经被覆盖
        if (StringUtil.isNotEmpty(gridBasicInfoDTO.getGridRange())) {
            boolean flag = gridRangeService.judgeExistHouse(gridBasicInfoDTO.getGridRange(), gridBasicInfoDTO.getId(), gridBasicInfoOld.getStagingId());
            if (flag) {
                commonResult.setState(false);
                commonResult.setMessage("存在已被覆盖的房产信息！");
                return commonResult;
            }
        }

        // 变更覆盖范围>>>记录历史>>>更新数据
        GridBasicInfo grid = gridBasicInfoService.get(gridBasicInfoDTO.getId());
        gridBasicInfoHistoryService.saveGridBasicInfoHistory(grid, 1);
        grid.setGridRange(gridBasicInfoDTO.getGridRange());
        grid.setUpdateTimes(grid.getUpdateTimes() + 1);
        grid.setUpdationDate(new Date());
        grid.setUpdatedBy(  ContextUtil.getCurrentUser().getUserId());
        int i = gridBasicInfoService.updateSelective(grid);
        if (i > 0) {
            if(GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoDTO.getGridType())){
                gridBasicInfoService.handChangeRange(gridBasicInfoDTO.getId(), gridBasicInfoDTO.getGridRange(), 2);//2 修改
            }
        }
        String[] ids = {gridBasicInfoDTO.getId()};
        gridRangeService.deleteRangeByGridIds(ids);
        gridRangeService.recordRange(gridBasicInfoDTO.getGridRange(), gridBasicInfoDTO.getId());
        // 调用K2
        //commonResult = gridApprovalRecordService.callApproval(GridOperateEnum.CHANGE_SCOPE.getOperateType(), gridBasicInfoDTO.getId(), gridBasicInfoDTO);
        return commonResult;
    }

    /**
     * 批量禁用网格
     *
     * @param gridBasicInfoDTO
     * @return
     */
    @Override
    public CommonResult<String> disableGridList(GridBasicInfoDTO gridBasicInfoDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
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
                dto.setAccount(gridBasicInfoDTO.getAccount());
                commonResult = gridApprovalRecordService.callApproval(GridOperateEnum.DISABLE_GRID.getOperateType(), gridInfo.getId(), dto);
                if (!commonResult.getState()) {
                    return commonResult;
                }
            }
        }
        return commonResult;
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

        String[] ids = gridBasicInfoBO.getIds();
        List<String> dtoIds = new ArrayList<>();
        for (String id : ids) {
            GridBasicInfo gridBasicInfo = gridBasicInfoService.get(id);
            if (GridTypeConstants.SERVICE_CENTER_GRID.equals(gridBasicInfo.getGridType())) {
                String serviceId = stageServiceGirdRefMapper.getServiceIdByStagingId(gridBasicInfo.getStagingId());
                if (!StringUtils.isEmpty(serviceId)) {
                    QueryFilter qf = QueryFilter.build(StageServiceGirdRef.class);
                    qf.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
                    qf.addFilter("serviceGridId", serviceId, QueryOP.EQUAL, FieldRelation.AND);
                    PageList<StageServiceGirdRef> stageServiceGirdRefPageList = stageServiceGirdRefService.query(qf);
                    if (stageServiceGirdRefPageList.getRows().size() > 0) {
                        for (StageServiceGirdRef grf : stageServiceGirdRefPageList.getRows()) {
                            grf.setIsDeleted(1);
                            grf.setCreatedBy(ContextUtil.getCurrentUser().getUserId());
                        }
                        stageServiceGirdRefService.updateBatch(stageServiceGirdRefPageList.getRows());
                    }
                }
                gridBasicInfoMapper.updateHousekeeperId(id);
            } else {
                dtoIds.add(id);
            }
        }
        if (dtoIds.size() > 0) {
            Integer num = 0;
            String[] newStr = new String[dtoIds.size()];
            dtoIds.toArray(newStr);
            gridBasicInfoBO.setIds(newStr);
            gridBasicInfoBO.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
            //主表更新
            num = gridBasicInfoMapper.deleteGridInfo(gridBasicInfoBO);
            //范围表更新
            gridRangeService.deleteRangeByGridIds(gridBasicInfoBO.getIds());
            if (num < 1) {
                gridErrorCode = GridErrorCode.DELETE_EXCEPTION;
            } else {
                //增加gridRange 字段的缓存
                if (GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoBO.getGridType())) {
                    handChangeRange(gridBasicInfoBO.getId(), null, 3);//3 删除
                }

            }
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
    public CommonResult<String> associatedGrid(GridBasicInfoDTO gridBasicInfoDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        List<GridBasicInfoBO> gridBasicInfoBOList = gridBasicInfoDTO.getGridBasicInfoBOList();

        // 调用K2
        if (!CollectionUtils.isEmpty(gridBasicInfoBOList)) {
            String housekeeperId = gridBasicInfoBOList.get(0).getHousekeeperId();
            for (GridBasicInfoBO bo : gridBasicInfoBOList) {
                GridBasicInfoDTO dto = new GridBasicInfoDTO();
                dto.setId(bo.getId());
                dto.setGridType(bo.getGridType());
                dto.setPostId(gridBasicInfoDTO.getPostId());
                dto.setPostName(gridBasicInfoDTO.getPostName());
                dto.setReason(gridBasicInfoDTO.getReason());
                dto.setHousekeeperId(housekeeperId);
                dto.setAreaName(gridBasicInfoDTO.getAreaName());
                dto.setCityName(gridBasicInfoDTO.getCityName());
                dto.setProjectName(gridBasicInfoDTO.getProjectName());
                dto.setStagingName(gridBasicInfoDTO.getStagingName());
                dto.setStagingId(gridBasicInfoDTO.getStagingId());
                dto.setAccount(gridBasicInfoDTO.getAccount());
                commonResult = gridApprovalRecordService.callApproval(GridOperateEnum.LINK_HOUSEKEEPER.getOperateType(), bo.getId(), dto);
                if (!commonResult.getState()) {
                    return commonResult;
                }
            }
        }
        return commonResult;
    }

    private List<GridBasicInfo> getGridBasicInfo(String id) {
        List<GridBasicInfo> gridBasicInfos = new ArrayList<>();
        Map<String, Object> stagingId = stageServiceGirdRefMapper.getServiceGridIdByStagingId(id);
        if (stagingId != null && stagingId.get("service_grid_id") != null) {
            QueryFilter qf = QueryFilter.build(StageServiceGirdRef.class);
            qf.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
            qf.addFilter("enabledFlag", 1, QueryOP.EQUAL, FieldRelation.AND);
            qf.addFilter("serviceGridId", stagingId.get("service_grid_id").toString(), QueryOP.EQUAL, FieldRelation.AND);

            PageList<StageServiceGirdRef> stageServiceGirdRefPageList = stageServiceGirdRefService.query(qf);
            if (stageServiceGirdRefPageList.getRows().size() > 0) {
                List<String> collect = stageServiceGirdRefPageList.getRows().stream().map(StageServiceGirdRef::getStagingId).collect(Collectors.toList());
                QueryFilter queryFilter = QueryFilter.build(GridBasicInfo.class);
                queryFilter.addFilter("gridType", GridTypeConstants.SERVICE_CENTER_GRID, QueryOP.EQUAL, FieldRelation.AND);
                queryFilter.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
                queryFilter.addFilter("enabledFlag", 1, QueryOP.EQUAL, FieldRelation.AND);
                queryFilter.addFilter("stagingId", collect, QueryOP.IN, FieldRelation.AND);
                PageList<GridBasicInfo> query = gridBasicInfoService.query(queryFilter);
                if (query != null && query.getRows() != null && query.getRows().size() > 0) {
                    gridBasicInfos = query.getRows();
                }
            }
        }
        return gridBasicInfos;
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
        if (org.apache.commons.lang3.StringUtils.isEmpty(massifId)) {
            List<GridBasicInfo> infos = new ArrayList<>();
            example.createCriteria()
                    .andEqualTo("gridType", "building_grid")
                    .andEqualTo("isDeleted", 0)
                    .andEqualTo("enabledFlag", 1);

            return doPage(page, pageSize, example);

        }
        example.createCriteria().andEqualTo("stagingId", massifId)
                .andEqualTo("gridType", "building_grid")
                .andEqualTo("isDeleted", 0)
                .andEqualTo("enabledFlag", 1);

        return new PageInfo<>(gridBasicInfoMapper.selectByExample(example));
    }

    public PageInfo<GridBasicInfo> doPage(int pageNum, int pageSize, Example example) {
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
    public CommonResult<String> disassociatedGrid(GridBasicInfoDTO gridBasicInfoDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        List<GridBasicInfoBO> gridBasicInfoBOList = gridBasicInfoDTO.getGridBasicInfoBOList();
        if (!CollectionUtils.isEmpty(gridBasicInfoBOList)) {
            String housekeeperId = gridBasicInfoBOList.get(0).getHousekeeperId();
            for (GridBasicInfoBO bo : gridBasicInfoBOList) {
                String[] arr = {bo.getId()};
                gridBasicInfoDTO.setIds(arr);
                gridBasicInfoDTO.setGridType(bo.getGridType());
                // 调用K2
                commonResult = gridApprovalRecordService.callApproval(GridOperateEnum.HOUSEKEEPER_DISASSOCIATED.getOperateType(), bo.getId(), gridBasicInfoDTO);
                if (!commonResult.getState()) {
                    return commonResult;
                }
            }
        }
        return commonResult;
    }

    @Override
    public List<RangeDTO> getGridsHouseBymassifId(String massifId) {
        List<RangeDTO> returnList = new ArrayList<>();
        List<GridBasicInfo> gridBasicInfos = this.getGridsBySmcloudmassifId(massifId);
        for (int i = 0; i < gridBasicInfos.size(); i++) {
            List<RangeDTO> listObjectFir = (List<RangeDTO>) JSONArray.parseArray(gridBasicInfos.get(i).getGridRange(), RangeDTO.class);
            if (!CollectionUtils.isEmpty(listObjectFir)) {
                returnList.addAll(listObjectFir);
            }
        }
		/*gridBasicInfos.forEach(grid -> {
			List<Map<String,Object>> listObjectFir = (List<Map<String,Object>>) JSONArray.parse(grid.getGridRange());
			returnList.addAll(listObjectFir);
			*//*returnList.addAll(JsonUtil.to)*//*
		});*/
        Set<RangeDTO> set = new HashSet<>(returnList);
        List<RangeDTO> returnList2 = new ArrayList<>(set);
        returnList2.sort(Comparator.comparing(RangeDTO::getName));

        return new ArrayList<>(returnList2);
    }

    @Override
    public List<Map<String, Object>> getGridsHouseBymassifIdPage(String massifId, Integer page, Integer pageSize) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        PageInfo<GridBasicInfo> pageInfo = this.getGridsBySmcloudmassifIdPage(massifId, page, pageSize);
        List<GridBasicInfo> gridBasicInfos = pageInfo.getList();
        gridBasicInfos.forEach(grid -> {
            List<Map<String, Object>> listObjectFir = (List<Map<String, Object>>) JSONArray.parse(grid.getGridRange());
            if (!CollectionUtils.isEmpty(listObjectFir)) {
                returnList.addAll(listObjectFir);
            }
        });

        if (org.apache.commons.lang3.StringUtils.isEmpty(massifId)) {
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
    public void handChangeRange(String gridId, String gridRange, Integer action) {
        dashBoardFeignService.handChangeRange(gridId, gridRange, action);
    }

    @Override
    public List<Map<String, Object>> getHouseByCondition(String divideId, String id, Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> gridsHouseBymassifId = getGridsHouseBymassifIdPage(divideId, pageNum, pageSize);

        List<Map<String, Object>> parentId = gridsHouseBymassifId.stream().filter(e -> String.valueOf(e.get("parentId")).equals(id)).collect(Collectors.toList());

        if (org.apache.commons.lang3.StringUtils.isEmpty(divideId)) {
            List<Map<String, Object>> pageInfo = gridsHouseBymassifId.parallelStream().filter(e -> e.get("pageInfo") != null).collect(Collectors.toList());
            Map<String, Object> map = (Map<String, Object>) pageInfo.get(0).get("pageInfo");
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(map));
            parentId.add(0, jsonObject);
        }
        return parentId;
    }

    Pattern compile = Pattern.compile("\\d+");

    public int sortListByNum(String s) {
        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group());
        }
        return 0;
    }

    @Override
    public Integer getPublicGridNum(String id) {
        Integer result = 0;
        QueryFilter qf = QueryFilter.build(GridBasicInfo.class);
        qf.addFilter("gridType", GridTypeConstants.PUBLIC_AREA_GRID, QueryOP.EQUAL, FieldRelation.AND);
        qf.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
        qf.addFilter("enabledFlag", 1, QueryOP.EQUAL, FieldRelation.AND);
        qf.addFilter("stagingId", id, QueryOP.EQUAL, FieldRelation.AND);
        PageList<GridBasicInfo> query = gridBasicInfoService.query(qf);
        if (query != null && !CollectionUtils.isEmpty(query.getRows())) {
            if (query.getRows().size() > 0) {
                result = 1;
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getByHouseId(String houseId) {
        return gridBasicInfoMapper.getByHouseId(houseId);
    }

    @Override
    public List<GridBasicInfoDTO> getByStagingId(String stagingId, String gridId) {
        if (StringUtil.isNotEmpty(gridId)) {
            return gridBasicInfoMapper.getByGridId(gridId);
        }
        List<String> list = this.stringToList(stagingId);
        return gridBasicInfoMapper.getByStagingId(list);
    }

    @Override
    public List<Map<String, Object>> getListByOrgs(String stagingId) {
        if (StringUtil.isEmpty(stagingId)) {
            return null;
        }
        List<String> list = this.stringToList(stagingId);
        return gridBasicInfoMapper.getListByOrgs(list);
    }

    private List<String> stringToList(String str) {
        str = str.replace("(", "");
        str = str.replace(")", "");
        str = str.replace("'", "");
        List<String> list = Arrays.asList(str.split(","));
        return list;
    }

	@Override
	public void realDeleteGridList(String id,String gridType) {
		if(GridTypeConstants.SERVICE_CENTER_GRID.equals(gridType)){
			StageServiceGirdRef stageServiceGirdRef=new StageServiceGirdRef();
			stageServiceGirdRef.setServiceGridId(id);
			stageServiceGirdRefMapper.delete(stageServiceGirdRef);
		}else{
			if(GridTypeConstants.BUILDING_GRID.equals(gridType)){
				String [] ids={id};
				gridRangeService.deleteRangeByGridIds(ids);
			}
			gridBasicInfoService.delete(id);
		}
	}

    @Override
    public List<GridBasicInfo> getGridByStagingId(String stagingId) {
        return this.gridBasicInfoMapper.getGridByStagingId(stagingId);
    }
}