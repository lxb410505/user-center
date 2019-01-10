package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import com.hypersmart.usercenter.model.SysLogs;
import com.hypersmart.usercenter.service.SysLogsService;

@RestController
@RequestMapping(value = {"/sysLogs/v1/sysLogs"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"sysLogsController"})
public class SysLogsController extends BaseController {
    @Resource
    SysLogsService sysLogsManager;

    @PostMapping({"/list"})
    @ApiOperation(value = "系统操作日志数据列表", httpMethod = "POST", notes = "获取系统操作日志列表")
    public PageList<SysLogs> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.sysLogsManager.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "系统操作日志数据列表", httpMethod = "GET", notes = "获取单个系统操作日志记录")
    public SysLogs get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.sysLogsManager.get(id);
    }

    @PostMapping({"save"})
    @ApiOperation(value = "保存 系统操作日志 信息", httpMethod = "POST", notes = "保存 系统操作日志")
    public CommonResult<String> save(
            @ApiParam(name = "sysLogs", value = "系统操作日志业务对象", required = true) @RequestBody SysLogs sysLogs) {
        String msg = "添加系统操作日志成功";
        if (StringUtil.isEmpty(sysLogs.getId())) {
            this.sysLogsManager.insert(sysLogs);
        } else {
            this.sysLogsManager.update(sysLogs);
            msg = "更新系统操作日志成功";
        }
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除 系统操作日志 记录", httpMethod = "DELETE", notes = "删除 系统操作日志 记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.sysLogsManager.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除 系统操作日志 记录", httpMethod = "DELETE", notes = "批量删除 系统操作日志 记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.sysLogsManager.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
