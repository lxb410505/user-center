package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.UcOrgUser;
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
        QueryFilter queryFilter =QueryFilter.build();
        //根据用户查询人与组织关系
        List<UcOrgUser> list = ucOrgUserService.getUserOrg(userId);
        if(null==list || list.size()<=0){
            return new ArrayList<>();
        }
        String orgIds = "";
        for(int i=0;i<list.size();i++){
            if(i==0){
                orgIds = list.get(i).getOrgId();
            }else{
                orgIds = orgIds+","+list.get(i).getOrgId();
            }
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id",orgIds,QueryOP.IN,FieldRelation.AND);
        orgQuery.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for(UcOrg ucOrg :returnList){
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path",ucOrg.getPath(), QueryOP.RIGHT_LIKE,FieldRelation.AND);
            childQuery.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for(UcOrg org :orgs){
                if(!ids.contains(org.getId())){
                    org.setDisabled("1");
                    set.add(org);
                    ids.add(org.getId());
                }
            }
            if(!ids.contains(ucOrg.getId())){
                ucOrg.setDisabled("1");
                set.add(ucOrg);
                ids.add(ucOrg.getId());
            }
        }
        //根据组织查询父级组织
        for(UcOrg ucOrg : returnList){
            String [] paths = ucOrg.getPath().split("\\.");
            for(int i=0;i<paths.length;i++){
                QueryFilter query = QueryFilter.build();
                query.addFilter("id",paths[i],QueryOP.EQUAL,FieldRelation.AND);
                query.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
                List<UcOrg> voList = this.query(query).getRows();
                for(UcOrg vo:voList){
                    if(!ids.contains(vo.getId())){
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
    public List<UcOrg> queryChildrenByUserId(String userId,String parentOrgId) {
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
        orgQuery.addFilter("id",orgIds,QueryOP.IN,FieldRelation.AND);
        orgQuery.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if(StringUtils.isEmpty(parentOrgId)){
            parentOrgId="0";
        }
        for(UcOrg ucOrg :returnList){
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path",ucOrg.getPath(), QueryOP.RIGHT_LIKE,FieldRelation.AND);
            childQuery.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for(UcOrg org :orgs){
                if(!ids.contains(org.getId())&&org.getParentId().equals(parentOrgId)){
                    org.setDisabled("1");
                    set.add(org);
                    ids.add(org.getId());
                }
            }
            if(!ids.contains(ucOrg.getId())&&ucOrg.getParentId().equals(parentOrgId)){
                ucOrg.setDisabled("1");
                set.add(ucOrg);
                ids.add(ucOrg.getId());
            }
        }
        return set;
    }

    //根据组织级别查询组织信息
    public List<UcOrg> queryByGrade(String userId,String grade) {
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
        orgQuery.addFilter("id",orgIds,QueryOP.IN,FieldRelation.AND);
        orgQuery.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
        List<UcOrg> returnList = this.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for(UcOrg ucOrg :returnList){
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path",ucOrg.getPath(), QueryOP.RIGHT_LIKE,FieldRelation.AND);
            childQuery.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
            List<UcOrg> orgs = this.query(childQuery).getRows();
            for(UcOrg org :orgs){
                if(!StringUtils.isEmpty(org.getGrade())) {
                    if (!ids.contains(org.getId())&& org.getGrade().equals(grade)) {
                        org.setDisabled("1");
                        set.add(org);
                        ids.add(org.getId());
                    }
                }
            }
            if(!ids.contains(ucOrg.getId())&&ucOrg.getGrade().equals(grade)){
                ucOrg.setDisabled("1");
                set.add(ucOrg);
                ids.add(ucOrg.getId());
            }
        }
        return set;
    }

    @Override
    public List<UcOrg> queryChildrenByOrgId(String orgId, String grade) {
        QueryFilter childQuery = QueryFilter.build();
        childQuery.addFilter("path",orgId, QueryOP.LIKE,FieldRelation.AND);
        childQuery.addFilter("id",orgId, QueryOP.NOT_EQUAL,FieldRelation.AND);
        childQuery.addFilter("grade",grade, QueryOP.EQUAL,FieldRelation.AND);
        childQuery.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
        List<UcOrg> orgs = this.query(childQuery).getRows();
        return orgs;
    }
}
