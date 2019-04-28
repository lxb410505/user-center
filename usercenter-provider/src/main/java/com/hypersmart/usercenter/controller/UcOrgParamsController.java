package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.usercenter.service.UcOrgParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



/**
 * 组织参数
 *
 * @author zhoukai
 * @email 70*******@qq.com
 * @date 2019-04-28 10:15:44
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucOrgParams"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucOrgParamsController"})
public class UcOrgParamsController extends BaseController {
    @Resource
    UcOrgParamsService ucOrgParamsService;

    @PostMapping({"/list"})
    @ApiOperation(value = "组织参数数据列表}", httpMethod = "POST", notes = "获取组织参数列表")
    public PageList<UcOrgParams> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgParamsService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "组织参数数据列表", httpMethod = "GET", notes = "获取单个组织参数记录")
    public UcOrgParams get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgParamsService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增组织参数信息", httpMethod = "POST", notes = "保存组织参数")
    public CommonResult<String> post(@ApiParam(name = "ucOrgParams", value = "组织参数业务对象", required = true) @RequestBody UcOrgParams model) {
        String msg = "添加组织参数成功";
        this.ucOrgParamsService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 组织参数 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 组织参数 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "ucOrgParams", value = "组织参数业务对象", required = true) @RequestBody UcOrgParams model) {
        String msg = "更新组织参数成功";
        this.ucOrgParamsService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 组织参数 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 组织参数 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "ucOrgParams", value = "组织参数业务对象", required = true) @RequestBody UcOrgParams model) {
        String msg = "更新组织参数成功";
        this.ucOrgParamsService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除组织参数记录", httpMethod = "DELETE", notes = "删除组织参数记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.ucOrgParamsService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除组织参数记录", httpMethod = "DELETE", notes = "批量删除组织参数记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.ucOrgParamsService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
