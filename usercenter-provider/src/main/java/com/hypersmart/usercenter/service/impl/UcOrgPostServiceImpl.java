package com.hypersmart.usercenter.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcOrgPost;
import com.hypersmart.usercenter.mapper.UcOrgPostMapper;
import com.hypersmart.usercenter.service.UcOrgPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 部门岗位
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:43:26
 */
@Service("ucOrgPostServiceImpl")
public class UcOrgPostServiceImpl extends GenericService<String, UcOrgPost> implements UcOrgPostService {

    @Autowired
    UcOrgPostMapper ucOrgPostMapper;
    public UcOrgPostServiceImpl(UcOrgPostMapper mapper) {
        super(mapper);
    }

    @Override
    public PageList<ObjectNode> getJobPage(QueryFilter filter) {
        Map<String, Object> params = filter.getParams();
        List<Map<String,Object>> list =ucOrgPostMapper.getJobPage(params);
        return new PageList(list);
    }
}