package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.mapper.UcUserMapper;
import com.hypersmart.usercenter.service.UcUserService;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@Service("ucUserServiceImpl")
public class UcUserServiceImpl extends GenericService<String, UcUser> implements UcUserService {

    public UcUserServiceImpl(UcUserMapper mapper) {
        super(mapper);
    }
}