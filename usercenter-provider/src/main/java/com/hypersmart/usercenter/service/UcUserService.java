package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.dto.GroupIdentityDTO;
import com.hypersmart.usercenter.dto.UserDetailRb;
import com.hypersmart.usercenter.dto.UserDetailValue;
import com.hypersmart.usercenter.dto.rsunJbDTO;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.util.ResourceErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
public interface  UcUserService extends IGenericService<String, UcUser> {

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

    /**
     * 工单系统总部角色导入
     * @param file
     * @return
     * @throws Exception
     */
    ResourceErrorCode importOrgUser(MultipartFile file) throws Exception;

    Set<GroupIdentity> getByJobCodeAndOrgIdAndDimCodeDeeply(String jobCode, String orgId, String dimCode, String fullName) throws Exception;

    Set<GroupIdentityDTO> getByJobCodeAndOrgIdAndDimCodeDeeplyWithPost(String jobCode, String orgId, String dimCode, String fullName);

    List<String> getSkillCodebyCategory(String category);

    UcUser getUserByDivideId(String divideId, String gridType);

    String getdikuai(String id);

    RsunUserStarLevel getkuozhan(String account);

    void insertt(UcUser ucUser);

    ArrayList<RsunUserStarLevel> getlist(PageBean pagebean);

    RsunUserStarLevel getxzjb(String userCode);

    List<RsunJbHiReward> getmoney(String userCode);

    List<JinBiJiLv> getUserCoinHisRecordByUserCode(String userCode,String dateString);

    List<Map<String, String>> getOrderCoinHisRecordByCode(String code, String groupUser);

    List<UcUser> getaa();

    Boolean insertadd(rsunJbDTO reward);

    void updatemoney(RsunJbHiReward reward);

    String getname(String ucUserId);

    List<UcUser> getzh(String getname);

    String getid(String value);
}

