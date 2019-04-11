package com.hypersmart.usercenter.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.UcOrgPost;
import com.hypersmart.framework.service.IGenericService;

/**
 * 部门岗位
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:43:26
 */
public interface UcOrgPostService extends IGenericService<String, UcOrgPost> {
    PageList<ObjectNode> getJobPage(QueryFilter filter);
}

