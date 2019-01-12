package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcOrgUserService;

import java.util.ArrayList;
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

    @PostMapping({"/list"})
    @ApiOperation(value = "用户组织关系数据列表}", httpMethod = "POST", notes = "获取用户组织关系列表")
    public PageList<UcOrgUser> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgUserService.query(queryFilter);
    }
    @PostMapping({"/queryList"})
    @ApiOperation(value = "用户组织关系数据列表}", httpMethod = "POST", notes = "获取用户组织关系列表")
    public PageList<UcUser> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {

        //根据用户信息获取用户所属组织
        IUser user =  ContextUtil.getCurrentUser();
        List<UcOrgUser> refList = ucOrgUserService.getUserOrg("1012");
        List<UcOrg> returnList = ucOrgService.getOrg(refList);
        List<UcOrg> list = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for(UcOrg ucOrg :returnList){
            List<UcOrg> orgs = ucOrgService.getChildrenOrg(ucOrg);
            for(UcOrg org :orgs){
                if(!idList.contains(org.getId())){
                    idList.add(org.getId());
                    list.add(org);
                }
            }
        }
        //获取查询参数
        Map<String,Object> map = queryFilter.getParams();
        if(null != map){
            String orgId = null;
            if(null != map.get("orgId")){
                orgId = map.get("orgId").toString();
                map.remove("orgId");
            }
            if(null != map.get("projectId")){
                orgId = map.get("projectId").toString();
                map.remove("projectId");
            }
            if(null != map.get("divideId")){
                orgId = map.get("divideId").toString();
                map.remove("divideId");
            }
            UcOrg ucOrg = ucOrgService.get(orgId);
            if(null != ucOrg){
                List<UcOrg> ucList = ucOrgService.getChildrenOrg(ucOrg);
                for(int i=0;i<ucList.size();i++){
                    if(!list.contains(ucList.get(i))){
                        ucList.remove(i);
                    }
                }
                list = ucList;
            }
            queryFilter.setParams(map);
        }

        String orgId = "";
        for(int i=0;i<list.size();i++){
            if(i==0){
                orgId = list.get(i).getId();
            }else{
                orgId = orgId + "," + list.get(i).getId();
            }
        }
        queryFilter.addFilter("orgId",orgId,QueryOP.IN);

        //根据查询参数查询区域人员关系
        QueryFilter query = queryFilter;
        query.setPageBean(null);
        PageList<UcOrgUser> page = this.ucOrgUserService.query(queryFilter);
        if(null != page && null != page.getRows() && page.getRows().size()>0){
            String str="";
            for(int i=0;i<page.getRows().size();i++){
                if(i==0){
                    str = str+","+page.getRows().get(i).getUserId();
                }
            }
            query = QueryFilter.build();
            query.setParams(map);
            query.setPageBean(queryFilter.getPageBean());
            query.addFilter("id",str,QueryOP.IN);
        }else{
            return new PageList<>();
        }
        //根据人员id获取管家列表
        return this.ucUserService.query(query);
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
