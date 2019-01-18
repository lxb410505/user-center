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

import com.hypersmart.usercenter.model.HousekeeperHistory;
import com.hypersmart.usercenter.service.HousekeeperHistoryService;

/**
 * 管家历史记录表-管家快照
 *
 * @author jiangxiaoxuan
 * @email 111111@163.com
 * @date 2019-01-17 17:40:36
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/housekeeperHistory"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"housekeeperHistoryController"})
public class HousekeeperHistoryController extends BaseController {
    @Resource
        HousekeeperHistoryService housekeeperHistoryService;

    @PostMapping({"/list"})
    @ApiOperation(value = "管家历史记录表-管家快照数据列表}", httpMethod = "POST", notes = "获取管家历史记录表-管家快照列表")
    public PageList<HousekeeperHistory> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.housekeeperHistoryService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "管家历史记录表-管家快照数据列表", httpMethod = "GET", notes = "获取单个管家历史记录表-管家快照记录")
    public HousekeeperHistory get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.housekeeperHistoryService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增管家历史记录表-管家快照信息", httpMethod = "POST", notes = "保存管家历史记录表-管家快照")
    public CommonResult<String> post(@ApiParam(name = "housekeeperHistory", value = "管家历史记录表-管家快照业务对象", required = true) @RequestBody HousekeeperHistory model) {
        String msg = "添加管家历史记录表-管家快照成功";
        this.housekeeperHistoryService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 管家历史记录表-管家快照 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 管家历史记录表-管家快照 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "housekeeperHistory", value = "管家历史记录表-管家快照业务对象", required = true) @RequestBody HousekeeperHistory model) {
        String msg = "更新管家历史记录表-管家快照成功";
        this.housekeeperHistoryService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 管家历史记录表-管家快照 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 管家历史记录表-管家快照 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "housekeeperHistory", value = "管家历史记录表-管家快照业务对象", required = true) @RequestBody HousekeeperHistory model) {
        String msg = "更新管家历史记录表-管家快照成功";
        this.housekeeperHistoryService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除管家历史记录表-管家快照记录", httpMethod = "DELETE", notes = "删除管家历史记录表-管家快照记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.housekeeperHistoryService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除管家历史记录表-管家快照记录", httpMethod = "DELETE", notes = "批量删除管家历史记录表-管家快照记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.housekeeperHistoryService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
