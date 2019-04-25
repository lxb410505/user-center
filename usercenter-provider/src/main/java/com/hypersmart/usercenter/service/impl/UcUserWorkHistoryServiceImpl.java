package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.exception.SystemException;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.model.Pagination;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcUserWorkHistoryMapper;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import com.hypersmart.usercenter.service.UcUserWorkHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
