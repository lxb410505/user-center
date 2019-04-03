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

import com.hypersmart.usercenter.model.UcDemension;
import com.hypersmart.usercenter.service.UcDemensionService;

/**
 * 维度管理
 *
 * @author yang
 * @email yang17766@sina.com
 * @date 2019-03-13 13:49:47
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucDemension"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucDemensionController"})
public class UcDemensionController extends BaseController {
    @Resource
        UcDemensionService ucDemensionService;

    @PostMapping({"/list"})
    @ApiOperation(value = "维度管理数据列表}", httpMethod = "POST", notes = "获取维度管理列表")
    public PageList<UcDemension> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucDemensionService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "维度管理数据列表", httpMethod = "GET", notes = "获取单个维度管理记录")
    public UcDemension get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucDemensionService.get(id);
    }


//    @PostMapping({"add"})
//    @ApiOperation(value = "新增维度管理信息", httpMethod = "POST", notes = "保存维度管理")
//    public CommonResult<String> post(@ApiParam(name = "ucDemension", value = "维度管理业务对象", required = true) @RequestBody UcDemension model) {
//        String msg = "添加维度管理成功";
//        this.ucDemensionService.insert(model);
//        return new CommonResult(msg);
//    }
//
//    @PutMapping({"update"})
//    @ApiOperation(value = "更新指定id的 维度管理 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 维度管理 信息（更新全部信息）")
//    public CommonResult<String> put(@ApiParam(name = "ucDemension", value = "维度管理业务对象", required = true) @RequestBody UcDemension model) {
//        String msg = "更新维度管理成功";
//        this.ucDemensionService.update(model);
//        return new CommonResult(msg);
//    }
//
//    @PatchMapping({"update"})
//    @ApiOperation(value = "更新指定id的 维度管理 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 维度管理 信息（更新部分信息）")
//    public CommonResult<String> patch(@ApiParam(name = "ucDemension", value = "维度管理业务对象", required = true) @RequestBody UcDemension model) {
//        String msg = "更新维度管理成功";
//        this.ucDemensionService.updateSelective(model);
//        return new CommonResult(msg);
//    }
//
//    @DeleteMapping({"remove/{id}"})
//    @ApiOperation(value = "删除维度管理记录", httpMethod = "DELETE", notes = "删除维度管理记录")
//    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
//        this.ucDemensionService.delete(id);
//        return new CommonResult(true, "删除成功");
//    }
//
//    @DeleteMapping({"/remove"})
//    @ApiOperation(value = "批量删除维度管理记录", httpMethod = "DELETE", notes = "批量删除维度管理记录")
//    public CommonResult<String> removes(
//            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
//        String[] aryIds = ids.split(",");
//        this.ucDemensionService.delete(aryIds);
//        return new CommonResult(true, "批量删除成功");
//    }
}
