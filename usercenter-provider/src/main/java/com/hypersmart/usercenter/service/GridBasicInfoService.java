package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
import com.hypersmart.usercenter.model.GridBasicInfo;

import java.util.List;
import java.util.Map;

/**
 * 网格基础信息表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */
public interface GridBasicInfoService extends IGenericService<String, GridBasicInfo> {

    PageList<Map<String,Object>> quertList(QueryFilter queryFilter);

    /**
     * 查询网格信息
     * @param queryFilter
     * @return
     */
    PageList<GridBasicInfoDTO> selectGridBasicInfo(QueryFilter queryFilter);

    /**
     * 根据id获取网格
     * @param id
     * @return
     */
    GridBasicInfoDTO getGridById(String id);

    /**
     * 新增网格
     * @param gridBasicInfoDTO
     * @return
     */
    GridErrorCode create(GridBasicInfoDTO gridBasicInfoDTO);

    /**
     * 修改网格
     * @param gridBasicInfoDTO
     * @return
     */
    GridErrorCode edit(GridBasicInfoDTO gridBasicInfoDTO);

    /**
     * 变更管家
     * @param gridBasicInfoDTO
     * @return
     */
    GridErrorCode changeHousekeeper(GridBasicInfoDTO gridBasicInfoDTO);

    /**
     * 变更映射楼栋
     * @param gridBasicInfoDTO
     * @return
     */
    GridErrorCode changeRange(GridBasicInfoDTO gridBasicInfoDTO);

    /**
     * 禁用网格
     * @param gridBasicInfoBO
     * @return
     */
    GridErrorCode disableGridList(GridBasicInfoBO gridBasicInfoBO);

    /**
     * 删除网格
     * @param gridBasicInfoBO
     * @return
     */
    GridErrorCode deleteGridList(GridBasicInfoBO gridBasicInfoBO);

    /**
     * 根据管家id和分期id获得 网格id和名称
     * @param houseKeeperBO
     * @return
     */
    List<GridBasicInfoSimpleDTO> getGridBasicInfoByHouseKeeperIds(List<HouseKeeperBO> houseKeeperBO);

    /**
     * 根据分期id获得该分期下未关联的分期
     * @param divideId
     * @return
     */
    List<GridBasicInfo> getDisassociatedGridByDivideId(String divideId);
}

