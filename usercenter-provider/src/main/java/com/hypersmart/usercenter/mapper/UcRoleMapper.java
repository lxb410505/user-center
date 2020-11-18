package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.UcRole;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UcRoleMapper extends GenericMapper<UcRole> {
    List<UcRole> getUcRoleByName(@Param("roleName") String roleName);

    List<UcRole> getRolesByUserId(@Param("userId") String userId);
}
