package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoHistoryDTO;
import com.hypersmart.usercenter.mapper.GridBasicInfoHistoryMapper;
import com.hypersmart.usercenter.mapper.StageServiceGirdRefMapper;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.usercenter.service.*;
import com.hypersmart.usercenter.util.GridTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网格基础信息历史表-变化前快照
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:03:42
 */
@Service("gridBasicInfoHistoryServiceImpl")
public class GridBasicInfoHistoryServiceImpl extends GenericService<String, GridBasicInfoHistory> implements GridBasicInfoHistoryService {

    @Autowired
    private GridBasicInfoService gridBasicInfoService;

    @Autowired
    private GridBasicInfoHistoryAfterService gridBasicInfoHistoryAfterService;

    @Autowired
    private UcOrgService ucOrgService;

    @Autowired
    private GridHistoryChangeTypeRefService gridHistoryChangeTypeRefService;

    @Autowired
    private HousekeeperHistoryService housekeeperHistoryService;

    @Autowired
    private UcUserService ucUserService;

    @Autowired
    private UcOrgUserService ucOrgUserService;

    @Autowired
    private UcOrgPostService ucOrgPostService;

    @Autowired
    private GridBasicInfoHistoryMapper gridBasicInfoHistoryMapper;

    @Resource
    private StageServiceGirdRefMapper stageServiceGirdRefMapper;

    public GridBasicInfoHistoryServiceImpl(GridBasicInfoHistoryMapper mapper) {
        super(mapper);
    }


    @Override
    public GridErrorCode saveGridBasicInfoHistory(GridBasicInfo gridBasicInfoDTO,Integer changeType) {
        GridErrorCode gridErrorCode = GridErrorCode.UNKOWN_EXCEPTION;
        IUser user = ContextUtil.getCurrentUser();
        Integer num = 0;
        if (gridBasicInfoDTO != null) {
            //之前网格记录
            GridBasicInfo beforeGrid = gridBasicInfoService.get(gridBasicInfoDTO.getId());

            if (beforeGrid == null) {
                return GridErrorCode.RESOURCE_NOT_FOUND;
            }
            if(GridTypeEnum.SERVICE_CENTER_GRID.getGridType().equals(beforeGrid.getGridType())){
                Map<String, Object> serviceGridIdByStagingId = stageServiceGirdRefMapper.getServiceGridIdByStagingId(beforeGrid.getStagingId());
                if(serviceGridIdByStagingId!=null){
                    if(serviceGridIdByStagingId.get("service_grid_id")!=null){
                        beforeGrid.setId(String.valueOf(serviceGridIdByStagingId.get("service_grid_id")));
                        beforeGrid.setGridName(String.valueOf(serviceGridIdByStagingId.get("service_grid_name")));
                        beforeGrid.setGridCode(String.valueOf(serviceGridIdByStagingId.get("service_grid_code")));
                    }

                }
            }
            String before_housekeeper_history_id = null;
            String after_housekeeper_history_id = null;
           /* changeType  0:管家变更     1:覆盖范围变更
              一开始没有管家  后来也没有管家             管家表里不入数据（范围变更）
              一开始有管家    后来有管家（管家没变）     管家表入一条数据（范围变更）
              一开始有管家    后来有管家（管家变更）     管家表入两条数据           （管家变更）
              一开始没有管家  后来有管家                 管家表里入后来的管家的数据  (管家变更)
              一开始有管家    后来没有管家               管家表入一开始的管家数据   （管家变更）*/

            if (StringUtils.isNotRealEmpty(beforeGrid.getHousekeeperId())) {
                UcUser ucUser = ucUserService.get(beforeGrid.getHousekeeperId());
                if (ucUser != null) {
                    HousekeeperHistory housekeeperHistory = new HousekeeperHistory();
                    housekeeperHistory.setEmail(ucUser.getEmail());
                    housekeeperHistory.setIsDeleted(0);
                    housekeeperHistory.setEnabledFlag(1);
                    housekeeperHistory.setMobilePhone(ucUser.getMobile());
                    housekeeperHistory.setUserAccount(ucUser.getAccount());
                    housekeeperHistory.setUserId(ucUser.getId());
                    housekeeperHistory.setUserName(ucUser.getFullname());
                    UcOrgUser ucOrgUser = ucOrgUserService.getByUserIdAndOrgId(beforeGrid.getHousekeeperId(),beforeGrid.getStagingId());
                    if (ucOrgUser != null && StringUtils.isNotRealEmpty(ucOrgUser.getPosId())){
                        UcOrgPost ucOrgPost = ucOrgPostService.get(ucOrgUser.getPosId());
                        if (ucOrgPost != null){
                            if ("junior".equals(ucOrgPost.getPostKey())){
                                housekeeperHistory.setUserLevel("junior");
                            }
                            if ("intermediate".equals(ucOrgPost.getPostKey())){
                                housekeeperHistory.setUserLevel("intermediate");
                            }
                            if ("senior".equals(ucOrgPost.getPostKey())){
                                housekeeperHistory.setUserLevel("senior");
                            }
                        }
                    }
                    housekeeperHistory.setCreationDate(new Date());
                    housekeeperHistory.setCreatedBy(user.getUserId());
                    housekeeperHistory.setUpdationDate(new Date());
                    housekeeperHistory.setUpdatedBy(user.getUserId());
                    housekeeperHistoryService.insert(housekeeperHistory);
                    before_housekeeper_history_id = housekeeperHistory.getId();
                }
            }

            //除了再次更新，别的情况区划信息调接口获取或前台返回
            //记录grid_basic_info_history
            GridBasicInfoHistory gridBasicInfoHistory = new GridBasicInfoHistory();
            gridBasicInfoHistory.setGridId(beforeGrid.getId());
            gridBasicInfoHistory.setGridCode(beforeGrid.getGridCode());
            gridBasicInfoHistory.setGridName(beforeGrid.getGridName());
            gridBasicInfoHistory.setGridRange(beforeGrid.getGridRange());
            gridBasicInfoHistory.setGridType(beforeGrid.getGridType());
            gridBasicInfoHistory.setGridRemark(beforeGrid.getGridRemark());
            //区域
            gridBasicInfoHistory.setAreaId(beforeGrid.getAreaId());
            if (StringUtils.isNotRealEmpty(beforeGrid.getAreaId())) {
                UcOrg beforeArea = ucOrgService.get(beforeGrid.getAreaId());
                if (beforeArea != null) {
                    gridBasicInfoHistory.setAreaCode(beforeArea.getCode());
                    gridBasicInfoHistory.setAreaName(beforeArea.getName());
                }
            }
            //城市
            gridBasicInfoHistory.setCityId(beforeGrid.getCityId());
            if (StringUtils.isNotRealEmpty(beforeGrid.getCityId())) {
                UcOrg beforeCity = ucOrgService.get(beforeGrid.getCityId());
                if (beforeCity != null) {
                    gridBasicInfoHistory.setCityCode(beforeCity.getCode());
                    gridBasicInfoHistory.setCityName(beforeCity.getName());
                }
            }
            //项目
            gridBasicInfoHistory.setProjectId(beforeGrid.getProjectId());
            if (StringUtils.isNotRealEmpty(beforeGrid.getProjectId())) {
                UcOrg beforeProject = ucOrgService.get(beforeGrid.getProjectId());
                if (beforeGrid != null) {
                    gridBasicInfoHistory.setProjectCode(beforeProject.getCode());
                    gridBasicInfoHistory.setProjectName(beforeProject.getName());
                }
            }
            //地块
            gridBasicInfoHistory.setMassifId(beforeGrid.getMassifId());
            if (StringUtils.isNotRealEmpty(beforeGrid.getMassifId())) {
                UcOrg beforeMassif = ucOrgService.get(beforeGrid.getMassifId());
                if (beforeMassif != null) {
                    gridBasicInfoHistory.setMassifCode(beforeMassif.getCode());
                    gridBasicInfoHistory.setMassifName(beforeMassif.getName());
                }
            }
            //分期
            gridBasicInfoHistory.setStagingId(beforeGrid.getStagingId());
            if (StringUtils.isNotRealEmpty(beforeGrid.getStagingId())) {
                UcOrg beforeStaging = ucOrgService.get(beforeGrid.getStagingId());
                if (beforeStaging != null) {
                    gridBasicInfoHistory.setStagingCode(beforeStaging.getCode());
                    gridBasicInfoHistory.setStagingName(beforeStaging.getName());
                }
            }
            gridBasicInfoHistory.setHousekeeperHistoryId(before_housekeeper_history_id);
            gridBasicInfoHistory.setFormatAttribute(beforeGrid.getFormatAttribute());
            gridBasicInfoHistory.setSecondFormatAttribute(beforeGrid.getSecondFormatAttribute());
            gridBasicInfoHistory.setThirdFormatAttribute(beforeGrid.getThirdFormatAttribute());
            gridBasicInfoHistory.setEnabledFlag(1);
            gridBasicInfoHistory.setIsDeleted(0);
            gridBasicInfoHistory.setCreationDate(new Date());
            gridBasicInfoHistory.setUpdationDate(new Date());
            gridBasicInfoHistory.setCreatedBy(user.getUserId());
            gridBasicInfoHistory.setUpdatedBy(user.getUserId());
            //新增
            num = this.insertSelective(gridBasicInfoHistory);


            if (num > 0) {
                //变更管家 还是变更覆盖范围
                //String after_housekeeper_history_id = null;
                Boolean housekeeperChange = false;
                    if (changeType == 0) {
                        //管家变更
                        //调用管家接口获取管家数据，记录housekeeper_history获取after_housekeeper_history_id
                        housekeeperChange = true;
                        GridHistoryChangeTypeRef gridHistoryChangeTypeRef = new GridHistoryChangeTypeRef();
                        gridHistoryChangeTypeRef.setGridHistoryId(gridBasicInfoHistory.getId());
                        gridHistoryChangeTypeRef.setEnabledFlag(1);
                        gridHistoryChangeTypeRef.setIsDeleted(0);
                        gridHistoryChangeTypeRef.setGridChangeType("housekeeper_change");
                        gridHistoryChangeTypeRef.setCreationDate(new Date());
                        gridHistoryChangeTypeRef.setUpdationDate(new Date());
                        gridHistoryChangeTypeRef.setCreatedBy(user.getUserId());
                        gridHistoryChangeTypeRef.setUpdatedBy(user.getUserId());
                        gridHistoryChangeTypeRefService.insert(gridHistoryChangeTypeRef);
                    } else {
                        //网格覆盖范围变更
                        after_housekeeper_history_id = before_housekeeper_history_id;
                        GridHistoryChangeTypeRef gridHistoryChangeTypeRef = new GridHistoryChangeTypeRef();
                        gridHistoryChangeTypeRef.setGridHistoryId(gridBasicInfoHistory.getId());
                        gridHistoryChangeTypeRef.setEnabledFlag(1);
                        gridHistoryChangeTypeRef.setIsDeleted(0);
                        gridHistoryChangeTypeRef.setGridChangeType("grid_range_change");
                        gridHistoryChangeTypeRef.setCreationDate(new Date());
                        gridHistoryChangeTypeRef.setUpdationDate(new Date());
                        gridHistoryChangeTypeRef.setUpdatedBy(user.getUserId());
                        gridHistoryChangeTypeRef.setCreatedBy(user.getUserId());
                        gridHistoryChangeTypeRefService.insert(gridHistoryChangeTypeRef);
                    }


                if (changeType == 1) {
                    after_housekeeper_history_id = before_housekeeper_history_id;
                } else{
                    if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getHousekeeperId())){
                        UcUser ucUser = ucUserService.get(gridBasicInfoDTO.getHousekeeperId());
                        if (ucUser != null) {
                            HousekeeperHistory housekeeperHistory = new HousekeeperHistory();
                            housekeeperHistory.setEmail(ucUser.getEmail());
                            housekeeperHistory.setIsDeleted(0);
                            housekeeperHistory.setEnabledFlag(1);
                            housekeeperHistory.setMobilePhone(ucUser.getMobile());
                            housekeeperHistory.setUserAccount(ucUser.getAccount());
                            housekeeperHistory.setUserId(ucUser.getId());
                            housekeeperHistory.setUserName(ucUser.getFullname());
                            UcOrgUser ucOrgUser = ucOrgUserService.getByUserIdAndOrgId(gridBasicInfoDTO.getHousekeeperId(),beforeGrid.getStagingId());
                            if (ucOrgUser != null && StringUtils.isNotRealEmpty(ucOrgUser.getPosId())){
                                UcOrgPost ucOrgPost = ucOrgPostService.get(ucOrgUser.getPosId());
                                if (ucOrgPost != null){
                                    if ("junior".equals(ucOrgPost.getPostKey())){
                                        housekeeperHistory.setUserLevel("junior");
                                    }
                                    if ("intermediate".equals(ucOrgPost.getPostKey())){
                                        housekeeperHistory.setUserLevel("intermediate");
                                    }
                                    if ("senior".equals(ucOrgPost.getPostKey())){
                                        housekeeperHistory.setUserLevel("senior");
                                    }
                                }
                            }
                            housekeeperHistory.setCreationDate(new Date());
                            housekeeperHistory.setUpdationDate(new Date());
                            housekeeperHistory.setCreatedBy(user.getUserId());
                            housekeeperHistory.setUpdatedBy(user.getUserId());
                            housekeeperHistoryService.insert(housekeeperHistory);
                            after_housekeeper_history_id = housekeeperHistory.getId();
                        }
                    }
                }


                //新增 grid_basic_info_history_after
                GridBasicInfoHistoryAfter gridBasicInfoHistoryAfter = new GridBasicInfoHistoryAfter();
                gridBasicInfoHistoryAfter.setGridHistoryId(gridBasicInfoHistory.getId());
                gridBasicInfoHistoryAfter.setGridId(beforeGrid.getId());
                gridBasicInfoHistoryAfter.setGridCode(beforeGrid.getGridCode());
                gridBasicInfoHistoryAfter.setGridName(beforeGrid.getGridName());
                gridBasicInfoHistoryAfter.setGridRange(gridBasicInfoDTO.getGridRange());
                gridBasicInfoHistoryAfter.setGridType(gridBasicInfoDTO.getGridType());
                gridBasicInfoHistoryAfter.setGridRemark(gridBasicInfoDTO.getGridRemark());
                //区域
                gridBasicInfoHistoryAfter.setAreaId(gridBasicInfoDTO.getAreaId());
                if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getAreaId())) {
                    UcOrg area = ucOrgService.get(gridBasicInfoDTO.getAreaId());
                    if (area != null) {
                        gridBasicInfoHistoryAfter.setAreaCode(area.getCode());
                        gridBasicInfoHistoryAfter.setAreaName(area.getName());
                    }
                }
                //城市
                gridBasicInfoHistoryAfter.setCityId(gridBasicInfoDTO.getCityId());
                if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getCityId())) {
                    UcOrg city = ucOrgService.get(gridBasicInfoDTO.getCityId());
                    if (city != null) {
                        gridBasicInfoHistoryAfter.setCityCode(city.getCode());
                        gridBasicInfoHistoryAfter.setCityName(city.getName());
                    }
                }
                //项目
                gridBasicInfoHistoryAfter.setProjectId(gridBasicInfoDTO.getProjectId());
                if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getProjectId())) {
                    UcOrg project = ucOrgService.get(gridBasicInfoDTO.getProjectId());
                    if (project != null) {
                        gridBasicInfoHistoryAfter.setProjectCode(project.getCode());
                        gridBasicInfoHistoryAfter.setProjectName(project.getName());
                    }
                }
                //地块
                gridBasicInfoHistoryAfter.setMassifId(gridBasicInfoDTO.getMassifId());
                if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getMassifId())) {
                    UcOrg massif = ucOrgService.get(gridBasicInfoDTO.getMassifId());
                    if (massif != null) {
                        gridBasicInfoHistoryAfter.setMassifCode(massif.getCode());
                        gridBasicInfoHistoryAfter.setMassifName(massif.getName());
                    }
                }
                //分期
                gridBasicInfoHistoryAfter.setStagingId(gridBasicInfoDTO.getStagingId());
                if (StringUtils.isNotRealEmpty(gridBasicInfoDTO.getStagingId())) {
                    UcOrg staging = ucOrgService.get(gridBasicInfoDTO.getStagingId());
                    if (staging != null) {
                        gridBasicInfoHistoryAfter.setStagingCode(staging.getCode());
                        gridBasicInfoHistoryAfter.setStagingName(staging.getName());
                    }
                }
                gridBasicInfoHistoryAfter.setHousekeeperHistoryId(after_housekeeper_history_id);
                gridBasicInfoHistoryAfter.setFormatAttribute(gridBasicInfoDTO.getFormatAttribute());
                gridBasicInfoHistoryAfter.setSecondFormatAttribute(gridBasicInfoDTO.getSecondFormatAttribute());
                gridBasicInfoHistoryAfter.setThirdFormatAttribute(gridBasicInfoDTO.getThirdFormatAttribute());
                gridBasicInfoHistoryAfter.setEnabledFlag(1);
                gridBasicInfoHistoryAfter.setIsDeleted(0);
                gridBasicInfoHistoryAfter.setCreationDate(new Date());
                gridBasicInfoHistoryAfter.setUpdationDate(new Date());
                gridBasicInfoHistoryAfter.setCreatedBy(user.getUserId());
                gridBasicInfoHistoryAfter.setUpdatedBy(user.getUserId());
                gridBasicInfoHistoryAfterService.insert(gridBasicInfoHistoryAfter);
            }
        }

        if (num > 0) {
            gridErrorCode = GridErrorCode.SUCCESS;
        }

        return gridErrorCode;
    }

    @Override
    public PageList<GridBasicInfoHistoryDTO> queryList(QueryFilter queryFilter) {
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
        List<GridBasicInfoHistoryDTO> query = this.gridBasicInfoHistoryMapper.queryList(queryFilter.getParams());
        return new PageList<>(query);
    }
}