package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.usercenter.mapper.UcOrgJobMapper;
import com.hypersmart.usercenter.service.UcOrgJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 职务定义
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:44:42
 */
@Service("ucOrgJobServiceImpl")
public class UcOrgJobServiceImpl extends GenericService<String, UcOrgJob> implements UcOrgJobService {

    @Autowired
    private UcOrgJobMapper orgJobMapper;

    public UcOrgJobServiceImpl(UcOrgJobMapper mapper) {
        super(mapper);
        orgJobMapper = mapper;
    }

    /**
     * 根据组织id过滤组织关联的岗位，再通过岗位过滤职务列表
     * @param queryFilter
     * @return
     */
    public PageList<UcOrgJob> filterJobByOrgId(QueryFilter queryFilter){

        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }

        List<UcOrgJob> query = this.orgJobMapper.filterJobByOrgId(queryFilter.getParams());
        return new PageList<>(query);
    }
}