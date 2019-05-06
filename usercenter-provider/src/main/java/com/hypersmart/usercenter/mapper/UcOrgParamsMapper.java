package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织参数
 * 
 * @author zhoukai
 * @email 70*******@qq.com
 * @date 2019-04-28 10:15:44
 */
@Mapper
public interface UcOrgParamsMapper extends GenericMapper<UcOrgParams> {
    List<UcOrgParams> selectByOrgId(@Param("orgId") String orgId);
    List<UcOrgParams> getOrgParams(@Param("code")String code , @Param("value") String value);
}
