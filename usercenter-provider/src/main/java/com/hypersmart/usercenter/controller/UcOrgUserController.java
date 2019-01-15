package com.hypersmart.usercenter.controller;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgPost;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.UcOrgPostService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcOrgUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户组织关系
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:59:39
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucOrgUser"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucOrgUserController"})
public class UcOrgUserController extends BaseController {
    @Resource
    UcOrgUserService ucOrgUserService;

    @Resource
    UcUserService ucUserService;

    @Resource
    UcOrgService ucOrgService;

    @Resource
    UcOrgPostService ucOrgPostService;

    @Value("${grid.code}")
    String gridCode;

    @PostMapping({"/list"})
    @ApiOperation(value = "用户组织关系数据列表}", httpMethod = "POST", notes = "获取用户组织关系列表")
    public PageList<UcOrgUser> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgUserService.query(queryFilter);
    }
//    @PostMapping({"/queryList"})
//    @ApiOperation(value = "用户组织关系数据列表}", httpMethod = "POST", notes = "获取用户组织关系列表")
//    public PageList<Map<String,String>> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
//
//        //根据用户信息获取用户所属组织
//        IUser user =  ContextUtil.getCurrentUser();
//        //获取用户组织关系
//        List<UcOrgUser> refList = ucOrgUserService.getUserOrg("461066");
//        String orgIds = "";
//        for(int i=0;i<refList.size();i++){
//            if(i==0){
//                orgIds = refList.get(i).getOrgId();
//            }else{
//                orgIds = orgIds+","+refList.get(i).getOrgId();
//            }
//        }
//        //根据组织id获取组织信息
//        QueryFilter orgQuery = QueryFilter.build();
//        orgQuery.addFilter("id",orgIds,QueryOP.IN);
//        List<UcOrg> returnList = ucOrgService.query(orgQuery).getRows();
//        List<UcOrg> orgList = new ArrayList<>();
//        //判断获取的组织等级
//        for(UcOrg ucOrg :returnList){
//            //获取当前用户关联组织的第四级别
//            if(ucOrg.getPath().split("\\.").length>6){
//                //不记录当前组织
//                continue;
//            }else if(ucOrg.getPath().split("\\.").length==5){
//                //直接记录--》分期
//                if(!orgList.contains(ucOrg)){
//                    orgList.add(ucOrg);
//                }
//            }else{
//                //根据地块获取分期
//                QueryFilter temp = QueryFilter.build();
//                temp.addFilter("path",ucOrg.getPath(),QueryOP.RIGHT_LIKE);
//                List<UcOrg> tempList = ucOrgService.query(temp).getRows();
//                for(UcOrg vo :tempList){
//                    String[] str = vo.getPath().split("\\.");
//                    if(str.length==5){
//                        if(!orgList.contains(vo)){
//                            orgList.add(vo);
//                        }
//                    }
//                }
//            }
//        }
//        //获取查询参数
//        Map<String,Object> map = queryFilter.getParams();
//        if(null != map){
//            Map<String,String> roleMap = new HashMap<>();
//            String orgId = null;
//            if(null != map.get("orgId")){
//                orgId = map.get("orgId").toString();
//                roleMap.put("orgId", map.get("orgId").toString());
//            }
//            if(null != map.get("projectId")){
//                orgId = map.get("projectId").toString();
//                roleMap.put("orgId", map.get("projectId").toString());
//            }
//            if(null != map.get("plotId")){
//                orgId = map.get("plotId").toString();
//                roleMap.put("orgId", map.get("plotId").toString());
//            }
//            if(null != map.get("divideId")){
//                orgId = map.get("divideId").toString();
//                roleMap.put("orgId", map.get("divideId").toString());
//            }
//            UcOrg ucOrg = ucOrgService.get(orgId);
//            if(null != ucOrg){
//                List<UcOrg> ucList = ucOrgService.getChildrenOrg(ucOrg);
//                for(int i=0;i<ucList.size();i++){
//                    if(!orgList.contains(ucList.get(i))){
//                        ucList.remove(i);
//                    }
//                }
//                orgList = ucList;
//            }
//            queryFilter.setParams(new HashMap<>());
//        }
//
//        String orgId = "";
//        for(int i=0;i<orgList.size();i++){
//            if(i==0){
//                orgId = orgList.get(i).getId();
//            }else{
//                orgId = orgId + "," + orgList.get(i).getId();
//            }
//        }
//        if("".equals(orgId)){
//            return new PageList<>();
//        }
//        queryFilter.addFilter("orgId",orgId,QueryOP.IN,FieldRelation.AND);
//
//        //获取管家的postId
//        List<String> postIds = ucOrgUserService.getPostIdByjobCode("guanjia");
//        String postId = "";
//        Map<String,UcOrgPost> postMap = new HashMap<>();
//        for(int i=0;i<postIds.size();i++){
//            UcOrgPost ucOrgPost = ucOrgPostService.get(postIds.get(i));
//            postMap.put(ucOrgPost.getId(),ucOrgPost);
//            if(i==0){
//                postId = postIds.get(i);
//            }else{
//                postId = postId +","+ postIds.get(i);
//            }
//        }
//        if("".equals(postId)){
//            return new PageList<>();
//        }
//        queryFilter.addFilter("posId",postId,QueryOP.IN,FieldRelation.AND);
//
//        //根据查询参数查询区域人员关系
//        queryFilter.setParams(new HashMap<>());
//        PageList<UcOrgUser> page = this.ucOrgUserService.query(queryFilter);
//
//        orgQuery = QueryFilter.build();
//        orgQuery.addFilter("id",orgId,QueryOP.IN,FieldRelation.AND);
//        List<UcOrg> orgs = ucOrgService.query(orgQuery).getRows();
//        Map<String,UcOrg> orgMaps = new HashMap<>();
//        for(UcOrg ucOrg:orgs){
//            orgMaps.put(ucOrg.getId(),ucOrg);
//        }
//
//        QueryFilter query = QueryFilter.build();
//        //查询出人员与组织的关系
//        if(null != page && null != page.getRows() && page.getRows().size()>0){
//            String str="";
//            for(int i=0;i<page.getRows().size();i++){
//                if(i==0){
//                    str = page.getRows().get(i).getUserId();
//                }else{
//                    str = str+","+page.getRows().get(i).getUserId();
//                }
//            }
//            query.setParams(map);
//            query.setPageBean(queryFilter.getPageBean());
//            query.addFilter("id",str,QueryOP.IN,FieldRelation.AND);
//            query.addFilter("IS_DELE_",0,QueryOP.EQUAL,FieldRelation.AND);
//        }else{
//            return new PageList<>();
//        }
//        //查询人员
//        PageList<UcUser> userPage = this.ucUserService.query(query);
//        Map<String,UcUser> userMap = new HashMap<>();
//        for(UcUser ucUser :userPage.getRows()){
//            userMap.put(ucUser.getId(),ucUser);
//        }
//
//        //循环人员与组织关系
//        List<Map<String,String>> list = new ArrayList<>();
//        for(int i=0;i<page.getRows().size();i++){
//           Map<String,String> returnMap = new HashMap<>();
//           UcOrgUser ucOrgUser = page.getRows().get(i);
//           UcUser ucUser = userMap.get(ucOrgUser.getUserId());
//           UcOrg ucOrg = orgMaps.get(ucOrgUser.getOrgId());
//           UcOrgPost post = postMap.get(ucOrgUser.getPosId());
//            if(null != ucUser && null !=ucOrg && null != post){
//                returnMap.put("id",ucOrgUser.getId());
//                returnMap.put("houseKeeperId",ucUser.getId());
//                returnMap.put("account",ucUser.getAccount());
//                returnMap.put("houseKeeperName",ucUser.getFullname());
//                returnMap.put("mobilePhone",ucUser.getMobile());
//                returnMap.put("divideId",ucOrg.getId());
//                returnMap.put("divide",ucOrg.getName());
//                String str[] = ucOrg.getPathName().split("/");
//                returnMap.put("area",str[1]);
//                returnMap.put("project",str[2]);
//                returnMap.put("plot",str[3]);
//                returnMap.put("level",post.getPosName());
//                list.add(returnMap);
//            }
//        }
//        PageList<Map<String,String>> pageList = new PageList<>();
//        pageList.setTotal(page.getTotal());
//        pageList.setPage(page.getPage());
//        pageList.setPageSize(page.getPageSize());
//        pageList.setRows(list);
//        return pageList;
//    }


    @PostMapping({"/queryList"})
    @ApiOperation(value = "用户组织关系数据列表}", httpMethod = "POST", notes = "获取用户组织关系列表")
    public PageList<Map<String,String>> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        PageList<Map<String,String>> page = null;
        Map<String,String> params = new HashMap<>();

        Map<String,Object> map = queryFilter.getParams();
        PageBean pageBean = queryFilter.getPageBean();
        List<FieldSort> sorts = queryFilter.getSorter();
        StringBuffer where = new StringBuffer(" 1=1 ");
        if(null != map && map.size()>0){
            if(null != map.get("orgId")){
                where.append(" and areaId =");
                where.append(map.get("areaId").toString());
            }
            if(null != map.get("projectId")){
                where.append(" and projectId =");
                where.append(map.get("projectId").toString());
            }
            if(null != map.get("plotId")){
                where.append(" and plotId =");
                where.append(map.get("plotId").toString());
            }
            if(null != map.get("divideId")){
                where.append(" and divideId =");
                where.append(map.get("divideId").toString());
            }
            if(null != map.get("account")){
                where.append(" and account =");
                where.append(map.get("account").toString());
            }
            if(null != map.get("fullname")){
                where.append(" and fullname =");
                where.append(map.get("fullname").toString());
            }
            if(null != map.get("mobile")){
                where.append(" and mobile =");
                where.append(map.get("mobile").toString());
            }
        }

        //根据用户信息获取用户所属组织
        IUser user =  ContextUtil.getCurrentUser();
        //获取用户组织关系
        List<UcOrgUser> refList = ucOrgUserService.getUserOrg("461066");
        String orgIds = "";
        for(int i=0;i<refList.size();i++){
            if(i==0){
                orgIds = refList.get(i).getOrgId();
            }else{
                orgIds = orgIds+","+refList.get(i).getOrgId();
            }
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id",orgIds,QueryOP.IN);
        List<UcOrg> returnList = ucOrgService.query(orgQuery).getRows();
        List<UcOrg> orgList = new ArrayList<>();
        //判断获取的组织等级
        for(UcOrg ucOrg :returnList){
            //获取当前用户关联组织的第四级别
            if(ucOrg.getPath().split("\\.").length>6){
                //不记录当前组织
                continue;
            }else if(ucOrg.getPath().split("\\.").length==5){
                //直接记录--》分期
                if(!orgList.contains(ucOrg)){
                    orgList.add(ucOrg);
                }
            }else{
                //根据地块获取分期
                QueryFilter temp = QueryFilter.build();
                temp.addFilter("path",ucOrg.getPath(),QueryOP.RIGHT_LIKE);
                List<UcOrg> tempList = ucOrgService.query(temp).getRows();
                for(UcOrg vo :tempList){
                    String[] str = vo.getPath().split("\\.");
                    if(str.length==5){
                        if(!orgList.contains(vo)){
                            orgList.add(vo);
                        }
                    }
                }
            }
        }
        if(orgList.size()>0){
            orgIds = "(";
            for(int i=0;i<orgList.size();i++){
                if(i==0){
                    orgIds = orgIds +orgList.get(i).getId();
                }else{
                    orgIds = orgIds + "," + orgList.get(i).getId();
                }
            }
            orgIds = orgIds +")";
            where.append( " and divideId in ");
            where.append(orgIds);
        }else{
            page = new PageList<>();
            if(BeanUtils.isEmpty(pageBean)){
                page.setPage(1);
                page.setPageSize(10);
                page.setRows(new ArrayList<>());
                page.setTotal(new Long(0));
            }
            return page;
        }
        //获取管家的postId
        List<String> postIds = ucOrgUserService.getPostIdByjobCode(gridCode);
        String postId = "";
        Map<String,UcOrgPost> postMap = new HashMap<>();
        for(int i=0;i<postIds.size();i++){
            UcOrgPost ucOrgPost = ucOrgPostService.get(postIds.get(i));
            postMap.put(ucOrgPost.getId(),ucOrgPost);
            if(i==0){
                postId = postIds.get(i);
            }else{
                postId = postId +","+ postIds.get(i);
            }
        }
        if("".equals(postId)){
            page = new PageList<>();
            if(BeanUtils.isEmpty(pageBean)){
                page.setPage(1);
                page.setPageSize(10);
                page.setRows(new ArrayList<>());
                page.setTotal(new Long(0));
            }
            return page;
        }else{
            postId = "("+postId +")";
            where.append( " and postId in ");
            where.append(postId);
        }
        params.put("whereSql",where.toString());

        if(sorts.size()>0){
            String sort = " order by";
            for(int i=0;i<sorts.size();i++){
                if(i==0){
                    sort = sort +sorts.get(i).getProperty()+" "+sorts.get(i).getDirection().name();
                }else{
                    sort = sort + "," +sorts.get(i).getProperty()+" "+sorts.get(i).getDirection().name();
                }
            }
            params.put("orderBySql",where.toString());
        }
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        List<Map<String,String>> query = this.ucOrgUserService.quertList(params);
        if(null != query){
            return new PageList<Map<String,String>>(query);
        }
        return new PageList<Map<String,String>>();
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "用户组织关系数据列表", httpMethod = "GET", notes = "获取单个用户组织关系记录")
    public UcOrgUser get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgUserService.get(id);
    }


//    @PostMapping({"add"})
//    @ApiOperation(value = "新增用户组织关系信息", httpMethod = "POST", notes = "保存用户组织关系")
//    public CommonResult<String> post(@ApiParam(name = "ucOrgUser", value = "用户组织关系业务对象", required = true) @RequestBody UcOrgUser model) {
//        String msg = "添加用户组织关系成功";
//        this.ucOrgUserService.insert(model);
//        return new CommonResult(msg);
//    }
//
//    @PutMapping({"update"})
//    @ApiOperation(value = "更新指定id的 用户组织关系 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 用户组织关系 信息（更新全部信息）")
//    public CommonResult<String> put(@ApiParam(name = "ucOrgUser", value = "用户组织关系业务对象", required = true) @RequestBody UcOrgUser model) {
//        String msg = "更新用户组织关系成功";
//        this.ucOrgUserService.update(model);
//        return new CommonResult(msg);
//    }
//
//    @PatchMapping({"update"})
//    @ApiOperation(value = "更新指定id的 用户组织关系 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 用户组织关系 信息（更新部分信息）")
//    public CommonResult<String> patch(@ApiParam(name = "ucOrgUser", value = "用户组织关系业务对象", required = true) @RequestBody UcOrgUser model) {
//        String msg = "更新用户组织关系成功";
//        this.ucOrgUserService.updateSelective(model);
//        return new CommonResult(msg);
//    }
//
//    @DeleteMapping({"remove/{id}"})
//    @ApiOperation(value = "删除用户组织关系记录", httpMethod = "DELETE", notes = "删除用户组织关系记录")
//    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
//        this.ucOrgUserService.delete(id);
//        return new CommonResult(true, "删除成功");
//    }
//
//    @DeleteMapping({"/remove"})
//    @ApiOperation(value = "批量删除用户组织关系记录", httpMethod = "DELETE", notes = "批量删除用户组织关系记录")
//    public CommonResult<String> removes(
//            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
//        String[] aryIds = ids.split(",");
//        this.ucOrgUserService.delete(aryIds);
//        return new CommonResult(true, "批量删除成功");
//    }
}
