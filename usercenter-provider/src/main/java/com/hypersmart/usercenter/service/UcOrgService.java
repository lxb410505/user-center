package com.hypersmart.usercenter.service;

import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.bo.UcOrgBO;
import com.hypersmart.usercenter.dto.UcOrgDTO;
import com.hypersmart.usercenter.dto.UcOrgExtend;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.usercenter.model.UcOrgUser;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    List<UcOrg> getUserOrgList(String userId);

    List<UcOrgDTO> queryChildrenByUserId(String userId, String parentOrgId);

    List<UcOrg> queryByGrade(String userId,String grade);

    List<UcOrg> queryChildrenByOrgId(String orgId,String grade);

    List<UcOrg> queryByDemensionCode(String userId,String demensionCode);
     List<UcOrg> queryByParents(String orgId, String grade);

    /**
     * 根据组织id获取所有维度的关联组织及当前组织
     * @param query
     * @return
     */
    List<UcOrgExtend> getAllDimOrgListByOrg(UcOrgBO query);

    //获取用户默认维度组织，以及条线对应的默认组织的合集
    List<UcOrg> getUserOrgListMerge(String userId);
    List<UcOrg> getUserOrgListMergeAll(String userId);


    List<UcOrg> queryChildrenByCondition(String userId,String orgId,String grade);

    List<UcOrg> getDefaultOrgList();
    List<UcOrgParams> getOrgParams(String code, String value);
    List<UcOrg> getDefaultOrgListByGrade(String grade);
    List<UcOrg> getAllOrgs() throws IOException;

    List<UcOrg> getOrgByCondition(UcOrg ucOrg);

    List<UcOrg> queryOrgByDemensionCode(String userId,String demensionCode);

    List<UcOrg> queryAllOrgsByUserId(String userId);
}

