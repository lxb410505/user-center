package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.GridRangeBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.constant.GridTypeConstants;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
import com.hypersmart.usercenter.mapper.GridBasicInfoMapper;
import com.hypersmart.usercenter.mapper.UcOrgUserMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
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


    /**
     * 网格分页查询
     * @param queryFilter
     * @return
     */
    @Override
    public PageList<Map<String, Object>> quertList(QueryFilter queryFilter) {
        /**
         * 1、查询当前登录人所在的所有分期（uc_org.LEVEL_ = 4）的管家信息：
         *    接口返回以下信息：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *    支持排序的字段：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *
         */
        /**
         * 地块id从前台传入，无须在后台过滤数据权限
         */
//        //1、根据用户信息获取用户所属组织
//        IUser user = ContextUtil.getCurrentUser();
//        //获取用户组织关系
//        List<UcOrgUser> ucOrgUsersList = this.ucOrgUserMapper.getUserOrg(user.getUserId());
//
//        //2、查找ucOrgUsersList 对应的组织下的分期，将分期id的集合作为quertList的条件。
//        String orgIds = "";
//        for (int i = 0; i < ucOrgUsersList.size(); i++) {
//            if (i == 0) {
//                orgIds = ucOrgUsersList.get(i).getOrgId();
//            } else {
//                orgIds = orgIds + "," + ucOrgUsersList.get(i).getOrgId();
//            }
//        }
//        QueryFilter queryOrg = QueryFilter.build();
//        if(null != ucOrgUsersList && ucOrgUsersList.size()>0){
//            queryOrg.addFilter("id",orgIds,QueryOP.IN,FieldRelation.AND);
//        }else{
//            PageList<Map<String, Object>> pageList = new PageList();
//            pageList.setTotal(0);
//            if(BeanUtils.isEmpty(queryFilter.getPageBean())){
//                pageList.setPage(1);
//                pageList.setPageSize(10);
//                pageList.setRows(new ArrayList<>());
//                pageList.setTotal(0);
//            }else{
//                pageList.setPage(queryFilter.getPageBean().getPage());
//                pageList.setPageSize(queryFilter.getPageBean().getPageSize());
//                pageList.setRows(new ArrayList<>());
//                pageList.setTotal(0);
//            }
//            return pageList;
//        }
//        PageList<UcOrg> orgList =  ucOrgService.query(queryOrg);
//        Map<String,UcOrg> map = new HashMap<>();
//        for(UcOrg ucOrg:orgList.getRows()){
//            QueryFilter ucorgQuery = QueryFilter.build();
//            ucorgQuery.addFilter("path",ucOrg.getPath(),QueryOP.RIGHT_LIKE,FieldRelation.AND);
//            ucorgQuery.addFilter("level",4,QueryOP.EQUAL,FieldRelation.AND);
//            PageList<UcOrg> divideList = ucOrgService.query(ucorgQuery);
//            if(null != divideList && null != divideList.getRows() && divideList.getRows().size()>0){
//                for(int i=0;i< divideList.getRows().size();i++){
//                    UcOrg temp = divideList.getRows().get(i);
//                    map.put(temp.getId(),temp);
//                }
//            }
//        }
//        Set<String> set = map.keySet();
//        if(null != set && set.size()>0){
//            String divideId = "";
//            for(String index: set){
//                if("".equals(divideId)){
//                    divideId = index;
//                }else{
//                    divideId = divideId +","+ index;
//                }
//            }
//            queryFilter.addFilter("divideId", divideId, QueryOP.IN, FieldRelation.AND,"two");
//        }else{
//            PageList<Map<String, Object>> pageList = new PageList();
//            pageList.setTotal(0);
//            if(BeanUtils.isEmpty(queryFilter.getPageBean())){
//                pageList.setPage(1);
//                pageList.setPageSize(10);
//                pageList.setRows(new ArrayList<>());
//                pageList.setTotal(0);
//            }else{
//                pageList.setPage(queryFilter.getPageBean().getPage());
//                pageList.setPageSize(queryFilter.getPageBean().getPageSize());
//                pageList.setRows(new ArrayList<>());
//                pageList.setTotal(0);
//            }
//            return pageList;
//        }


        Object orgId = ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);

        if (orgId != null) {
            queryFilter.getParams().put("massifId",orgId.toString());
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
        //===============================================================================================
        PageBean pageBean = queryFilter.getPageBean();
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
     * 根据id获取网格
     *
     * @param id
     * @return
     */
    @Override
    public Map<String,Object> getGridById(String id) {
        Map<String,Object> objectMap = gridBasicInfoMapper.getGridById(id);
        objectMap.put("allRange",null);
        if(objectMap.get("id") != null && objectMap.get("stagingId") != null) {
            GridRangeBO gridRangeBO = new GridRangeBO();
            gridRangeBO.setGridId(objectMap.get("id").toString());
            gridRangeBO.setStagingId(objectMap.get("stagingId").toString());
            List<String> rangeList = gridRangeService.getAllRange(gridRangeBO);
            objectMap.put("allRange",rangeList);
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
            if(StringUtil.isNotEmpty(gridBasicInfoDTO.getGridRange())) {
                boolean flag = gridRangeService.judgeExistHouse(gridBasicInfoDTO.getGridRange(),null,gridBasicInfoDTO.getStagingId());
                if(flag) {
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
            if("".equals(gridBasicInfoDTO.getHousekeeperId())) {
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
            if(StringUtil.isEmpty(gridBasicInfoDTO.getSecondFormatAttribute())) {
                gridBasicInfo.setSecondFormatAttribute("");
            } else {
                gridBasicInfo.setSecondFormatAttribute(gridBasicInfoDTO.getSecondFormatAttribute());
            }
            if(StringUtil.isEmpty(gridBasicInfoDTO.getThirdFormatAttribute())) {
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
        if("".equals(gridBasicInfoDTO.getHousekeeperId())) {
            gridBasicInfo.setHousekeeperId(null);
        } else {
            gridBasicInfo.setHousekeeperId(gridBasicInfoDTO.getHousekeeperId());
        }
        gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
        gridBasicInfo.setUpdationDate(new Date());
        gridBasicInfo.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
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
        //判断房产是否已经被覆盖
        if(StringUtil.isNotEmpty(gridBasicInfoDTO.getGridRange())) {
            boolean flag = gridRangeService.judgeExistHouse(gridBasicInfoDTO.getGridRange(),gridBasicInfoDTO.getId(),gridBasicInfoOld.getStagingId());
            if(flag) {
                gridErrorCode = GridErrorCode.INSERT_EXCEPTION;
                gridErrorCode.setMessage("存在已被覆盖的房产信息！");
                return gridErrorCode;
            }
        }
        //变更楼栋
        GridBasicInfo gridBasicInfo = new GridBasicInfo();
        gridBasicInfo.setId(gridBasicInfoDTO.getId());
        gridBasicInfo.setGridRange(gridBasicInfoDTO.getGridRange());
        gridBasicInfo.setUpdateTimes(gridBasicInfoOld.getUpdateTimes() + 1);
        gridBasicInfo.setUpdationDate(new Date());
        gridBasicInfo.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
        num = this.updateSelective(gridBasicInfo);

//        //网格覆盖范围表记录
//        if(!StringUtils.isEmpty(gridBasicInfoDTO.getGridRange())) {
//
//        }
        String[] ids = new String[1];
        ids[0] = gridBasicInfoDTO.getId();
        //删除旧的网格覆盖范围数据
        gridRangeService.deleteRangeByGridIds(ids);
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
        gridBasicInfoBO.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
        Integer num = 0;
        //主表更新
        num = gridBasicInfoMapper.disableGridInfo(gridBasicInfoBO);
        //范围表更新
        gridRangeService.deleteRangeByGridIds(gridBasicInfoBO.getIds());
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
        gridBasicInfoBO.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
        //主表更新
        num = gridBasicInfoMapper.deleteGridInfo(gridBasicInfoBO);
        //范围表更新
        gridRangeService.deleteRangeByGridIds(gridBasicInfoBO.getIds());
        if (num < 1) {
            gridErrorCode = GridErrorCode.DELETE_EXCEPTION;
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
}