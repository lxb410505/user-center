package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.UcUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色管理
 * 
 * @author sunwenjie
 * @email xx
 * @date 2019-05-07 16:52:59
 */
public interface UcUserRoleMapper extends GenericMapper<UcUserRole> {
    List<UcUserRole> getByRoleIdAndUserId(@Param("RoleId") String RoleId, @Param("UserId") String UserId);
}
