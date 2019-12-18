package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.dto.UcOrgDTO;
import com.hypersmart.usercenter.model.UserIdGrade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.PlatformMdmGroupMapping;
import com.hypersmart.usercenter.service.PlatformMdmGroupMappingService;

import java.util.List;

/**
 * 平台组团映射表
 *
 * @author lily
 * @email lily
 * @date 2019-12-18 12:51:08
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/orgMapping"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"platformMdmGroupMappingController"})
public class PlatformMdmGroupMappingController extends BaseController {
    @Resource
        PlatformMdmGroupMappingService platformMdmGroupMappingService;

    //根据组织级别查询组织信息
    @PostMapping({"/queryByGrade"})
    public List<UcOrgDTO> queryByGrade(@RequestBody UserIdGrade userIdGrade) {
        String userId=userIdGrade.getUserId();
        String grade=userIdGrade.getGrade();
        return platformMdmGroupMappingService.queryByGrade(userId,grade);
    }

    @PostMapping({"/list"})
    @ApiOperation(value = "平台组团映射表数据列表}", httpMethod = "POST", notes = "获取平台组团映射表列表")
    public PageList<PlatformMdmGroupMapping> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.platformMdmGroupMappingService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "平台组团映射表数据列表", httpMethod = "GET", notes = "获取单个平台组团映射表记录")
    public PlatformMdmGroupMapping get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.platformMdmGroupMappingService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增平台组团映射表信息", httpMethod = "POST", notes = "保存平台组团映射表")
    public CommonResult<String> post(@ApiParam(name = "platformMdmGroupMapping", value = "平台组团映射表业务对象", required = true) @RequestBody PlatformMdmGroupMapping model) {
        String msg = "添加平台组团映射表成功";
        this.platformMdmGroupMappingService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 平台组团映射表 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 平台组团映射表 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "platformMdmGroupMapping", value = "平台组团映射表业务对象", required = true) @RequestBody PlatformMdmGroupMapping model) {
        String msg = "更新平台组团映射表成功";
        this.platformMdmGroupMappingService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 平台组团映射表 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 平台组团映射表 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "platformMdmGroupMapping", value = "平台组团映射表业务对象", required = true) @RequestBody PlatformMdmGroupMapping model) {
        String msg = "更新平台组团映射表成功";
        this.platformMdmGroupMappingService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除平台组团映射表记录", httpMethod = "DELETE", notes = "删除平台组团映射表记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.platformMdmGroupMappingService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除平台组团映射表记录", httpMethod = "DELETE", notes = "批量删除平台组团映射表记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.platformMdmGroupMappingService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
