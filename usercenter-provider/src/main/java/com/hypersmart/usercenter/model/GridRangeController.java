package com.hypersmart.usercenter.model;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.service.GridRangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 网格覆盖范围表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-17 12:48:37
 */
@RestController
@RequestMapping(value = {"/api/gridRange/v1/gridRange"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridRangeController"})
public class GridRangeController extends BaseController {
    @Resource
    GridRangeService gridRangeService;

    @PostMapping({"/list"})
    @ApiOperation(value = "网格覆盖范围表数据列表}", httpMethod = "POST", notes = "获取网格覆盖范围表列表")
    public PageList<GridRange> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.gridRangeService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "网格覆盖范围表数据列表", httpMethod = "GET", notes = "获取单个网格覆盖范围表记录")
    public GridRange get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.gridRangeService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增网格覆盖范围表信息", httpMethod = "POST", notes = "保存网格覆盖范围表")
    public CommonResult<String> post(@ApiParam(name = "gridRange", value = "网格覆盖范围表业务对象", required = true) @RequestBody GridRange model) {
        String msg = "添加网格覆盖范围表成功";
        this.gridRangeService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 网格覆盖范围表 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 网格覆盖范围表 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "gridRange", value = "网格覆盖范围表业务对象", required = true) @RequestBody GridRange model) {
        String msg = "更新网格覆盖范围表成功";
        this.gridRangeService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 网格覆盖范围表 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 网格覆盖范围表 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "gridRange", value = "网格覆盖范围表业务对象", required = true) @RequestBody GridRange model) {
        String msg = "更新网格覆盖范围表成功";
        this.gridRangeService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除网格覆盖范围表记录", httpMethod = "DELETE", notes = "删除网格覆盖范围表记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.gridRangeService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除网格覆盖范围表记录", httpMethod = "DELETE", notes = "批量删除网格覆盖范围表记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.gridRangeService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
