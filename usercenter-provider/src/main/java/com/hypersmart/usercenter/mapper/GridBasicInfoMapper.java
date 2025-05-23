package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
import com.hypersmart.usercenter.model.GridBasicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 网格基础信息表
 * 
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */
public interface GridBasicInfoMapper extends GenericMapper<GridBasicInfo> {

    /**
     * 网格分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> quertList(Map<String, Object> map);


    /**
     * 网格分页查询（管家关联网格）
     * @param map
     * @return
     */
    List<Map<String, Object>> queryAssociateList(Map<String, Object> map);


    /**
     * 根据管家id和分期id获得 网格id和名称
     * @param houseKeeperBO
     * @return
     */
    List<GridBasicInfoSimpleDTO> getGridBasicInfoByHouseKeeperIds(@Param("houseKeeperBO") List<HouseKeeperBO> houseKeeperBO);

    /**
     * 获取指定id的网格信息
     * @param id
     * @return
     */
    Map<String, Object> getGridById(@Param("id") String id,@Param("stagingId") String stagingId);

    /**
     * 禁用网格
     * @param gridBasicInfoBO
     * @return
     */
    Integer disableGridInfo(GridBasicInfoBO gridBasicInfoBO);

    /**
     * 删除网格
     * @param gridBasicInfoBO
     * @return
     */
    Integer deleteGridInfo(GridBasicInfoBO gridBasicInfoBO);

    /**
     * 检查用户是否用户指定权限
     * @param params
     * @return
     */
    List<Map<String,Object>> checkPermission(Map<String, Object> params);

    List<GridBasicInfo> getByGridRange(@Param("gridRange") String gridRange);
    
    /**
     * 根据管家id查询网格信息和用户fullname
     * @param houseKeeperID
     * @return
     */
    List<GridBasicInfo> getGridBasicInfoByHouseKeeperID(List<String> list);

    /**
     * 获取所有有公区网格的地块
     * @param params
     * @return
     */
    List<String> getServiceGridByStagingIds(Map<String, Object> params);

    Integer updateHousekeeperId(String id);

    /**
     * 根据房产id查询网格基础信息
     * @param houseId
     * @return
     */
    Map<String,Object> getByHouseId(String houseId);

    /**
     * 根据地块id查询网格及网格管家
     * @param
     * @return
     */
    List<GridBasicInfoDTO> getByStagingId(@Param("list") List<String> list);

    /**
     * 根据网格id查询网格和网格管家姓名
     * @param id
     * @return
     */
    List<GridBasicInfoDTO> getByGridId(@Param("id") String id);

    List<Map<String,Object>> getListByOrgs(@Param("list") List<String> list);

    /**
     * 根据地块查询网格
     * @param stagingId
     * @return
     */
    List<GridBasicInfo> getGridByStagingId(@Param("stagingId") String stagingId);
}
