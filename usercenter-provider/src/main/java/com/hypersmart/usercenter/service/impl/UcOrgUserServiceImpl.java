package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.mapper.UcOrgUserMapper;
import com.hypersmart.usercenter.mapper.UcUserMapper;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcOrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 用户组织关系
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:59:39
 */
@Service("ucOrgUserServiceImpl")
public class UcOrgUserServiceImpl extends GenericService<String, UcOrgUser> implements UcOrgUserService {

    @Autowired
    UcOrgUserMapper ucOrgUserMapper;

    @Autowired
    UcUserMapper ucUserMapper;

    @Autowired
    UcOrgService ucOrgService;


    public UcOrgUserServiceImpl(UcOrgUserMapper mapper) {
        super(mapper);
    }

    @Override
    public List<UcOrgUser> getUserOrg(String userId) {
        return ucOrgUserMapper.getUserOrg(userId);
    }

    @Override
    public List<String> getPostIdByjobCode(String joCode) {
        return ucOrgUserMapper.getPostIdByjobCode(joCode);
    }

    @Override
    public PageList<Map<String, Object>> quertListFive(QueryFilter queryFilter) {

        /**
         * 1、查询当前登录人所在的所有分期（uc_org.LEVEL_ = 5）的管家信息：
         *    接口返回以下信息：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *    支持排序的字段：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *
         */
        Object orgId = ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);

        if (orgId != null) {
            queryFilter.addFilter("divideId", orgId.toString(), QueryOP.IN, FieldRelation.AND, "two");
        } else {
            PageList<Map<String, Object>> pageList = new PageList();
            pageList.setTotal(0);
            pageList.setPage(1);
            pageList.setPageSize(10);
            pageList.setRows(new ArrayList<>());
            return pageList;
        }

        //1、根据用户信息获取用户所属组织
        //IUser user = ContextUtil.getCurrentUser();
        //获取用户组织关系
        //List<UcOrgUser> ucOrgUsersList = this.getUserOrg(user.getUserId());

        //2、查找ucOrgUsersList 对应的组织下的分期，将分期id的集合作为quertList的条件。
       /* String orgIds = "";
        for (int i = 0; i < ucOrgUsersList.size(); i++) {
            if (i == 0) {
                orgIds = ucOrgUsersList.get(i).getOrgId();
            } else {
                orgIds = orgIds + "," + ucOrgUsersList.get(i).getOrgId();
            }
        }
        QueryFilter queryOrg = QueryFilter.build();
        if(null != ucOrgUsersList && ucOrgUsersList.size()>0){
            queryOrg.addFilter("id",orgIds,QueryOP.IN,FieldRelation.AND);
        }else{
            PageList<Map<String, Object>> pageList = new PageList();
            pageList.setTotal(0);
            if(BeanUtils.isEmpty(queryFilter.getPageBean())){
                pageList.setPage(1);
                pageList.setPageSize(10);
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            }else{
                pageList.setPage(queryFilter.getPageBean().getPage());
                pageList.setPageSize(queryFilter.getPageBean().getPageSize());
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            }
            return pageList;
        }
        PageList<UcOrg> orgList =  ucOrgService.query(queryOrg);
        Map<String,UcOrg> map = new HashMap<>();
        for(UcOrg ucOrg:orgList.getRows()){
            QueryFilter ucorgQuery = QueryFilter.build();
            ucorgQuery.addFilter("path",ucOrg.getPath(),QueryOP.RIGHT_LIKE,FieldRelation.AND);
            ucorgQuery.addFilter("level",5,QueryOP.EQUAL,FieldRelation.AND);
            PageList<UcOrg> divideList = ucOrgService.query(ucorgQuery);
            if(null != divideList && null != divideList.getRows() && divideList.getRows().size()>0){
                for(int i=0;i< divideList.getRows().size();i++){
                    UcOrg temp = divideList.getRows().get(i);
                    map.put(temp.getId(),temp);
                }
            }
        }
        Set<String> set = map.keySet();
        if(null != set && set.size()>0){
            String divideId = "";
            for(String index: set){
                if("".equals(divideId)){
                    divideId = index;
                }else{
                    divideId = divideId +","+ index;
                }
            }
            queryFilter.addFilter("divideId", divideId, QueryOP.IN, FieldRelation.AND,"two");
        }else{
            PageList<Map<String, Object>> pageList = new PageList();
            pageList.setTotal(0);
            if(BeanUtils.isEmpty(queryFilter.getPageBean())){
                pageList.setPage(1);
                pageList.setPageSize(10);
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            }else{
                pageList.setPage(queryFilter.getPageBean().getPage());
                pageList.setPageSize(queryFilter.getPageBean().getPageSize());
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            }
            return pageList;
        }*/
//        if (StringUtils.isRealEmpty(ContextUtil.getCurrentUser().getAttributes().get("currentOrg"))) {
//            IUser iUser = ContextUtil.getCurrentUser();
//            PageList<Map<String, Object>> pageList = new PageList();
//            pageList.setTotal(0);
//            pageList.setPage(1);
//            pageList.setPageSize(10);
//            pageList.setRows(new ArrayList<>());
//            return pageList;
//        }
        //===============================================================================================
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }

        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        UcOrg ucOrg = ucOrgService.get(orgId.toString());
        if(null!= ucOrg){
            String path = ucOrg.getPath();
            String pathpli = path.replace(".","");
            int num = path.length()-pathpli.length();
            List<Map<String, Object>> query = new ArrayList<>();
            switch (num){
                case 3:
                    query= this.ucUserMapper.quertListTwo(queryFilter.getParams());
                    break;
                case 4:
                    query= this.ucUserMapper.quertListThree(queryFilter.getParams());
                    break;
                case 5:
                    query= this.ucUserMapper.quertListFour(queryFilter.getParams());
                    break;
                case 6:
                    query= this.ucUserMapper.quertListFive(queryFilter.getParams());
                    break;
            }
            return new PageList<>(query);
        }else{
            return new PageList<>(new ArrayList<>());
        }
    }

    public PageList<Map<String, Object>> quertList(QueryFilter queryFilter) {

        /**
         * 1、查询当前登录人所在的所有分期（uc_org.LEVEL_ = 4）的管家信息：
         *    接口返回以下信息：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *    支持排序的字段：区域、项目、地块、分期、管家名称、管家手机号、岗位等级
         *
         */

        //1、根据用户信息获取用户所属组织
        IUser user = ContextUtil.getCurrentUser();
        //获取用户组织关系
        List<UcOrgUser> ucOrgUsersList = this.getUserOrg(user.getUserId());

        //2、查找ucOrgUsersList 对应的组织下的分期，将分期id的集合作为quertList的条件。
        String orgIds = "";
        for (int i = 0; i < ucOrgUsersList.size(); i++) {
            if (i == 0) {
                orgIds = ucOrgUsersList.get(i).getOrgId();
            } else {
                orgIds = orgIds + "," + ucOrgUsersList.get(i).getOrgId();
            }
        }
        QueryFilter queryOrg = QueryFilter.build();
        if (null != ucOrgUsersList && ucOrgUsersList.size() > 0) {
            queryOrg.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
        } else {
            PageList<Map<String, Object>> pageList = new PageList();
            pageList.setTotal(0);
            if (BeanUtils.isEmpty(queryFilter.getPageBean())) {
                pageList.setPage(1);
                pageList.setPageSize(10);
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            } else {
                pageList.setPage(queryFilter.getPageBean().getPage());
                pageList.setPageSize(queryFilter.getPageBean().getPageSize());
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            }
            return pageList;
        }
        PageList<UcOrg> orgList = ucOrgService.query(queryOrg);
        Map<String, UcOrg> map = new HashMap<>();
        for (UcOrg ucOrg : orgList.getRows()) {
            QueryFilter ucorgQuery = QueryFilter.build();
            ucorgQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
            ucorgQuery.addFilter("level", 4, QueryOP.EQUAL, FieldRelation.AND);
            PageList<UcOrg> divideList = ucOrgService.query(ucorgQuery);
            if (null != divideList && null != divideList.getRows() && divideList.getRows().size() > 0) {
                for (int i = 0; i < divideList.getRows().size(); i++) {
                    UcOrg temp = divideList.getRows().get(i);
                    map.put(temp.getId(), temp);
                }
            }
        }
        Set<String> set = map.keySet();
        if (null != set && set.size() > 0) {
            String divideId = "";
            for (String index : set) {
                if ("".equals(divideId)) {
                    divideId = index;
                } else {
                    divideId = divideId + "," + index;
                }
            }
            queryFilter.addFilter("divideId", divideId, QueryOP.IN, FieldRelation.AND, "two");
        } else {
            PageList<Map<String, Object>> pageList = new PageList();
            pageList.setTotal(0);
            if (BeanUtils.isEmpty(queryFilter.getPageBean())) {
                pageList.setPage(1);
                pageList.setPageSize(10);
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            } else {
                pageList.setPage(queryFilter.getPageBean().getPage());
                pageList.setPageSize(queryFilter.getPageBean().getPageSize());
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
            }
            return pageList;
        }

        //===============================================================================================
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }

        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        List<Map<String, Object>> query = this.ucUserMapper.quertListFour(queryFilter.getParams());
        return new PageList<>(query);
    }

    @Override
    public UcOrgUser getByUserIdAndOrgId(String housekeeperId, String stagingId) {
        Example example = new Example(UcOrgUser.class);
        example.createCriteria().andEqualTo("userId", housekeeperId).andEqualTo("orgId", stagingId);
        List<UcOrgUser> list = this.ucOrgUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<UcOrgUser> getUserDefaultOrg(String userId) {
        return ucOrgUserMapper.getUserDefaultOrg(userId);
    }

    @Override
    public List<UcOrgUser> getUserDefaultOrgByRef(String userId) {
        return ucOrgUserMapper.getUserDefaultOrgByRef(userId);
    }
}