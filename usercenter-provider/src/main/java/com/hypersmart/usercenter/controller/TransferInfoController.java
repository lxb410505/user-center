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

import com.hypersmart.usercenter.model.TransferInfo;
import com.hypersmart.usercenter.service.TransferInfoService;

import java.util.Date;

/**
 * 过户信息
 *
 * @author liyong
 * @email xx
 * @date 2019-08-27 20:21:29
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/transferInfo"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"transferInfoController"})
public class TransferInfoController extends BaseController {
    @Resource
        TransferInfoService transferInfoService;

    @PostMapping({"/list"})
    @ApiOperation(value = "过户信息数据列表}", httpMethod = "POST", notes = "获取过户信息列表")
    public PageList<TransferInfo> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.transferInfoService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "过户信息数据列表", httpMethod = "GET", notes = "获取单个过户信息记录")
    public TransferInfo get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.transferInfoService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增过户信息信息", httpMethod = "POST", notes = "保存过户信息")
    public CommonResult<String> post(@ApiParam(name = "transferInfo", value = "过户信息业务对象", required = true) @RequestBody TransferInfo model) {
        String msg = "添加过户信息成功";
        model.setCreateBy(current());
        model.setCreateTime(new Date());
        model.setUpdateBy(current());
        model.setUpdateTime(new Date());
        model.setIsDelete(0);
        this.transferInfoService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 过户信息 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 过户信息 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "transferInfo", value = "过户信息业务对象", required = true) @RequestBody TransferInfo model) {
        String msg = "更新过户信息成功";
        model.setUpdateBy(current());
        model.setUpdateTime(new Date());
        this.transferInfoService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 过户信息 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 过户信息 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "transferInfo", value = "过户信息业务对象", required = true) @RequestBody TransferInfo model) {
        String msg = "更新过户信息成功";
        this.transferInfoService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除过户信息记录", httpMethod = "DELETE", notes = "删除过户信息记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.transferInfoService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除过户信息记录", httpMethod = "DELETE", notes = "批量删除过户信息记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.transferInfoService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
