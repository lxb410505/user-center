package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.framework.service.IGenericService;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@Service
public interface UcUserService extends IGenericService<String, UcUser> {
}

