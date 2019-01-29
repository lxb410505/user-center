package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UserCurrentOrgHistory;
import com.hypersmart.usercenter.mapper.UserCurrentOrgHistoryMapper;
import com.hypersmart.usercenter.service.UserCurrentOrgHistoryService;
import org.springframework.stereotype.Service;

/**
 * 用户当前组织表
 *
 * @author admin
 * @email @sian.cn
 * @date 2019-01-29 20:06:10
 */
@Service("userCurrentOrgHistoryServiceImpl")
public class UserCurrentOrgHistoryServiceImpl extends GenericService<String, UserCurrentOrgHistory> implements UserCurrentOrgHistoryService {

    public UserCurrentOrgHistoryServiceImpl(UserCurrentOrgHistoryMapper mapper) {
        super(mapper);
    }
}