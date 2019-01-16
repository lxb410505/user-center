package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.mapper.GridBasicInfoHistoryMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.GridBasicInfoHistory;
import com.hypersmart.usercenter.model.GridBasicInfoHistoryAfter;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryAfterService;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryService;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public GridBasicInfoHistoryServiceImpl(GridBasicInfoHistoryMapper mapper) {
        super(mapper);
    }


    @Override
    public GridErrorCode saveGridBasicInfoHistory(GridBasicInfoDTO gridBasicInfoDTO) {
        GridErrorCode gridErrorCode = GridErrorCode.UNKOWN_EXCEPTION;
        Integer num = 0;
        if(gridBasicInfoDTO!=null){
            //之前网格记录
            GridBasicInfo beforeGrid= gridBasicInfoService.get(gridBasicInfoDTO.getId());

            if(beforeGrid==null) {
                return GridErrorCode.RESOURCE_NOT_FOUND;
            }
            String housekeeper_history_id=null;
            if(StringUtils.isNotRealEmpty(beforeGrid.getHousekeeperId())){
                GridBasicInfoHistoryAfter gridBasicInfoHistoryAfter =gridBasicInfoHistoryAfterService.findDataByGridId(gridBasicInfoDTO.getId());
                if(gridBasicInfoHistoryAfter!=null){
                    //再次修改  获取housekeeper_history_id，记录grid_basic_info_history
                    housekeeper_history_id=gridBasicInfoHistoryAfter.getHousekeeperHistoryId();

                }else{
                    //首次 调用管家接口获取管家数据，记录housekeeper_history获取housekeeper_history_id，记录grid_basic_info_history
                    //todo

                }
            }

            //除了再次更新，别的情况区划信息调接口获取或前台返回
            //记录grid_basic_info_history
            GridBasicInfoHistory gridBasicInfoHistory=new GridBasicInfoHistory();
            gridBasicInfoHistory.setGridId(gridBasicInfoDTO.getId());
            gridBasicInfoHistory.setGridCode(beforeGrid.getGridCode());
            gridBasicInfoHistory.setGridName(beforeGrid.getGridName());
            gridBasicInfoHistory.setGridRange(beforeGrid.getGridRange());
            gridBasicInfoHistory.setGridType(beforeGrid.getGridType());
            gridBasicInfoHistory.setGridRemark(beforeGrid.getGridRemark());
            gridBasicInfoHistory.setAreaId(beforeGrid.getAreaId());
            gridBasicInfoHistory.setAreaCode(null);
            gridBasicInfoHistory.setAreaName(null);
            gridBasicInfoHistory.setProjectId(beforeGrid.getProjectId());
            gridBasicInfoHistory.setProjectCode(null);
            gridBasicInfoHistory.setProjectName(null);
            gridBasicInfoHistory.setMassifId(beforeGrid.getMassifId());
            gridBasicInfoHistory.setMassifCode(null);
            gridBasicInfoHistory.setMassifName(null);
            gridBasicInfoHistory.setStagingId(beforeGrid.getStagingId());
            gridBasicInfoHistory.setStagingCode(null);
            gridBasicInfoHistory.setStagingName(null);
            gridBasicInfoHistory.setHousekeeperHistoryId(housekeeper_history_id);
            gridBasicInfoHistory.setFormatAttribute(beforeGrid.getFormatAttribute());
            gridBasicInfoHistory.setCityId(beforeGrid.getCityId());
            gridBasicInfoHistory.setCityCode(null);
            gridBasicInfoHistory.setCityName(null);

            //新增
            num=  this.insertSelective(gridBasicInfoHistory);

            if(num>0){
                //新增 grid_basic_info_history_after
                String after_housekeeper_history_id=null;
                if(StringUtils.isNotRealEmpty(gridBasicInfoDTO.getHousekeeperId())){
                    if(gridBasicInfoDTO.getHousekeeperId()!=beforeGrid.getHousekeeperId()){
                        //调用管家接口获取管家数据，记录housekeeper_history获取after_housekeeper_history_id
                        //todo

                    }else{
                        after_housekeeper_history_id=housekeeper_history_id;
                    }
                }
                GridBasicInfoHistoryAfter gridBasicInfoHistoryAfter=new GridBasicInfoHistoryAfter();
                gridBasicInfoHistoryAfter.setGridHistoryId(gridBasicInfoHistory.getId());
                gridBasicInfoHistoryAfter.setGridId(gridBasicInfoDTO.getId());
                gridBasicInfoHistoryAfter.setGridCode(gridBasicInfoDTO.getGridCode());
                gridBasicInfoHistoryAfter.setGridName(gridBasicInfoDTO.getGridName());
                gridBasicInfoHistoryAfter.setGridRange(gridBasicInfoDTO.getGridRange());
                gridBasicInfoHistoryAfter.setGridType(gridBasicInfoDTO.getGridType());
                gridBasicInfoHistoryAfter.setGridRemark(gridBasicInfoDTO.getGridRemark());
                gridBasicInfoHistoryAfter.setAreaId(gridBasicInfoDTO.getAreaId());
                gridBasicInfoHistoryAfter.setAreaCode(null);
                gridBasicInfoHistoryAfter.setAreaName(null);
                gridBasicInfoHistoryAfter.setProjectId(gridBasicInfoDTO.getProjectId());
                gridBasicInfoHistoryAfter.setProjectCode(null);
                gridBasicInfoHistoryAfter.setProjectName(null);
                gridBasicInfoHistoryAfter.setMassifId(gridBasicInfoDTO.getMassifId());
                gridBasicInfoHistoryAfter.setMassifCode(null);
                gridBasicInfoHistoryAfter.setMassifName(null);
                gridBasicInfoHistoryAfter.setStagingId(gridBasicInfoDTO.getStagingId());
                gridBasicInfoHistoryAfter.setStagingCode(null);
                gridBasicInfoHistoryAfter.setStagingName(null);
                gridBasicInfoHistoryAfter.setHousekeeperHistoryId(after_housekeeper_history_id);
                gridBasicInfoHistoryAfter.setFormatAttribute(gridBasicInfoDTO.getFormatAttribute());
                gridBasicInfoHistoryAfter.setCityId(gridBasicInfoDTO.getCityId());
                gridBasicInfoHistoryAfter.setCityCode(null);
                gridBasicInfoHistoryAfter.setCityName(null);

                gridBasicInfoHistoryAfterService.insertSelective(gridBasicInfoHistoryAfter);
            }
        }

        if(num>0){
            gridErrorCode=GridErrorCode.SUCCESS;
        }

        return gridErrorCode;
    }
}