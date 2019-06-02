package com.hypersmart.usercenter.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcUserWorkHistoryMapper;
import com.hypersmart.usercenter.model.GroupIdentity;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import com.hypersmart.usercenter.service.UcUserWorkHistoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Description:
 * @Author: liyong
 * @CreateDate: 2019/4/25 19:47
 * @Version: 1.0
 */
@Service("ucUserWorkHistoryServiceImpl")
public class UcUserWorkHistoryServiceImpl extends GenericService<String, UcUserWorkHistory> implements UcUserWorkHistoryService {
    @Autowired
    UcUserWorkHistoryMapper ucUserWorkHistoryMapper;


    public UcUserWorkHistoryServiceImpl(GenericMapper<UcUserWorkHistory> genericMapper) {
        super(genericMapper);
    }

    @Override
    public PageList<Map<String, Object>> queryPage(QueryFilter queryFilter) {
        Map<String, Object> map = queryFilter.getParams();
        PageList pageList = new PageList();
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        List<Map<String, Object>> maps = ucUserWorkHistoryMapper.queryPage(map);
        if (CollectionUtils.isEmpty(maps)) {

            pageList.setRows(new ArrayList<>());
            pageList.setTotal(0);
        }

        return new PageList<>(maps);
    }

    @Override
    public int save(UcUserWorkHistory ucUserWorkHistory) {
        return ucUserWorkHistoryMapper.save(ucUserWorkHistory);
    }

    @Override
    public String queryLatest(String username) {
        return ucUserWorkHistoryMapper.queryLatest(username);
    }

    @Override
    public  List<UcUserWorkHistory> queryUserWorkStatusList(List<String> userIds)
    {
        return ucUserWorkHistoryMapper.queryUserWorkStatusList(userIds);
    }
}
