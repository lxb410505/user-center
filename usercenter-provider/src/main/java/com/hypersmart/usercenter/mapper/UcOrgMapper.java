package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.UcOrgUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织架构
 * 
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */
@Mapper
public interface UcOrgMapper extends GenericMapper<UcOrg> {

    List<UcOrg> getOrgList(@Param("orgUserList") List<UcOrgUser> list);

    List<UcOrg> getChildrenOrg(UcOrg ucOrg);
}
