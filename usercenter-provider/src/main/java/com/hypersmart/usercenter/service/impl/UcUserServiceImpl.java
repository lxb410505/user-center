package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcDemension;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.mapper.UcUserMapper;
import com.hypersmart.usercenter.service.UcDemensionService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@Service("ucUserServiceImpl")
public class UcUserServiceImpl extends GenericService<String, UcUser> implements UcUserService {

    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcOrgService ucOrgService;
    @Autowired
    private UcDemensionService ucDemensionService;

    public UcUserServiceImpl(UcUserMapper mapper) {
        super(mapper);
        this.ucUserMapper = mapper;
    }

    @Override
    public UcUser getUserByUnitId(String unitId, String unitType) {
        return ucUserMapper.getUserByUnitId(unitId, unitType);
    }

    @Override
    public Set<UcUser> getDepUserByOrgCodeAndJobCode(String orgCode, String jobCode) {
        //1、查出orgCode对应组织下的所有组织
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("code", orgCode, QueryOP.EQUAL);
        PageList<UcOrg> ucOrgPageList = this.ucOrgService.query(queryFilter);


        List<String> userIds= new ArrayList<>();

        if (BeanUtils.isNotEmpty(ucOrgPageList) && BeanUtils.isNotEmpty(ucOrgPageList.getRows())) {
            UcOrg ucOrg = ucOrgPageList.getRows().get(0);
            List<UcOrg> ucOrgList = ucOrgService.getChildrenOrg(ucOrg); // 通过path查询,匹配方式： path%

           UcDemension ucDemension = ucDemensionService.get(ucOrg.getDemId());
           if(BeanUtils.isNotEmpty(ucDemension)){

               if(ucDemension.getIsDefault().equals(1)){ //默认维度

               }
               else {
                   //ucOrgList
               }

           }

        }
        //ucOrgService.getChildrenOrg() // 通过path查询,匹配方式： path%

        return null;
    }

    @Override
    public List<UcUser> queryUserByGradeAndDemCode(String userId,String grade, String DemensionCode, String fullname, String mobile) {
        List<UcOrg> ucOrgList=ucOrgService.queryByDemensionCode(userId,DemensionCode);
        List<UcUser> ucUsers= this.ucUserMapper.queryUserByOrgIdList(ucOrgList,fullname,mobile);

        return ucUsers;
    }
}
