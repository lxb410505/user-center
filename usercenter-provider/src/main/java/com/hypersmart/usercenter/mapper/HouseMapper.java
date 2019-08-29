package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.dto.ClientRelationDTO;
import com.hypersmart.usercenter.dto.HouseExcelInfoDTO;
import com.hypersmart.usercenter.model.House;
import com.hypersmart.usercenter.model.UserHouseRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【基础信息】房产
 * 
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */
public interface HouseMapper extends GenericMapper<House> {
    List<Map<String,Object>> list(Map<String,Object> map);
    List<Map<String,Object>> selectGridBuilding(@Param("id")String id);
    List<Map<String,Object>> selectBuildingUnit(@Param("id")String id);
    List<HouseExcelInfoDTO> selectHouseExcelInfo(Map<String,Object> map);
    List<ClientRelationDTO> selectUcMemberRelation(Map<String,Object> map);

    int insertUserHouseRefList(List<UserHouseRef> addData);

    Map<String,String> selectMemberInfos(@Param("houseId") String houseId);

    UserHouseRef selectUserHouseRef(@Param("id") String id);

    int updateUserHouseRef(UserHouseRef userHouseRef);

    List<Map<String,Object>> selectByClientId(@Param("memberId")String id);

    List<Map<String, Object>> selectHouseAndClient(Map<String, Object> params);
}
