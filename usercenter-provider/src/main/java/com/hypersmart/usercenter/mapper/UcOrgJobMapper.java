package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 职务定义
 * 
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:44:42
 */
@Mapper
public interface UcOrgJobMapper extends GenericMapper<UcOrgJob> {

    /**
     * 根据组织id过滤组织关联的岗位，再通过岗位过滤职务列表
     * @param paramMap
     * @return
     */
    List<UcOrgJob> filterJobByOrgId(Map<String, Object> paramMap);

    /**
     * 检查指定用户是否有用指定权限
     * @param map
     * @return
     */
    List<Map<String,Object>> checkPermission(Map<String,Object> map);

}
