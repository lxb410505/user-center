package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.dto.UserDetailRb;
import com.hypersmart.usercenter.dto.UserDetailValue;
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

import java.util.*;

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
//        List<String> ids = new ArrayList<>();
//        for(UcUser ucUser:ucUsers){
//                if(ids.contains(ucUser.getId())||ids.contains(ucUser.getId())){
//                    ids.add(ucUser.getId());
//                }
//        }
        return ucUsers;
    }

    /**
     * 根据组织和职务查询对应组织中的用户
     * @param queryFilter
     * @return
     */
    public PageList<UcUser> searchUserByCondition(QueryFilter queryFilter) {
        queryFilter.setClazz(UcUser.class);
        PageBean pageBean = queryFilter.getPageBean();
        if (!BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        }
        Map<String, Object> paramMap = queryFilter.getParams();
        if (BeanUtils.isEmpty(paramMap) || BeanUtils.isEmpty(paramMap.get("orgIdList"))) {
            PageList pageList = new PageList();
            if (!BeanUtils.isEmpty(pageBean)) {
                pageList.setPage(pageBean.getPage());
                pageList.setPageSize(pageBean.getPageSize());
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0L);
            }

            return pageList;
        }

        List<UcUser> userList = ucUserMapper.searchUserByCondition(queryFilter.getParams());
        return new PageList(userList);
    }

    @Override
    public UserDetailValue searchUserDetailByCondition(UserDetailRb userDetailRb) {
        UserDetailValue userDetailValue = new UserDetailValue();
        UcOrg devideInfo = ucOrgService.get(userDetailRb.getDevideId());
        if (BeanUtils.isNotEmpty(devideInfo)) {
            userDetailValue.setDevideName(devideInfo.getName());
            UcOrg projectInfo = ucOrgService.get(devideInfo.getParentId());
            if (BeanUtils.isNotEmpty(projectInfo)) {
                userDetailValue.setProjectName(projectInfo.getName());
            }

        }
        List<String> jobs = ucUserMapper.serchUserJobsByUserId(userDetailRb.getUserId(), userDetailRb.getDevideId());
        userDetailValue.setJobs(BeanUtils.isEmpty(jobs) ? new ArrayList<>() : jobs);
        return userDetailValue;
    }


}
