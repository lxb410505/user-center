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

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.UcOrgService;

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

    @PostMapping({"/list"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "POST", notes = "获取组织架构列表")
    public PageList<UcOrg> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgService.query(queryFilter);
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
