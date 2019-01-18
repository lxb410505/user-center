package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.constant.GridTypeConstants;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
import com.hypersmart.usercenter.dto.GridRangeInfo;
import com.hypersmart.usercenter.mapper.GridBasicInfoMapper;
import com.hypersmart.usercenter.mapper.UcOrgUserMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.GridRange;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryService;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.mdm.feign.UcOrgFeignService;
import com.hypersmart.mdm.feign.UcOrgUserFeign;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.service.GridRangeService;
import com.hypersmart.usercenter.service.UcOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

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

    @Autowired
    private GridBasicInfoHistoryService gridBasicInfoHistoryService;

    @Autowired
    private UcOrgFeignService ucOrgFeignService;

    @Autowired
    private UcOrgUserFeign ucOrgUserFeign;

    @Autowired
    UcOrgUserMapper ucOrgUserMapper;

    @Autowired
    UcOrgService ucOrgService;

    @Autowired
    GridRangeService gridRangeService;


    public GridBasicInfoServiceImpl(GridBasicInfoMapper mapper) {
        super(mapper);
    }

    @Override
    public PageList<Map<String, Object>> quertList(QueryFilter queryFilter) {
        queryFilter.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);
        /**
         * 1、查询当前登录人所在的所有分期（uc_org.LEVEL_ = 4）的管家信息：
         *    接口返回以下信息：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *    支持排序的字段：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *
         */

        //1、根据用户信息获取用户所属组织
        IUser user = ContextUtil.getCurrentUser();
        //获取用户组织关系
        List<UcOrgUser> ucOrgUsersList = this.ucOrgUserMapper.getUserOrg(user.getUserId());

        //2、查找ucOrgUsersList 对应的组织下的分期，将分期id的集合作为quertList的条件。
        String orgIds = "";
        for (int i = 0; i < ucOrgUsersList.size(); i++) {
            if (i == 0) {
                orgIds = ucOrgUsersList.get(i).getOrgId();
            } else {
                orgIds = orgIds + "," + ucOrgUsersList.get(i).getOrgId();
            }
        }
        QueryFilter queryOrg = QueryFilter.build();
        if(null != ucOrgUsersList && ucOrgUsersList.size()>0){
            queryOrg.addFilter("id",orgIds,QueryOP.IN,FieldRelation.AND);
        }else{
            return new PageList<>();
        }
        PageList<UcOrg> orgList =  ucOrgService.query(queryOrg);
        Map<String,UcOrg> map = new HashMap<>();
        for(UcOrg ucOrg:orgList.getRows()){
            QueryFilter ucorgQuery = QueryFilter.build();
            ucorgQuery.addFilter("path",ucOrg.getPath(),QueryOP.RIGHT_LIKE,FieldRelation.AND);
            ucorgQuery.addFilter("level",5,QueryOP.EQUAL,FieldRelation.AND);
            PageList<UcOrg> divideList = ucOrgService.query(ucorgQuery);
            if(null != divideList && null != divideList.getRows() && divideList.getRows().size()>0){
                for(int i=0;i< divideList.getRows().size();i++){
                    UcOrg temp = divideList.getRows().get(i);
                    map.put(temp.getId(),temp);
                }
            }
        }
        Set<String> set = map.keySet();
        if(null != set && set.size()>0){
            String divideId = "";
            for(String index: set){
                if("".equals(divideId)){
                    divideId = index;
                }else{
                    divideId = divideId +","+ index;
                }
            }
            queryFilter.addFilter("divideId", divideId, QueryOP.IN, FieldRelation.AND,"two");
        }else{
            return new PageList<>();
        }

        //===============================================================================================
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }

        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        List<Map<String,Object>> query = this.gridBasicInfoMapper.quertList(queryFilter.getParams());
        return new PageList<>(query);
    }

    /**
     * 查询网格基础信息
     *
     * @param queryFilter
     * @return
     */
    @Override
    public PageList<GridBasicInfoDTO> selectGridBasicInfo(QueryFilter queryFilter) {

        queryFilter.addFilter("isDeleted", 0, QueryOP.EQUAL, FieldRelation.AND);

        //封装查询
        PageList<GridBasicInfo> gridBasicInfoPageList = this.query(queryFilter);
        //转DTO
        PageList<GridBasicInfoDTO> gridBasicInfoDTOPageList = new PageList<>();
        List<GridBasicInfoDTO> rows = new ArrayList<>();
        gridBasicInfoDTOPageList.setPage(gridBasicInfoPageList.getPage());
        gridBasicInfoDTOPageList.setPageSize(gridBasicInfoPageList.getPageSize());
        gridBasicInfoDTOPageList.setTotal(gridBasicInfoPageList.getTotal());
        List<GridBasicInfo> gridBasicInfoList = gridBasicInfoPageList.getRows();
        //存储组织id集合
        Set<String> orgIdList = new HashSet<>();
        //存储管家id集合
        Set<String> housekeeperIdList = new HashSet<>();
        if (!CollectionUtils.isEmpty(gridBasicInfoList)) {
            for (GridBasicInfo gridBasicInfo : gridBasicInfoList) {
                //存储组织id集合
                orgIdList.add(gridBasicInfo.getAreaId());
                orgIdList.add(gridBasicInfo.getProjectId());
                orgIdList.add(gridBasicInfo.getCityId());
                orgIdList.add(gridBasicInfo.getMassifId());
                orgIdList.add(gridBasicInfo.getStagingId());
                if (!StringUtils.isEmpty(gridBasicInfo.getCityId())) {
                    //存储管家id集合
                    if (!StringUtils.isEmpty(gridBasicInfo.getHousekeeperId())) {
                        housekeeperIdList.add(gridBasicInfo.getHousekeeperId());
                    }
                }
            }
        }
        //管家入参
        Map<String, String> housekeeperMap = new HashMap<>();
        //存储管家ids
        String ids = null;

        PageList<JsonNode> pageList = new PageList<>();
        if (!CollectionUtils.isEmpty(housekeeperIdList)) {
            QueryFilter queryFilterQ = QueryFilter.build();
            ids = String.join(",", housekeeperIdList);
            queryFilterQ.addFilter("houseKeeperId",ids,QueryOP.IN,FieldRelation.AND);
            pageList = ucOrgUserFeign.queryList(queryFilterQ);
        }
        //组装管家map
        if (pageList != null && !CollectionUtils.isEmpty(pageList.getRows())) {
            List<JsonNode> jsonNodeList = pageList.getRows();
            for (JsonNode jsonNode : jsonNodeList) {
                JsonNode s = jsonNode.get("houseKeeperId");
                String id = s.asText();
                if (!StringUtils.isEmpty(id)) {
                    housekeeperMap.put(id, jsonNode.get("fullName").asText());
                }
            }
        }

        //组织入参
        Map<String, String> orgMap = new HashMap<>();
        Map<String, List<String>> orgQueryMap = new HashMap<>();
        orgQueryMap.put("list", new ArrayList<>(orgIdList));

        //组织集合
        List<JsonNode> orgList = ucOrgFeignService.getByList(orgQueryMap);
        if (!CollectionUtils.isEmpty(orgList)) {
            for (JsonNode jsonNode : orgList) {
                JsonNode s = jsonNode.get("id");
                String id = s.asText();
                if (!StringUtils.isEmpty(id)) {
                    orgMap.put(id, jsonNode.get("name").asText());
                }
            }
        }
        if (!CollectionUtils.isEmpty(gridBasicInfoList)) {
            for (GridBasicInfo gridBasicInfo : gridBasicInfoList) {
                GridBasicInfoDTO gridBasicInfoDTO = new GridBasicInfoDTO();
                gridBasicInfoDTO.setId(gridBasicInfo.getId());
                gridBasicInfoDTO.setAreaId(gridBasicInfo.getAreaId());
                gridBasicInfoDTO.setCityId(gridBasicInfo.getCityId());
                gridBasicInfoDTO.setProjectId(gridBasicInfo.getProjectId());
                gridBasicInfoDTO.setMassifId(gridBasicInfo.getMassifId());
                gridBasicInfoDTO.setStagingId(gridBasicInfo.getStagingId());
                gridBasicInfoDTO.setGridName(gridBasicInfo.getGridName());
                gridBasicInfoDTO.setGridCode(gridBasicInfo.getGridCode());
                gridBasicInfoDTO.setGridType(gridBasicInfo.getGridType());
                gridBasicInfoDTO.setGridRemark(gridBasicInfo.getGridRemark());
                gridBasicInfoDTO.setFormatAttribute(gridBasicInfo.getFormatAttribute());
                gridBasicInfoDTO.setHousekeeperId(gridBasicInfo.getHousekeeperId());
                gridBasicInfoDTO.setCreatedBy(gridBasicInfo.getCreatedBy());
                gridBasicInfoDTO.setCreationDate(gridBasicInfo.getCreationDate());
                gridBasicInfoDTO.setEnabledFlag(gridBasicInfo.getEnabledFlag());
                //返回组织数据
                if (orgMap != null) {
                    gridBasicInfoDTO.setAreaId(orgMap.get(gridBasicInfoDTO.getAreaId()));
                    gridBasicInfoDTO.setProjectId(orgMap.get(gridBasicInfoDTO.getProjectId()));
                    gridBasicInfoDTO.setMassifId(orgMap.get(gridBasicInfoDTO.getMassifId()));
                    gridBasicInfoDTO.setStagingId(orgMap.get(gridBasicInfoDTO.getStagingId()));
                    gridBasicInfoDTO.setCityId(orgMap.get(gridBasicInfoDTO.getCityId()));
                    //不支持表头排序，作废
//                    gridBasicInfoDTO.setAreaName(orgMap.get(gridBasicInfoDTO.getAreaId()));
//                    gridBasicInfoDTO.setProjectName(orgMap.get(gridBasicInfoDTO.getProjectId()));
//                    gridBasicInfoDTO.setMassifName(orgMap.get(gridBasicInfoDTO.getMassifId()));
//                    gridBasicInfoDTO.setStagingName(orgMap.get(gridBasicInfoDTO.getStagingId()));
                }
                if (housekeeperMap != null) {
                    gridBasicInfoDTO.setHousekeeperId(housekeeperMap.get(gridBasicInfoDTO.getHousekeeperId()));
                }
                rows.add(gridBasicInfoDTO);
            }
        }
        gridBasicInfoDTOPageList.setRows(rows);
        return gridBasicInfoDTOPageList;
    }

    /**
     * 根据id获取网格
     *
     * @param id
     * @return
     */
    @Override
    public GridBasicInfoDTO getGridById(String id) {
        GridBasicInfo gridBasicInfo = this.get(id);
        //存储组织id集合
        Set<String> orgIdList = new HashSet<>();
        //存储管家id集合
        orgIdList.add(gridBasicInfo.getAreaId());
        orgIdList.add(gridBasicInfo.getProjectId());
        orgIdList.add(gridBasicInfo.getMassifId());
        orgIdList.add(gridBasicInfo.getStagingId());
        orgIdList.add(gridBasicInfo.getCityId());
        //管家入参
        Map<String, String> housekeeperMap = new HashMap<>();
        //存储管家List
        PageList<JsonNode> pageList = new PageList<>();
        if (!StringUtils.isEmpty(gridBasicInfo.getHousekeeperId())) {
            QueryField queryField = new QueryField();
            QueryFilter queryFilterQ = QueryFilter.build();
            List<QueryField> querys = new ArrayList<>();
            queryField.setProperty("houseKeeperId");
            queryField.setValue(gridBasicInfo.getHousekeeperId());
            queryField.setOperation(QueryOP.EQUAL);
            queryField.setRelation(FieldRelation.AND);
            querys.add(queryField);
            queryFilterQ.setQuerys(querys);
            pageList = ucOrgUserFeign.queryList(queryFilterQ);
        }

        //组织入参
        Map<String, String> orgMap = new HashMap<>();
        Map<String, List<String>> orgQueryMap = new HashMap<>();
        orgQueryMap.put("list", new ArrayList<>(orgIdList));

        //组织集合
        List<JsonNode> orgList = ucOrgFeignService.getByList(orgQueryMap);
        if (!CollectionUtils.isEmpty(orgList)) {
            for (JsonNode jsonNode : orgList) {
                JsonNode s = jsonNode.get("id");
                String orgId = s.asText();
                if (!StringUtils.isEmpty(id)) {
                    orgMap.put(orgId, jsonNode.get("name").asText());
                }
            }
        }
        //返回DTO
        GridBasicInfoDTO gridBasicInfoDTO = new GridBasicInfoDTO();
        gridBasicInfoDTO.setId(gridBasicInfo.getId());
        gridBasicInfoDTO.setAreaId(gridBasicInfo.getAreaId());
        gridBasicInfoDTO.setProjectId(gridBasicInfo.getProjectId());
        gridBasicInfoDTO.setMassifId(gridBasicInfo.getMassifId());
        gridBasicInfoDTO.setStagingId(gridBasicInfo.getStagingId());
        gridBasicInfoDTO.setCityId(gridBasicInfo.getCityId());
        gridBasicInfoDTO.setGridName(gridBasicInfo.getGridName());
        gridBasicInfoDTO.setGridCode(gridBasicInfo.getGridCode());
        gridBasicInfoDTO.setGridType(gridBasicInfo.getGridType());
        gridBasicInfoDTO.setGridRemark(gridBasicInfo.getGridRemark());
        gridBasicInfoDTO.setGridRange(gridBasicInfo.getGridRange());
        gridBasicInfoDTO.setFormatAttribute(gridBasicInfo.getFormatAttribute());
        gridBasicInfoDTO.setHousekeeperId(gridBasicInfo.getHousekeeperId());
        gridBasicInfoDTO.setCreatedBy(gridBasicInfo.getCreatedBy());
        gridBasicInfoDTO.setCreationDate(gridBasicInfo.getCreationDate());
        gridBasicInfoDTO.setEnabledFlag(gridBasicInfo.getEnabledFlag());
        //返回组织数据
        if (orgMap != null) {
            gridBasicInfoDTO.setAreaName(orgMap.get(gridBasicInfoDTO.getAreaId()));
            gridBasicInfoDTO.setProjectName(orgMap.get(gridBasicInfoDTO.getProjectId()));
            gridBasicInfoDTO.setMassifName(orgMap.get(gridBasicInfoDTO.getMassifId()));
            gridBasicInfoDTO.setStagingName(orgMap.get(gridBasicInfoDTO.getStagingId()));
            gridBasicInfoDTO.setCityName(orgMap.get(gridBasicInfoDTO.getCityId()));
        }
        //返回管家信息
        if (pageList != null && !CollectionUtils.isEmpty(pageList.getRows())) {
            JsonNode jsonNode = pageList.getRows().get(0);
            gridBasicInfoDTO.setHousekeeperName(jsonNode.get("fullName").asText());
        }
        return gridBasicInfoDTO;
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
            GridBasicInfo gridBasicInfo = new GridBasicInfo();
            gridBasicInfo.setId(UUID.randomUUID().toString());
            gridBasicInfo.setAreaId(gridBasicInfoDTO.getAreaId());
            gridBasicInfo.setCityId(gridBasicInfoDTO.getCityId());
            gridBasicInfo.setProjectId(gridBasicInfoDTO.getProjectId());
            gridBasicInfo.setMassifId(gridBasicInfoDTO.getMassifId());
            gridBasicInfo.setStagingId(gridBasicInfoDTO.getStagingId());
            gridBasicInfo.setGridCode(gridBasicInfoDTO.getGridCode());
            gridBasicInfo.setGridName(gridBasicInfoDTO.getGridName());
            if (GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoDTO.getGridType())) {
                gridBasicInfo.setGridRange(gridBasicInfoDTO.getGridRange());
            }
            gridBasicInfo.setGridRemark(gridBasicInfoDTO.getGridRemark());
            gridBasicInfo.setGridType(gridBasicInfoDTO.getGridType());
            gridBasicInfo.setHousekeeperId(gridBasicInfoDTO.getHousekeeperId());
            gridBasicInfo.setFormatAttribute(gridBasicInfoDTO.getFormatAttribute());
            gridBasicInfo.setCreationDate(new Date());
            gridBasicInfo.setUpdationDate(new Date());
            //网格范围
            gridBasicInfo.setGridRange(gridBasicInfoDTO.getGridRange());
            //新增
            num = this.insertSelective(gridBasicInfo);
            //网格覆盖范围表记录
            if(GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoDTO.getGridType()) && !StringUtils.isEmpty(gridBasicInfoDTO.getGridRange())) {
                //记录数据
                gridRangeService.recordRange(gridBasicInfoDTO.getGridRange(),gridBasicInfo.getId());
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
            //只要记录历史记录updateTimes都加1
            gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
            gridBasicInfo.setUpdationDate(new Date());
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
        Integer num = 0;
        //获取对应id旧的的网格数据
        GridBasicInfo gridBasicInfoOld = this.get(gridBasicInfoDTO.getId());
        //历史数据存储(变更前) Todo
        //历史数据存储(变更后) Todo
        //变更类型历史记录 Todo
        //管家变更历史记录 Todo
        //变更管家
        GridBasicInfo gridBasicInfo = new GridBasicInfo();
        gridBasicInfo.setId(gridBasicInfoDTO.getId());
        gridBasicInfo.setHousekeeperId(gridBasicInfoDTO.getHousekeeperId());
        gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
        gridBasicInfo.setUpdationDate(new Date());
        num = this.updateSelective(gridBasicInfo);
        if (num < 1) {
            gridErrorCode = GridErrorCode.UPDATE_EXCEPTION;
        }
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
        Integer num = 0;
        //获取对应id旧的的网格数据
        GridBasicInfo gridBasicInfoOld = this.get(gridBasicInfoDTO.getId());
        //历史数据存储(变更前) Todo
        //历史数据存储(变更后) Todo
        //楼栋数据存储 Todo
        //变更楼栋
        GridBasicInfo gridBasicInfo = new GridBasicInfo();
        gridBasicInfo.setId(gridBasicInfoDTO.getId());
        gridBasicInfo.setGridRange(gridBasicInfoDTO.getGridRange());
        gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
        gridBasicInfo.setUpdationDate(new Date());
        num = this.updateSelective(gridBasicInfo);

//        //网格覆盖范围表记录
//        if(!StringUtils.isEmpty(gridBasicInfoDTO.getGridRange())) {
//
//        }
        //删除旧的网格覆盖范围数据
        gridRangeService.deleteRangeByGridId(gridBasicInfoDTO.getId());
        //记录数据
        gridRangeService.recordRange(gridBasicInfoDTO.getGridRange(),gridBasicInfoDTO.getId());
        if (num < 1) {
            gridErrorCode = GridErrorCode.UPDATE_EXCEPTION;
        }
        return gridErrorCode;
    }

    /**
     * 批量禁用网格
     *
     * @param gridBasicInfoBO
     * @return
     */
    @Override
    public GridErrorCode disableGridList(GridBasicInfoBO gridBasicInfoBO) {
        GridErrorCode gridErrorCode = GridErrorCode.SUCCESS;
        Integer num = 0;
        //获取对应id旧的的网格数据
        GridBasicInfo gridBasicInfoOld = new GridBasicInfo();
        List<GridBasicInfo> gridBasicInfoList = new ArrayList<>();
        //循环更新数据
        if (gridBasicInfoBO.getIds() != null && gridBasicInfoBO.getIds().length > 0) {
            for (String id : gridBasicInfoBO.getIds()) {
                gridBasicInfoOld = this.get(id);
                //历史数据存储(变更前) Todo
                //历史数据存储(变更后) Todo
                GridBasicInfo gridBasicInfo = new GridBasicInfo();
                gridBasicInfo.setId(id);
                gridBasicInfo.setEnabledFlag(0);
                gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
                gridBasicInfo.setUpdationDate(new Date());
                //如果是楼栋网格则释放映射信息
                if (GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoOld.getGridType())) {
                    gridBasicInfo.setGridRange(null);
                }
                //只禁用已启用的
                if (gridBasicInfoOld.getEnabledFlag().equals(1)) {
                    gridBasicInfoList.add(gridBasicInfo);
                }
                //删除旧的网格覆盖范围数据
                gridRangeService.deleteRangeByGridId(gridBasicInfoBO.getId());
            }
        }
        //批量更新
        if (!CollectionUtils.isEmpty(gridBasicInfoList)) {
            num = this.updateBatchSelective(gridBasicInfoList);
        }
        if (num < 1) {
            gridErrorCode = GridErrorCode.DISABLE_EXCEPTION;
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
        //获取对应id旧的的网格数据
        GridBasicInfo gridBasicInfoOld = new GridBasicInfo();
        List<GridBasicInfo> gridBasicInfoList = new ArrayList<>();
        //循环更新数据
        if (gridBasicInfoBO.getIds() != null && gridBasicInfoBO.getIds().length > 0) {
            for (String id : gridBasicInfoBO.getIds()) {
                gridBasicInfoOld = this.get(id);
                //历史数据存储(变更前) Todo
                //历史数据存储(变更后) Todo
                GridBasicInfo gridBasicInfo = new GridBasicInfo();
                gridBasicInfo.setId(id);
                gridBasicInfo.setIsDeleted(1);
                gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
                gridBasicInfo.setUpdationDate(new Date());
                //如果是楼栋网格则释放映射信息
                if (GridTypeConstants.BUILDING_GRID.equals(gridBasicInfoOld.getGridType())) {
                    gridBasicInfo.setGridRange(null);
                }
                //只删除未删除的
                if (gridBasicInfoOld.getIsDeleted().equals(0)) {
                    gridBasicInfoList.add(gridBasicInfo);
                }
                //删除旧的网格覆盖范围数据
                gridRangeService.deleteRangeByGridId(gridBasicInfoBO.getId());
            }
        }
        //批量更新
        if (!CollectionUtils.isEmpty(gridBasicInfoList)) {
            num = this.updateBatchSelective(gridBasicInfoList);
        }
        if (num < 1) {
            gridErrorCode = GridErrorCode.DELETE_EXCEPTION;
        }
        return gridErrorCode;
    }

    /**
     * 组装返回数据
     *
     * @param gridBasicInfoDTOList
     * @param query
     */
    private void assembleData(List<GridBasicInfoDTO> gridBasicInfoDTOList, GridBasicInfoBO query) {

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
}