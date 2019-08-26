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

import com.hypersmart.usercenter.model.Parking;
import com.hypersmart.usercenter.service.ParkingService;

/**
 * 【基础信息】停车位
 *
 * @author zcf
 * @email 1490318946@qq.com
 * @date 2019-08-21 16:19:42
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/parking"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"parkingController"})
public class ParkingController extends BaseController {
    @Resource
    ParkingService parkingService;

    @PostMapping({"/list"})
    @ApiOperation(value = "【基础信息】停车位数据列表}", httpMethod = "POST", notes = "获取【基础信息】停车位列表")
    public PageList<Parking> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.parkingService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "【基础信息】停车位数据列表", httpMethod = "GET", notes = "获取单个【基础信息】停车位记录")
    public Parking get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.parkingService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增【基础信息】停车位信息", httpMethod = "POST", notes = "保存【基础信息】停车位")
    public CommonResult<String> post(@ApiParam(name = "parking", value = "【基础信息】停车位业务对象", required = true) @RequestBody Parking model) {

        CommonResult<String> result = parkingService.addParking(model);
        return result;
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 【基础信息】停车位 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 【基础信息】停车位 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "parking", value = "【基础信息】停车位业务对象", required = true) @RequestBody Parking model) {
        String msg = "更新【基础信息】停车位成功";
        this.parkingService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 【基础信息】停车位 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 【基础信息】停车位 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "parking", value = "【基础信息】停车位业务对象", required = true) @RequestBody Parking model) {
        CommonResult<String> result = parkingService.updateParking(model);
        return result;
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除【基础信息】停车位记录", httpMethod = "DELETE", notes = "删除【基础信息】停车位记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.parkingService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除【基础信息】停车位记录", httpMethod = "DELETE", notes = "批量删除【基础信息】停车位记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.parkingService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
