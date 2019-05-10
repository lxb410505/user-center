package com.hypersmart.usercenter.controller;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.usercenter.service.UcOrgPostService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @PostMapping({"/queryList"})
    @ApiOperation(value = "用户组织关系数据列表}", httpMethod = "POST", notes = "获取用户组织关系列表")
    public PageList<Map<String, Object>> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgUserService.quertListFive(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "用户组织关系数据列表", httpMethod = "GET", notes = "获取单个用户组织关系记录")
    public UcOrgUser get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgUserService.get(id);
    }

    @PostMapping({"/orgList"})
    @ApiOperation(value = "获取用户地块数据列表}", httpMethod = "POST", notes = "获取用户地块数据列表")
    public List<Map<String, Object>> getOrgByCondition(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgUserService.getOrgByCondition(queryFilter);
    }

    @PostMapping({"/getDivide"})
    @ApiOperation(value = "获取用户地块数据列表}", httpMethod = "POST", notes = "获取用户地块数据列表")
    public PageList<UcOrg> getDivide(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
      return   ucOrgUserService.findDivide(queryFilter);
    }

    @PostMapping({"/getHouse"})
    @ApiOperation(value = "获取用户地块数据列表}", httpMethod = "POST", notes = "获取用户地块数据列表")
    public List<String> getHouse(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return   ucOrgUserService.findHous(queryFilter);
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
