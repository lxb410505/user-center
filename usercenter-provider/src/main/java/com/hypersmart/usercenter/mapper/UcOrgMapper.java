package com.hypersmart.usercenter.mapper;

import com.hypersmart.usercenter.bo.UcOrgBO;
import com.hypersmart.usercenter.dto.UcOrgDTO;
import com.hypersmart.usercenter.dto.UcOrgExtend;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.UcOrgUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Set;

/**
 * 组织架构
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */
@Mapper
public interface UcOrgMapper extends GenericMapper<UcOrg> {

    List<UcOrg> getOrgList(@Param("orgUserList") List<UcOrgUser> list);

    List<UcOrg> getChildrenOrg(UcOrg ucOrg);

    List<UcOrg> queryByDemId(String demId);

    /**
     * 根据组织id获取所有维度的关联组织及当前组织
     * @param query
     * @return
     */
    List<UcOrgExtend> getAllDimOrgListByOrg(UcOrgBO query);

    List<UcOrgDTO> getByIdsAndParentId(@Param("ids") List<String> list2,@Param("parentId") String parentOrgId);

    String getCodeById(@Param("orgId") String parentOrgId);

    List<UcOrg> getChildrenOrgByCondition(@Param("path")String path, @Param("ids")Set<String> ids, @Param("grade")String grade);

    List<UcOrg> getByOrgName(@Param("orgName") String orgName);

    UcOrg getByOrgNameParentId(@Param("orgName") String orgName,@Param("parentId") String parentId);
}
