package com.hypersmart.usercenter.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgPost;
import com.hypersmart.usercenter.mapper.UcOrgPostMapper;
import com.hypersmart.usercenter.service.UcOrgPostService;
import com.hypersmart.usercenter.service.UcOrgService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    UcOrgService ucOrgService;
    public UcOrgPostServiceImpl(UcOrgPostMapper mapper) {
        super(mapper);
    }

    @Override
    public PageList<ObjectNode> getJobPage(QueryFilter filter) {
        Object orgId = ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);
        PageList<ObjectNode> pageList=new PageList<>();
        filter.addFilter("ORG_ID_",orgId, QueryOP.EQUAL);
        Map<String, Object> params = filter.getParams();

        if (null == filter.getPageBean() || null == filter.getPageBean().getPageSize()
                || null == filter.getPageBean().getPage()) {
            pageList.setPage(1);
            pageList.setPageSize(20);
        } else {
            pageList.setPage(filter.getPageBean().getPage());
            pageList.setPageSize(filter.getPageBean().getPageSize());
        }

        List<Map<String,Object>> list =ucOrgPostMapper.getJobPage(params);


        if(CollectionUtils.isEmpty(list)){
            pageList.setTotal(0);
            pageList.setRows(new ArrayList<>());
            pageList.setPage(1);
            pageList.setPageSize(20);
            return pageList;

        }
        return new PageList(list);
    }

    @Override
    public PageList<ObjectNode> getJobPage2(QueryFilter filter) {
        Object orgId = ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);
        PageList<ObjectNode> pageList=new PageList<>();
        UcOrg ucOrg= ucOrgService.get(orgId.toString());
        List<String> orgIds = new ArrayList<>();
        orgIds.add(orgId.toString());
        if (ucOrg != null){
            if (StringUtil.isNotEmpty(ucOrg.getParentId())){
                orgIds.add(ucOrg.getParentId());
            }
        }
        filter.addFilter("ORG_ID_",orgIds, QueryOP.IN);
        Map<String, Object> params = filter.getParams();

        if (null == filter.getPageBean() || null == filter.getPageBean().getPageSize()
                || null == filter.getPageBean().getPage()) {
            pageList.setPage(1);
            pageList.setPageSize(20);
        } else {
            pageList.setPage(filter.getPageBean().getPage());
            pageList.setPageSize(filter.getPageBean().getPageSize());
        }

        List<Map<String,Object>> list =ucOrgPostMapper.getJobPage(params);


        if(CollectionUtils.isEmpty(list)){
            pageList.setTotal(0);
            pageList.setRows(new ArrayList<>());
            pageList.setPage(1);
            pageList.setPageSize(20);
            return pageList;

        }
        return new PageList(list);
    }
}