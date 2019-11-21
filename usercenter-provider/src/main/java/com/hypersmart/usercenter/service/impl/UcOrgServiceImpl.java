package com.hypersmart.usercenter.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.hypersmart.base.annotation.cache.Cache;
import com.hypersmart.base.cache.ICache;
import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.AppUtil;
import com.hypersmart.base.util.JsonUtil;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.bo.UcOrgBO;
import com.hypersmart.usercenter.constant.RedisKeys;
import com.hypersmart.usercenter.dto.UcOrgDTO;
import com.hypersmart.usercenter.dto.UcOrgExtend;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.mapper.UcOrgParamsMapper;
import com.hypersmart.usercenter.model.UcDemension;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.*;
import javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @Autowired
    UcOrgParamsMapper ucOrgParamsMapper;

    @Autowired
    private StageServiceGirdRefService stageServiceGirdRefService;

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

//    @Override
    /**
     *  获取用户权限列表
     *  diasbled:1->有权限 2：->无权限只能查看
     * */
//    public List<UcOrg> getUserOrgList2(String userId) {
//        QueryFilter queryFilter = QueryFilter.build();
//        //根据用户查询人与组织关系
//        List<UcOrgUser> list = ucOrgUserService.getUserOrg(userId);
//        if (null == list || list.size() <= 0) {
//            return new ArrayList<>();
//        }
//        String orgIds = "";
//        for (int i = 0; i < list.size(); i++) {
//            if (i == 0) {
//                orgIds = list.get(i).getOrgId();
//            } else {
//                orgIds = orgIds + "," + list.get(i).getOrgId();
//            }
//        }
//
//
//         根据组织id获取组织信息
//        QueryFilter orgQuery = QueryFilter.build();
//        orgQuery.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
//        orgQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
//        List<UcOrg> returnList = this.query(orgQuery).getRows();
//        //根据组织获取子级
//        List<UcOrg> set = new ArrayList<>();
//        List<String> ids = new ArrayList<>();
//        for (UcOrg ucOrg : returnList) {
//            QueryFilter childQuery = QueryFilter.build();
//            childQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
//            childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
//            List<UcOrg> orgs = this.query(childQuery).getRows();
//            for (UcOrg org : orgs) {
//                if (!ids.contains(org.getId())) {
//                    org.setDisabled("1");
//                    set.add(org);
//                    ids.add(org.getId());
//                }
//            }
//            if (!ids.contains(ucOrg.getId())) {
//                ucOrg.setDisabled("1");
//                set.add(ucOrg);
//                ids.add(ucOrg.getId());
//            }
//        }
//
//        QueryFilter query = QueryFilter.build();
//        query.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
//        Set<String> parentIds=new HashSet<>();
//
//        //根据组织查询父级组织
//        for (UcOrg ucOrg : returnList) {
//            List<String> paths = Arrays.asList(ucOrg.getPath().split("\\."));
//            parentIds.addAll(paths);
//        }
//
//        if(parentIds.size()>0){
//            query.addFilter("id", String.join(",",parentIds), QueryOP.IN, FieldRelation.AND);
//            List<UcOrg> voList = this.query(query).getRows();
//            for (UcOrg vo : voList) {
//                if (!ids.contains(vo.getId())) {
//                    vo.setDisabled("2");
//                    set.add(vo);
//                    ids.add(vo.getId());
//                }
//            }
//        }
//    }

    /**
     *  获取用户权限列表
     *  diasbled:1->有权限 2：->无权限只能查看
     * */
    @Override
    public List<UcOrg> getUserOrgList(String userId) {
        QueryFilter queryFilter = QueryFilter.build();
        //根据用户查询人与组织关系
        List<UcOrgUser> list = ucOrgUserService.getUserOrg(userId);
        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }
        List<String> orgIdList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            orgIdList.add(list.get(i).getOrgId());
        }
        List<UcOrg> offlineResult= getAuthOrgListByOrgIds(orgIdList);
        return  offlineResult;
    }

    //根据path获取子组织（离线）
    private List<UcOrg> getChildListByPath(List<UcOrg> orgList,String path){
        List<UcOrg> result = new ArrayList<>();
        for(UcOrg org:orgList){
            if(org.getPath().contains(path)){
                result.add(org);
            }
        }
        return result;
    }

    //根据id获取组织（离线）
    private UcOrg getOrgById(List<UcOrg> orgList,String id){
        for(UcOrg org:orgList){
            if(id.equals(org.getId())){
                return org;
            }
        }
        return null;
    }

    //根据用户关联的组织ids获取有权限的组织列表diasbled:1->有权限 2：->无权限只能查看
    public List<UcOrg> getAuthOrgListByOrgIds(List<String> orgIds) {
        List<UcOrg>  allOrgList=this.getAllOrgs();
        //根据组织id获取组织信息
        List<UcOrg> set = new ArrayList<>();
        List<UcOrg> returnList = new ArrayList<>();
        //1、获取关联组织
        for(UcOrg org:allOrgList){
            if(orgIds.contains(org.getId())){
                returnList.add(org);
            }
        }

        //2、获取子级
        List<String> ids = new ArrayList<>();
        for (UcOrg ucOrg : returnList) {
            List<UcOrg> childList= getChildListByPath(allOrgList,ucOrg.getPath());
            for (UcOrg child : childList) {
                if (!ids.contains(child.getId())) {
                    child.setDisabled("1");
                    set.add(child);
                    ids.add(child.getId());
                }
            }
        }

        //3、根据组织查询父级组织
        for (UcOrg ucOrg : returnList) {
            String[] paths = ucOrg.getPath().split("\\.");
            for (int i = 0; i < paths.length; i++) {
               UcOrg anceOrg= getOrgById(allOrgList,paths[i]);
               if(anceOrg!=null){
                   if (!ids.contains(anceOrg.getId())) {
                       anceOrg.setDisabled("2");
                       set.add(anceOrg);
                       ids.add(anceOrg.getId());
                   }
               }
            }
        }
        return set;
    }

    //根据userId和组织父级id查询组织信息
    public List<UcOrgDTO> queryChildrenByUserId(String userId, String parentOrgId) {
        List<UcOrg> orgList = this.getUserOrgListMerge(userId);//getUserOrgList--edit by lily
        if (orgList != null && orgList.size() > 0) {
            if (StringUtils.isRealEmpty(parentOrgId)) {
                parentOrgId = "0";
            }
            List<UcOrgDTO> ucOrgDTOList = new ArrayList<>();
            String parentCode = "";
            for (UcOrg ucOrg : orgList) {
                if (parentOrgId.equals(ucOrg.getParentId())) {
                    UcOrgDTO ucOrgDTO = new UcOrgDTO();
                    BeanUtils.copyProperties(ucOrg, ucOrgDTO);
                    if (StringUtils.isRealEmpty(parentCode)) {
                        parentCode = ucOrgMapper.getCodeById(parentOrgId);
                    }
                    ucOrgDTO.setParentCode(parentCode);
                    ucOrgDTOList.add(ucOrgDTO);
                }
            }
            return ucOrgDTOList;
        } else {
            return new ArrayList<>();
        }
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
    public List<UcOrg> queryByParents(String orgId, String grade) {

        UcOrg org = this.get(orgId);

        QueryFilter childQuery = QueryFilter.build();
//        childQuery.addFilter("path", org.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
//        childQuery.addFilter("id", orgId, QueryOP.NOT_EQUAL, FieldRelation.AND);
//        childQuery.addFilter("grade", grade, QueryOP.EQUAL, FieldRelation.AND);
        childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        childQuery.addFilter("parentId", org.getParentId(), QueryOP.NOT_EQUAL, FieldRelation.AND);
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
            for (UcOrg ucOrg : ucOrgs) {
                if (ucOrgList.contains(ucOrg.getRefId())) {
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
            // 获取指定用户ID下的组织
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
        List<UcOrg> ucOrgs = new ArrayList<>();
        for (UcOrg sets : set) {
            if (ucOrgList.contains(sets.getId()) || ucOrgNotCodeList.contains(sets.getId())) {
                ucOrgs.add(sets);
            }
        }
        return ucOrgs;
    }

    /**
     * 根据组织id获取所有维度的关联组织及当前组织
     *
     * @param query
     * @return
     */
    public List<UcOrgExtend> getAllDimOrgListByOrg(UcOrgBO query) {
        return ucOrgMapper.getAllDimOrgListByOrg(query);
    }


    //获取用户默认维度组织，以及条线对应的默认组织的合集（排除行政-edit by lily 0515）
    public List<UcOrg> getUserOrgListMerge(String userId) {
        QueryFilter queryFilter = QueryFilter.build();
        //查询用户所在默认维度组织
        List<UcOrgUser> list = new ArrayList<>();
        List<UcOrgUser> list1 = ucOrgUserService.getUserDefaultOrg(userId);

        //查询用户所在非默认维度组织的引用默认组织（查询用户所在条线对应的默认组织，只查询地块级别）
        List<UcOrgUser> list2 = ucOrgUserService.getUserDefaultOrgByRef(userId);
        if (list1 != null && list1.size() > 0) {
            list.addAll(list1);
        }
        if (list2 != null && list2.size() > 0) {
            list.addAll(list2);
        }

        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }
        String orgIds = "";
        //去重
        List<String> fullOrgIds = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String tempId = list.get(i).getOrgId();
            if (!fullOrgIds.contains(tempId)) {
                if (i == 0) {
                    orgIds = tempId;
                } else {
                    orgIds = orgIds + "," + tempId;
                }
                fullOrgIds.add(tempId);
            }
        }

        //根据组织id获取组织信息
//        QueryFilter orgQuery = QueryFilter.build();
//        orgQuery.addFilter("id", orgIds, QueryOP.IN, FieldRelation.AND);
//        orgQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
//        List<UcOrg> returnList = this.query(orgQuery).getRows();
//        //根据组织获取子级
//        List<UcOrg> set = new ArrayList<>();
//        List<String> ids = new ArrayList<>();
//        for (UcOrg ucOrg : returnList) {
//            QueryFilter childQuery = QueryFilter.build();
//            childQuery.addFilter("path", ucOrg.getPath(), QueryOP.RIGHT_LIKE, FieldRelation.AND);
//            childQuery.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
//            List<UcOrg> orgs = this.query(childQuery).getRows();
//            for (UcOrg org : orgs) {
//                if (!ids.contains(org.getId())) {
//                    org.setDisabled("1");
//                    set.add(org);
//                    ids.add(org.getId());
//                }
//            }
//            if (!ids.contains(ucOrg.getId())) {
//                ucOrg.setDisabled("1");
//                set.add(ucOrg);
//                ids.add(ucOrg.getId());
//            }
//        }
//        //根据组织查询父级组织
//        for (UcOrg ucOrg : returnList) {
//            String[] paths = ucOrg.getPath().split("\\.");
//            for (int i = 0; i < paths.length; i++) {
//                QueryFilter query = QueryFilter.build();
//                query.addFilter("id", paths[i], QueryOP.EQUAL, FieldRelation.AND);
//                query.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
//                List<UcOrg> voList = this.query(query).getRows();
//                for (UcOrg vo : voList) {
//                    if (!ids.contains(vo.getId())) {
//                        vo.setDisabled("2");
//                        set.add(vo);
//                        ids.add(vo.getId());
//                    }
//                }
//            }
//        }

        List<UcOrg> set=getAuthOrgListByOrgIds(fullOrgIds);

        Set<String> xingZhengList=new HashSet<>();
        for(UcOrg item:set){
            if ("ORG_XingZheng".equals(item.getGrade())) {
                xingZhengList.add(item.getId());
            }
        }

        List<UcOrg> resultSet = new ArrayList<>();
        for(UcOrg item:set){
            Boolean isUnder=false;
            for(String xzId:xingZhengList){
                if(item.getPath().contains(xzId)){
                    isUnder=true;
                    break;
                }
            }
            if(!isUnder){
                resultSet.add(item);
            }
        }
        resultSet=resultSet.stream().sorted(Comparator.comparing(a -> null ==a.getOrderNo()?1000:a.getOrderNo())).collect(Collectors.toList());
        return resultSet;
    }


    public List<UcOrg> queryChildrenByCondition(String userId, String orgId, String grade) {


        UcOrg org = this.ucOrgMapper.get(orgId);
        if (org == null) {
            return new ArrayList<>();
        }

        Set<String> list = new HashSet<String>();
        List<UcOrg> list1 = getUserOrgListMerge(userId);

        if (list1 != null && list1.size() > 0) {
            for (UcOrg o : list1) {
                if ("1".equals(o.getDisabled())) {
                    list.add(o.getId());
                }
            }
        }

        if (null == list || list.size() <= 0) {
            return new ArrayList<>();
        }

        return ucOrgMapper.getChildrenOrgByCondition(org.getPath(), list, grade);


    }

    /**
     * 根据userID查询他下面的所有org组织
     * @param userId
     * @return
     */
    public List<UcOrg> queryAllOrgsByUserId(String userId){
        List<UcOrg> returnOrgs = new ArrayList<>();
        if(StringUtil.isEmpty(userId)){
            return new ArrayList<>();
        }
        returnOrgs = this.ucOrgMapper.getAllOrgsByUserId(userId);
        if(com.hypersmart.base.util.BeanUtils.isEmpty(returnOrgs)){
            return new ArrayList<>();
        }
        List<UcOrg> ZBList = new ArrayList<>();
        List<UcOrg> areaList = new ArrayList<>();
        List<UcOrg> cityList = new ArrayList<>();
        List<UcOrg> projectList = new ArrayList<>();
        List<UcOrg> stagingList = new ArrayList<>();
        for (int i= 0;i<returnOrgs.size();i++){
            if(returnOrgs.get(i).getGrade().equals("ORG_ZongBu")){
                ZBList.add(returnOrgs.get(i));
            }else if(returnOrgs.get(i).getGrade().equals("ORG_QuYu")){
                areaList.add(returnOrgs.get(i));
            }else if(returnOrgs.get(i).getGrade().equals("ORG_ChengQu")){
                cityList.add(returnOrgs.get(i));
            }else if(returnOrgs.get(i).getGrade().equals("ORG_XiangMu")){
                projectList.add(returnOrgs.get(i));
            }else if(returnOrgs.get(i).getGrade().equals("ORG_DiKuai")){
                stagingList.add(returnOrgs.get(i));
            }
        }
        returnOrgs.clear();
        if(ZBList!=null && ZBList.size()>0){
            returnOrgs.addAll(ZBList);
            returnOrgs.addAll(this.queryChildrenByOrgId(ZBList.get(0).getId(),"ORG_QuYU"));
            returnOrgs.addAll(this.queryChildrenByOrgId(ZBList.get(0).getId(),"ORG_ChengQu"));
            returnOrgs.addAll(this.queryChildrenByOrgId(ZBList.get(0).getId(),"ORG_XiangMu"));
            returnOrgs.addAll(this.queryChildrenByOrgId(ZBList.get(0).getId(),"ORG_DiKuai"));
        }else if (areaList!=null && areaList.size()>0){
            returnOrgs.addAll(areaList);
            for (int i=0;i<areaList.size();i++){
                returnOrgs.addAll(this.queryChildrenByOrgId(areaList.get(i).getId(),"ORG_ChengQu"));
                returnOrgs.addAll(this.queryChildrenByOrgId(areaList.get(i).getId(),"ORG_XiangMu"));
                returnOrgs.addAll(this.queryChildrenByOrgId(areaList.get(i).getId(),"ORG_DiKuai"));
            }
        }else if (cityList!=null && cityList.size()>0){
            returnOrgs.addAll(cityList);
            for (int i=0;i<cityList.size();i++){
                returnOrgs.addAll(this.queryChildrenByOrgId(cityList.get(i).getId(),"ORG_XiangMu"));
                returnOrgs.addAll(this.queryChildrenByOrgId(cityList.get(i).getId(),"ORG_DiKuai"));
            }
        }else if (projectList!=null && projectList.size()>0){
            returnOrgs.addAll(projectList);
            for (int i=0;i<projectList.size();i++){
                returnOrgs.addAll(this.queryChildrenByOrgId(projectList.get(i).getId(),"ORG_DiKuai"));
            }
        }else {
            returnOrgs.addAll(stagingList);
        }
        for (int i=0;i<returnOrgs.size();i++){
            if (returnOrgs.get(i).getGrade().equals("ORG_ZhongBu"));
        }
        return returnOrgs;
    }

    public List<UcOrg> getDefaultOrgList() {

        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.setPageBean(null);
        queryFilter.addFilter("IS_DELE_", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        queryFilter.addFilter("IS_DEFAULT_", 1, QueryOP.EQUAL, FieldRelation.AND);
        PageList<UcDemension> demensionPageList = ucDemensionService.query(queryFilter);
        if (demensionPageList.getRows().size() > 0) {
            UcDemension demension = demensionPageList.getRows().get(0);

            QueryFilter queryFilter2 = QueryFilter.build();
            queryFilter2.setPageBean(null);
            queryFilter2.addFilter("IS_DELE_", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            queryFilter2.addFilter("DEM_ID_", demension.getId(), QueryOP.EQUAL, FieldRelation.AND);
            PageList<UcOrg> pageList = this.query(queryFilter2);
            return pageList.getRows();
        } else {
            return null;
        }


    }

    public List<UcOrg> getDefaultOrgListByGrade(String grade) {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.setPageBean(null);
        queryFilter.addFilter("IS_DELE_", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
        queryFilter.addFilter("IS_DEFAULT_", 1, QueryOP.EQUAL, FieldRelation.AND);
        PageList<UcDemension> demensionPageList = ucDemensionService.query(queryFilter);
        if (demensionPageList.getRows().size() > 0) {
            UcDemension demension = demensionPageList.getRows().get(0);

            QueryFilter queryFilter2 = QueryFilter.build();
            queryFilter2.setPageBean(null);
            queryFilter2.addFilter("IS_DELE_", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            queryFilter2.addFilter("DEM_ID_", demension.getId(), QueryOP.EQUAL, FieldRelation.AND);
            queryFilter2.addFilter("GRADE_", grade, QueryOP.EQUAL, FieldRelation.AND);
            PageList<UcOrg> pageList = this.query(queryFilter2);
            return pageList.getRows();
        } else {
            return null;
        }
    }


    public List<UcOrgParams> getOrgParams(String code, String value) {

        if(StringUtil.isEmpty(code) || StringUtil.isEmpty(value)){
            return null;
        }
        return  ucOrgParamsMapper.getOrgParams(code,value);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Autowired
    private ICache<String> iCache;
    public List<UcOrg> getAllOrgs() {
        String key=RedisKeys.UC_ORRG+".getAllOrgs";
        if (iCache.containKey(key)) {
            List<UcOrg> ucOrgList= null;
            try {
                ucOrgList = JsonUtil.toBean(iCache.getByKey(key),new TypeReference<List<UcOrg>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("*************** from CACHE  KEY:" + key);
            return ucOrgList;
        }
        List<UcOrg> ucOrgList2= ucOrgMapper.getAllOrgs();
        try {
            iCache.add(key, JsonUtil.toJson(ucOrgList2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ucOrgList2;
    }

    @Override
    public List<UcOrg> getOrgByCondition(UcOrg ucOrg) {
        return ucOrgMapper.getOrgByCondition(ucOrg);
    }

    @Override
    public List<UcOrg> queryOrgByDemensionCode(String userId, String demensionCode) {

        //根据用户查询人与组织关系
        Map<String,Object> map= Maps.newHashMap();
        map.put("userId",userId);
        map.put("demCode",demensionCode);
        List<UcOrgUser> list = ucOrgUserService.getUserWithDemByCondition(map);

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
            // 获取指定用户ID下的组织
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

        return set;
    }
}
