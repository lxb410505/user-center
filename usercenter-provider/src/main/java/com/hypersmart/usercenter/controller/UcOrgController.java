package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcOrgUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.UcOrgService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 组织架构
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucOrg"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucOrgController"})
public class UcOrgController extends BaseController {
    @Resource
        UcOrgService ucOrgService;

    @Resource
    UcOrgUserService ucOrgUserService;

    @PostMapping({"/list"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "POST", notes = "获取组织架构列表")
    public List<UcOrg> list( @RequestBody  Map<String,String> map) {
        QueryFilter queryFilter =QueryFilter.build();
        IUser user =  ContextUtil.getCurrentUser();
        List<UcOrgUser> list = ucOrgUserService.getUserOrg("1012");
        List<UcOrg> returnList = ucOrgService.getOrg(list);
        List<UcOrg> set = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for(UcOrg ucOrg :returnList){
            List<UcOrg> orgs = ucOrgService.getChildrenOrg(ucOrg);
            for(UcOrg org :orgs){
                if(!idList.contains(org.getId())){
                    idList.add(org.getId());
                    set.add(org);
                }
            }
        }
        String str = "";
        for(int i=0;i<set.size();i++){
            if(i==0){
                str = set.get(i).getId();
            }else{
                str = str + "," + set.get(i).getId();
            }
        }
        queryFilter.addFilter("id",str, QueryOP.IN,FieldRelation.AND);
        if(null != map && null != map.get("parentId")) {
            if("".equals(map.get("parentId"))){
                map.put("parentId","0");
            }
            queryFilter.addFilter("parentId",map.get("parentId"), QueryOP.EQUAL,FieldRelation.AND);
            return this.ucOrgService.query(queryFilter).getRows();
        }
        return new ArrayList<>();
    }

    @PostMapping({"/getByList"})
    @ApiOperation(value = "根据组织id集合获取组织信息}", httpMethod = "POST", notes = "根据组织id集合获取组织信息")
    public List<UcOrg> getByList( @RequestBody  Map<String,List<String>> map) {
        QueryFilter queryFilter =QueryFilter.build();

        if(null != map && null != map.get("list")) {
            String ids = "";
            for(int i=0;i<map.get("list").size();i++){
                if(i==0){
                    ids = map.get("list").get(i);
                }else{
                    ids = ids + "," + map.get("list").get(i);
                }
            }
            queryFilter.addFilter("id",ids, QueryOP.IN,FieldRelation.AND);
            return this.ucOrgService.query(queryFilter).getRows();
        }
        return new ArrayList<>();
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "组织架构数据列表", httpMethod = "GET", notes = "获取单个组织架构记录")
    public UcOrg get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgService.get(id);
    }


//    @PostMapping({"add"})
//    @ApiOperation(value = "新增组织架构信息", httpMethod = "POST", notes = "保存组织架构")
//    public CommonResult<String> post(@ApiParam(name = "ucOrg", value = "组织架构业务对象", required = true) @RequestBody UcOrg model) {
//        String msg = "添加组织架构成功";
//        this.ucOrgService.insert(model);
//        return new CommonResult(msg);
//    }
//
//    @PutMapping({"update"})
//    @ApiOperation(value = "更新指定id的 组织架构 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 组织架构 信息（更新全部信息）")
//    public CommonResult<String> put(@ApiParam(name = "ucOrg", value = "组织架构业务对象", required = true) @RequestBody UcOrg model) {
//        String msg = "更新组织架构成功";
//        this.ucOrgService.update(model);
//        return new CommonResult(msg);
//    }
//
//    @PatchMapping({"update"})
//    @ApiOperation(value = "更新指定id的 组织架构 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 组织架构 信息（更新部分信息）")
//    public CommonResult<String> patch(@ApiParam(name = "ucOrg", value = "组织架构业务对象", required = true) @RequestBody UcOrg model) {
//        String msg = "更新组织架构成功";
//        this.ucOrgService.updateSelective(model);
//        return new CommonResult(msg);
//    }
//
//    @DeleteMapping({"remove/{id}"})
//    @ApiOperation(value = "删除组织架构记录", httpMethod = "DELETE", notes = "删除组织架构记录")
//    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
//        this.ucOrgService.delete(id);
//        return new CommonResult(true, "删除成功");
//    }
//
//    @DeleteMapping({"/remove"})
//    @ApiOperation(value = "批量删除组织架构记录", httpMethod = "DELETE", notes = "批量删除组织架构记录")
//    public CommonResult<String> removes(
//            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
//        String[] aryIds = ids.split(",");
//        this.ucOrgService.delete(aryIds);
//        return new CommonResult(true, "批量删除成功");
//    }
}
