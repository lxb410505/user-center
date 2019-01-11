package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.framework.service.IGenericService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户组织关系
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:59:39
 */
@Service
public interface UcOrgUserService extends IGenericService<String, UcOrgUser> {

    List<UcOrgUser> getUserOrg(String userId);
}

