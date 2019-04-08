package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.framework.service.IGenericService;

import java.util.List;
import java.util.Map;

/**
 * 职务定义
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:44:42
 */
public interface UcOrgJobService extends IGenericService<String, UcOrgJob> {
    /**
     * 根据组织id过滤组织关联的岗位，再通过岗位过滤职务列表
     * @param paramMap
     * @return
     */
    PageList<UcOrgJob> filterJobByOrgId(QueryFilter queryFilter);
}

