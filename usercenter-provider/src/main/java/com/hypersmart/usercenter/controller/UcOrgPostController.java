package com.hypersmart.usercenter.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UcOrgPost;
import com.hypersmart.usercenter.service.UcOrgPostService;

/**
 * 部门岗位
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:43:26
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucOrgPost"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucOrgPostController"})
public class UcOrgPostController extends BaseController {
    @Resource
        UcOrgPostService ucOrgPostService;

    @PostMapping({"/list"})
    @ApiOperation(value = "部门岗位数据列表}", httpMethod = "POST", notes = "获取部门岗位列表")
    public PageList<UcOrgPost> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgPostService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "部门岗位数据列表", httpMethod = "GET", notes = "获取单个部门岗位记录")
    public UcOrgPost get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgPostService.get(id);
    }
    @RequestMapping(value = { "/jobPage" }, method = {
            RequestMethod.POST })
    @ApiOperation(value = "获取职位列表}", httpMethod = "POST", notes = "获取获取职位列表")
    PageList<ObjectNode> jobPage(@RequestBody QueryFilter filter){
        return ucOrgPostService.getJobPage(filter);
    }

//    @PostMapping({"add"})
//    @ApiOperation(value = "新增部门岗位信息", httpMethod = "POST", notes = "保存部门岗位")
//    public CommonResult<String> post(@ApiParam(name = "ucOrgPost", value = "部门岗位业务对象", required = true) @RequestBody UcOrgPost model) {
//        String msg = "添加部门岗位成功";
//        this.ucOrgPostService.insert(model);
//        return new CommonResult(msg);
//    }
//
//    @PutMapping({"update"})
//    @ApiOperation(value = "更新指定id的 部门岗位 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 部门岗位 信息（更新全部信息）")
//    public CommonResult<String> put(@ApiParam(name = "ucOrgPost", value = "部门岗位业务对象", required = true) @RequestBody UcOrgPost model) {
//        String msg = "更新部门岗位成功";
//        this.ucOrgPostService.update(model);
//        return new CommonResult(msg);
//    }
//
//    @PatchMapping({"update"})
//    @ApiOperation(value = "更新指定id的 部门岗位 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 部门岗位 信息（更新部分信息）")
//    public CommonResult<String> patch(@ApiParam(name = "ucOrgPost", value = "部门岗位业务对象", required = true) @RequestBody UcOrgPost model) {
//        String msg = "更新部门岗位成功";
//        this.ucOrgPostService.updateSelective(model);
//        return new CommonResult(msg);
//    }
//
//    @DeleteMapping({"remove/{id}"})
//    @ApiOperation(value = "删除部门岗位记录", httpMethod = "DELETE", notes = "删除部门岗位记录")
//    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
//        this.ucOrgPostService.delete(id);
//        return new CommonResult(true, "删除成功");
//    }
//
//    @DeleteMapping({"/remove"})
//    @ApiOperation(value = "批量删除部门岗位记录", httpMethod = "DELETE", notes = "批量删除部门岗位记录")
//    public CommonResult<String> removes(
//            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
//        String[] aryIds = ids.split(",");
//        this.ucOrgPostService.delete(aryIds);
//        return new CommonResult(true, "批量删除成功");
//    }
}
