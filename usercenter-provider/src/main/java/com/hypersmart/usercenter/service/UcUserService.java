package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.dto.UserDetailRb;
import com.hypersmart.usercenter.dto.UserDetailValue;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.framework.service.IGenericService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@Service
public interface UcUserService extends IGenericService<String, UcUser> {

    UcUser getUserByUnitId(String unitId, String unitType);

    Set<UcUser> getDepUserByOrgCodeAndJobCode(String orgCode, String jobCode);

    List<UcUser> queryUserByGradeAndDemCode(String userId, String grade, String DemensionCode, String fullname, String mobile);

    /**
     * 根据组织和职务查询对应组织中的用户
     *
     * @param queryFilter
     * @return
     */
    PageList<UcUser> searchUserByCondition(QueryFilter queryFilter);

    /**
     * 根据职务编码查询对应的用户
     *
     * @param queryFilter
     * @return
     */
    PageList<UcUser> pagedQueryByJobCodes(String jobCodes, QueryFilter queryFilter);

    UserDetailValue searchUserDetailByCondition(UserDetailRb userDetailRb);
}

