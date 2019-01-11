package com.hypersmart.usercenter.controller;

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

import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.UcUserService;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucUser"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucUserController"})
public class UcUserController extends BaseController {
    @Resource
        UcUserService ucUserService;

    @PostMapping({"/list"})
    @ApiOperation(value = "用户管理数据列表}", httpMethod = "POST", notes = "获取用户管理列表")
    public PageList<UcUser> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucUserService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "用户管理数据列表", httpMethod = "GET", notes = "获取单个用户管理记录")
    public UcUser get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucUserService.get(id);
    }


//    @PostMapping({"add"})
//    @ApiOperation(value = "新增用户管理信息", httpMethod = "POST", notes = "保存用户管理")
//    public CommonResult<String> post(@ApiParam(name = "ucUser", value = "用户管理业务对象", required = true) @RequestBody UcUser model) {
//        String msg = "添加用户管理成功";
//        this.ucUserService.insert(model);
//        return new CommonResult(msg);
//    }
//
//    @PutMapping({"update"})
//    @ApiOperation(value = "更新指定id的 用户管理 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 用户管理 信息（更新全部信息）")
//    public CommonResult<String> put(@ApiParam(name = "ucUser", value = "用户管理业务对象", required = true) @RequestBody UcUser model) {
//        String msg = "更新用户管理成功";
//        this.ucUserService.update(model);
//        return new CommonResult(msg);
//    }
//
//    @PatchMapping({"update"})
//    @ApiOperation(value = "更新指定id的 用户管理 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 用户管理 信息（更新部分信息）")
//    public CommonResult<String> patch(@ApiParam(name = "ucUser", value = "用户管理业务对象", required = true) @RequestBody UcUser model) {
//        String msg = "更新用户管理成功";
//        this.ucUserService.updateSelective(model);
//        return new CommonResult(msg);
//    }
//
//    @DeleteMapping({"remove/{id}"})
//    @ApiOperation(value = "删除用户管理记录", httpMethod = "DELETE", notes = "删除用户管理记录")
//    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
//        this.ucUserService.delete(id);
//        return new CommonResult(true, "删除成功");
//    }
//
//    @DeleteMapping({"/remove"})
//    @ApiOperation(value = "批量删除用户管理记录", httpMethod = "DELETE", notes = "批量删除用户管理记录")
//    public CommonResult<String> removes(
//            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
//        String[] aryIds = ids.split(",");
//        this.ucUserService.delete(aryIds);
//        return new CommonResult(true, "批量删除成功");
//    }
}
