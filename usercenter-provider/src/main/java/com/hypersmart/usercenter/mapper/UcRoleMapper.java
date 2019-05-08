package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.UcRole;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 汇报关系分类管理
 * 
 * @author sunwenjie
 * @email xx
 * @date 2019-05-07 17:02:47
 */
public interface UcRoleMapper extends GenericMapper<UcRole> {
    List<UcRole> getUcRoleByName(@Param("roleName") String roleName);
}
