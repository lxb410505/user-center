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

import com.hypersmart.usercenter.model.DecorateFiles;
import com.hypersmart.usercenter.service.DecorateFilesService;

/**
 * 客户装修资料归档管理
 *
 * @author ljia
 * @email 123@456.com
 * @date 2019-08-27 14:21:33
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/decorateFiles"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"decorateFilesController"})
public class DecorateFilesController extends BaseController {
    @Resource
        DecorateFilesService decorateFilesService;

    @PostMapping({"/list"})
    @ApiOperation(value = "客户装修资料归档管理数据列表}", httpMethod = "POST", notes = "获取客户装修资料归档管理列表")
    public PageList<DecorateFiles> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.decorateFilesService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "客户装修资料归档管理数据列表", httpMethod = "GET", notes = "获取单个客户装修资料归档管理记录")
    public DecorateFiles get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.decorateFilesService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增客户装修资料归档管理信息", httpMethod = "POST", notes = "保存客户装修资料归档管理")
    public CommonResult<String> post(@ApiParam(name = "decorateFiles", value = "客户装修资料归档管理业务对象", required = true) @RequestBody DecorateFiles model) {
        return this.decorateFilesService.add(model);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 客户装修资料归档管理 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 客户装修资料归档管理 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "decorateFiles", value = "客户装修资料归档管理业务对象", required = true) @RequestBody DecorateFiles model) {
        String msg = "更新客户装修资料归档管理成功";
        this.decorateFilesService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 客户装修资料归档管理 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 客户装修资料归档管理 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "decorateFiles", value = "客户装修资料归档管理业务对象", required = true) @RequestBody DecorateFiles model) {
        String msg = "更新客户装修资料归档管理成功";
        this.decorateFilesService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除客户装修资料归档管理记录", httpMethod = "DELETE", notes = "删除客户装修资料归档管理记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.decorateFilesService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除客户装修资料归档管理记录", httpMethod = "DELETE", notes = "批量删除客户装修资料归档管理记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.decorateFilesService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
    @GetMapping({"/listFiles/{decorateId}"})
    @ApiOperation(value = "用户归档数据列表}", httpMethod = "GET", notes = "用户归档数据列表")
    public PageList<DecorateFiles> listFiles( @PathVariable String decorateId) {
        return this.decorateFilesService.queryFilesList(decorateId);
    }
}
