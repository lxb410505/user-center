package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.UcOrgUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织架构
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */
@Service
public interface UcOrgService extends IGenericService<String, UcOrg> {

    List<UcOrg> getOrg(List<UcOrgUser> list);

    List<UcOrg> getChildrenOrg(UcOrg ucOrg);
}

