package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.framework.service.IGenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    List<String> getPostIdByjobCode(String joCode);

    PageList<Map<String,Object>> quertListFive(QueryFilter queryFilter);

    PageList<Map<String,Object>> quertList(QueryFilter queryFilter);

    UcOrgUser getByUserIdAndOrgId(String housekeeperId, String stagingId);

    List<UcOrgUser> getUserDefaultOrg(String userId);

    List<UcOrgUser> getUserDefaultOrgByRef(String userId);
}

