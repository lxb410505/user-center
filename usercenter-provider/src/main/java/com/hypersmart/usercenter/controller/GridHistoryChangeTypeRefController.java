package com.hypersmart.usercenter.controller;

import com.hypersmart.base.aop.norepeat.NoRepeatSubmit;
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

import com.hypersmart.usercenter.model.GridHistoryChangeTypeRef;
import com.hypersmart.usercenter.service.GridHistoryChangeTypeRefService;

/**
 * 网格历史网格内容变更类型关系表
 *
 * @author jiangxiaoxuan
 * @email 1111111@163.com
 * @date 2019-01-17 16:47:11
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/gridHistoryChangeTypeRef"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridHistoryChangeTypeRefController"})
public class GridHistoryChangeTypeRefController extends BaseController {
    @Resource
        GridHistoryChangeTypeRefService gridHistoryChangeTypeRefService;

    @PostMapping({"/list"})
    @ApiOperation(value = "网格历史网格内容变更类型关系表数据列表}", httpMethod = "POST", notes = "获取网格历史网格内容变更类型关系表列表")
    public PageList<GridHistoryChangeTypeRef> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.gridHistoryChangeTypeRefService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "网格历史网格内容变更类型关系表数据列表", httpMethod = "GET", notes = "获取单个网格历史网格内容变更类型关系表记录")
    public GridHistoryChangeTypeRef get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.gridHistoryChangeTypeRefService.get(id);
    }


    @PostMapping({"add"})
    @NoRepeatSubmit
    @ApiOperation(value = "新增网格历史网格内容变更类型关系表信息", httpMethod = "POST", notes = "保存网格历史网格内容变更类型关系表")
    public CommonResult<String> post(@ApiParam(name = "gridHistoryChangeTypeRef", value = "网格历史网格内容变更类型关系表业务对象", required = true) @RequestBody GridHistoryChangeTypeRef model) {
        String msg = "添加网格历史网格内容变更类型关系表成功";
        this.gridHistoryChangeTypeRefService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @NoRepeatSubmit
    @ApiOperation(value = "更新指定id的 网格历史网格内容变更类型关系表 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 网格历史网格内容变更类型关系表 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "gridHistoryChangeTypeRef", value = "网格历史网格内容变更类型关系表业务对象", required = true) @RequestBody GridHistoryChangeTypeRef model) {
        String msg = "更新网格历史网格内容变更类型关系表成功";
        this.gridHistoryChangeTypeRefService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @NoRepeatSubmit
    @ApiOperation(value = "更新指定id的 网格历史网格内容变更类型关系表 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 网格历史网格内容变更类型关系表 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "gridHistoryChangeTypeRef", value = "网格历史网格内容变更类型关系表业务对象", required = true) @RequestBody GridHistoryChangeTypeRef model) {
        String msg = "更新网格历史网格内容变更类型关系表成功";
        this.gridHistoryChangeTypeRefService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除网格历史网格内容变更类型关系表记录", httpMethod = "DELETE", notes = "删除网格历史网格内容变更类型关系表记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.gridHistoryChangeTypeRefService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除网格历史网格内容变更类型关系表记录", httpMethod = "DELETE", notes = "批量删除网格历史网格内容变更类型关系表记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.gridHistoryChangeTypeRefService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
