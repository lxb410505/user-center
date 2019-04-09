package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.UcDemension;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcDemensionService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcOrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */
@Service("ucOrgServiceImpl")
public class UcOrgServiceImpl extends GenericService<String, UcOrg> implements UcOrgService {

    @Autowired
    UcOrgMapper ucOrgMapper;

    @Autowired
    UcOrgUserService ucOrgUserService;

    @Autowired
    UcDemensionService ucDemensionService;

    public UcOrgServiceImpl(UcOrgMapper mapper) {
        super(mapper);
        this.ucOrgMapper = mapper;
    }

    @Override
    public List<UcOrg> getOrg(List<UcOrgUser> list) {
        return ucOrgMapper.getOrgList(list);
    }

    @Override
    public List<UcOrg> getChildrenOrg(UcOrg ucOrg) {
        return ucOrgMapper.getChildrenOrg(ucOrg);
    }

    @Override
    /**
     *  获取用户权限列表
     *  diasbled:1->有权限 2：->无权限只能查看
     * */
    public List<UcOrg> getUserOrgList(String userId) {
        QueryFilter queryFilter = QueryFilter.build();
        //根据用户查询人与组织关系
        List<UcOrgUser> list =ucOrgUserService.getUserOrg(userId);
        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }
        String orgIds = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                orgIds = list.get(i).getOrgId();
            } else {
                orgIds = orgIds + "," + list.get(i).getOrgId();
            }
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
        orgQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (UcOrg ucOrg : returnList) {
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
            childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for (UcOrg org : orgs) {
                if (!ids.contains(org.getId())) {
                    org.setDisabled("1");
                    set.add(org);
                    ids.add(org.getId());
                }
            }
            if (!ids.contains(ucOrg.getId())) {
                ucOrg.setDisabled("1");
                set.add(ucOrg);
                ids.add(ucOrg.getId());
            }
        }
        //根据组织查询父级组织
        for (UcOrg ucOrg : returnList) {
            String[] paths = ucOrg.getPath().split("\\.");
            for (int i = 0; i < paths.length; i++) {
                QueryFilter query = QueryFilter.build();
                query.addFilter("id", paths[i], QueryOP.EQUAL, FieldRelation.AND);
                query.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
                List<UcOrg> voList = this.query(query).getRows();
                for (UcOrg vo : voList) {
                    if (!ids.contains(vo.getId())) {
                        vo.setDisabled("2");
                        set.add(vo);
                        ids.add(vo.getId());
                    }
                }
            }
        }
        return set;
    }

    //根据userId和组织父级id查询组织信息
    public List<UcOrg> queryChildrenByUserId(String userId, String parentOrgId) {
        QueryFilter queryFilter = QueryFilter.build();
        //根据用户查询人与组织关系
        List<UcOrgUser> list = ucOrgUserService.getUserOrg(userId);
        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }
        String orgIds = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                orgIds = list.get(i).getOrgId();
            } else {
                orgIds = orgIds + "," + list.get(i).getOrgId();
            }
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
        orgQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if (StringUtils.isEmpty(parentOrgId)) {
            parentOrgId = "0";
        }
        for (UcOrg ucOrg : returnList) {
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
            childQuery.addFilter("parentId", parentOrgId, QueryOP.RIGHT_LIKE, FieldRelation.AND);
            childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for (UcOrg org : orgs) {
                if (!ids.contains(org.getId())) {
//                    org.setDisabled("1");
                    set.add(org);
                    ids.add(org.getId());
                }
            }
        }
        return set;
    }

    //根据组织级别查询组织信息
    public List<UcOrg> queryByGrade(String userId, String grade) {
        QueryFilter queryFilter = QueryFilter.build();
        //根据用户查询人与组织关系
        List<UcOrgUser> list = ucOrgUserService.getUserOrg(userId);
        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }
        String orgIds = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                orgIds = list.get(i).getOrgId();
            } else {
                orgIds = orgIds + "," + list.get(i).getOrgId();
            }
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
        orgQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (UcOrg ucOrg : returnList) {
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
            childQuery.addFilter("grade", grade, QueryOP.EQUAL, FieldRelation.AND);
            childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for (UcOrg org : orgs) {
                if (!ids.contains(org.getId())) {
//                    org.setDisabled("1");
                    set.add(org);
                    ids.add(org.getId());
                }

            }
        }
        return set;
    }

    @Override
    public List<UcOrg> queryChildrenByOrgId(String orgId, String grade) {

        UcOrg org = this.get(orgId);

        QueryFilter childQuery = QueryFilter.build();
        childQuery.addFilter("path", org.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
        childQuery.addFilter("id", orgId, QueryOP.NOT_EQUAL, FieldRelation.AND);
        childQuery.addFilter("grade", grade, QueryOP.EQUAL, FieldRelation.AND);
        childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        List<UcOrg> orgs = this.query(childQuery).getRows();
        return orgs;
    }

    @Override
    public List<UcOrg> queryByDemensionCode(String userId, String demensionCode) {
        List<UcDemension> queryByCodeList = ucDemensionService.queryByCode(demensionCode);
        List<String> ucOrgList = new ArrayList<>();//id集合
        //维度为xx组织下的所有组织机构的id集合
        for (UcDemension ucDemension : queryByCodeList) {
            List<UcOrg> ucOrgs = ucOrgMapper.queryByDemId(ucDemension.getId());
            for (UcOrg ucOrg : ucOrgs) {
                ucOrgList.add(ucOrg.getId());
            }
        }
        //非xx维度组织下的所有组织机构的id集合(且组织机构的引用id（REF_ID_）在第一步查出的id集合中)
        List<UcDemension> queryByNotCodeList = ucDemensionService.queryByNotCode(demensionCode);
        List<String> ucOrgNotCodeList = new ArrayList<>();//id集合
        for (UcDemension ucDemension : queryByNotCodeList) {
            List<UcOrg> ucOrgs = ucOrgMapper.queryByDemId(ucDemension.getId());
            for(UcOrg ucOrg:ucOrgs){
                if(ucOrgList.contains(ucOrg.getRefId())){
                    ucOrgNotCodeList.add(ucOrg.getId());
                }
            }
        }

        QueryFilter queryFilter = QueryFilter.build();
        //根据用户查询人与组织关系
        List<UcOrgUser> list = ucOrgUserService.getUserOrg(userId);
        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }
        String orgIds = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                orgIds = list.get(i).getOrgId();
            } else {
                orgIds = orgIds + "," + list.get(i).getOrgId();
            }
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
        orgQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (UcOrg ucOrg : returnList) {
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
            childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for (UcOrg org : orgs) {
                if (!ids.contains(org.getId())) {
                    set.add(org);
                    ids.add(org.getId());
                }
            }
        }
        List<UcOrg> ucOrgs=new ArrayList<>();
        for(UcOrg sets:set){
            if(ucOrgList.contains(sets.getId())||ucOrgNotCodeList.contains(sets.getId())){
                ucOrgs.add(sets);
            }
        }
        return ucOrgs;
    }


    //获取用户默认维度组织，以及条线对应的默认组织的合集
    public List<UcOrg> getUserOrgListMerge(String userId) {
        QueryFilter queryFilter = QueryFilter.build();
        //查询用户所在默认维度组织
        List<UcOrgUser> list=new ArrayList<>();
        List<UcOrgUser> list1 =ucOrgUserService.getUserDefaultOrg(userId);

        //查询用户所在非默认维度组织的引用默认组织（查询用户所在条线对应的默认组织，只查询地块级别）
        List<UcOrgUser> list2 =ucOrgUserService.getUserDefaultOrgByRef(userId);
        if(list1!=null&& list1.size()>0){
            list.addAll(list1);
        }
        if(list2!=null&& list2.size()>0){
            list.addAll(list2);
        }

        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }
        String orgIds = "";
        //去重
        List<String> fullOrgIds = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String tempId=list.get(i).getOrgId();
            if (!fullOrgIds.contains(tempId)) {
                if (i == 0) {
                    orgIds =tempId;
                } else {
                    orgIds = orgIds + "," +tempId;
                }
                fullOrgIds.add(tempId);
            }
        }

        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
        orgQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (UcOrg ucOrg : returnList) {
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
            childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for (UcOrg org : orgs) {
                if (!ids.contains(org.getId())) {
                    org.setDisabled("1");
                    set.add(org);
                    ids.add(org.getId());
                }
            }
            if (!ids.contains(ucOrg.getId())) {
                ucOrg.setDisabled("1");
                set.add(ucOrg);
                ids.add(ucOrg.getId());
            }
        }
        //根据组织查询父级组织
        for (UcOrg ucOrg : returnList) {
            String[] paths = ucOrg.getPath().split("\\.");
            for (int i = 0; i < paths.length; i++) {
                QueryFilter query = QueryFilter.build();
                query.addFilter("id", paths[i], QueryOP.EQUAL, FieldRelation.AND);
                query.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
                List<UcOrg> voList = this.query(query).getRows();
                for (UcOrg vo : voList) {
                    if (!ids.contains(vo.getId())) {
                        vo.setDisabled("2");
                        set.add(vo);
                        ids.add(vo.getId());
                    }
                }
            }
        }
        return set;
    }
}
