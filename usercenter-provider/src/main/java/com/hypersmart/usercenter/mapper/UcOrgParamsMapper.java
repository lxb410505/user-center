package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织参数
 * 
 * @author lily
 * @email lily@qq.com
 * @date 2019-04-29 11:42:50
 */
public interface UcOrgParamsMapper extends GenericMapper<UcOrgParams> {
    List<UcOrgParams> getOrgParams(@Param("code")String code , @Param("value") String value);
}
