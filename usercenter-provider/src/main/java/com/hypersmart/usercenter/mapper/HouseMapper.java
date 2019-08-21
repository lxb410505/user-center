package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.House;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 【基础信息】房产
 * 
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */
public interface HouseMapper extends GenericMapper<House> {
    Map selectGridBuilding(@Param("id")String id);
    Map selectBuildingUnit(@Param("id")String id);
}
