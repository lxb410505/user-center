package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.usercenter.model.UcOrgPost;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门岗位
 * 
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:43:26
 */
@Mapper
public interface UcOrgPostMapper extends GenericMapper<UcOrgPost> {

    List<Map<String, Object>> getJobPage(Map<String, Object> params);

    UcOrgPost getByOrgIdAndJobId(@Param("orgId") String orgId,@Param("jobId") String jobId);
}
