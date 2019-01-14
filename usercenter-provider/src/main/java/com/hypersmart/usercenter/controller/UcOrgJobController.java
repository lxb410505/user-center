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

import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.usercenter.service.UcOrgJobService;

/**
 * 职务定义
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:44:42
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucOrgJob"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucOrgJobController"})
public class UcOrgJobController extends BaseController {
    @Resource
        UcOrgJobService ucOrgJobService;

    @PostMapping({"/list"})
    @ApiOperation(value = "职务定义数据列表}", httpMethod = "POST", notes = "获取职务定义列表")
    public PageList<UcOrgJob> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgJobService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "职务定义数据列表", httpMethod = "GET", notes = "获取单个职务定义记录")
    public UcOrgJob get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgJobService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增职务定义信息", httpMethod = "POST", notes = "保存职务定义")
    public CommonResult<String> post(@ApiParam(name = "ucOrgJob", value = "职务定义业务对象", required = true) @RequestBody UcOrgJob model) {
        String msg = "添加职务定义成功";
        this.ucOrgJobService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 职务定义 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 职务定义 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "ucOrgJob", value = "职务定义业务对象", required = true) @RequestBody UcOrgJob model) {
        String msg = "更新职务定义成功";
        this.ucOrgJobService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 职务定义 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 职务定义 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "ucOrgJob", value = "职务定义业务对象", required = true) @RequestBody UcOrgJob model) {
        String msg = "更新职务定义成功";
        this.ucOrgJobService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除职务定义记录", httpMethod = "DELETE", notes = "删除职务定义记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.ucOrgJobService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除职务定义记录", httpMethod = "DELETE", notes = "批量删除职务定义记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.ucOrgJobService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
