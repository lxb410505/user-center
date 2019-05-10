package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.Divide;
import com.hypersmart.usercenter.model.House;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户组织关系
 * 
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:59:39
 */
@Mapper
public interface UcOrgUserMapper extends GenericMapper<UcOrgUser> {

    List<UcOrgUser> getUserOrg(@Param("userId") String userId);

    List<String> getPostIdByjobCode(@Param("code") String joCode);

    List<UcOrgUser> getUserDefaultOrg(@Param("userId") String userId);

    List<UcOrgUser> getUserDefaultOrgByRef(@Param("userId") String userId);


    /**
     * 根据用户和区域查询其下所有地块
     * @param params
     * @return
     */
    List<Map<String, Object>> getOrgByCondition(Map<String, Object> params);

    List<UcOrgUser> getListByOrgIdUserId(@Param("orgId") String orgId,@Param("userId") String userId);

    List<UcOrg> findDivide(Map<String,Object> param);
    List<House> findHous(Map<String,Object> param);
}
