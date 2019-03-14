package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@Mapper
public interface UcUserMapper extends GenericMapper<UcUser> {

    List<Map<String, Object>> quertListFive(Map<String, Object> map);

    List<Map<String, Object>> quertListFour(Map<String, Object> map);

    List<Map<String, Object>> quertListThree(Map<String, Object> map);

    List<Map<String, Object>> quertListTwo(Map<String, Object> map);

    List<UcUser> queryUserByOrgIdList(@Param("ucOrgList")List<UcOrg> ucOrgList, @Param("fullname")String fullname, @Param("mobile")String mobile);


    UcUser getUserByUnitId(@Param("unitId") String unitId,@Param("unitType")String unitType);
}
