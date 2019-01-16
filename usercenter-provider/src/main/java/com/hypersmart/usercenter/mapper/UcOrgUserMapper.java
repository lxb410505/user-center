package com.hypersmart.usercenter.mapper;

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

    List<Map<String, Object>> quertList(Map<String, Object> map);
}
