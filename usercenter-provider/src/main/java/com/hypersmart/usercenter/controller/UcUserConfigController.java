package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.model.UcUserConfig;
import com.hypersmart.usercenter.service.UcUserConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 
 *
 * @author Magellan
 * @email 121935403@qq.com
 * @date 2019-04-30 15:13:12
 */
@RestController
@RequestMapping(value = {"/api/UcUserConfig/v1/ucUserConfig"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucUserConfigController"})
public class UcUserConfigController extends BaseController {
    @Resource
    UcUserConfigService ucUserConfigService;

    @PostMapping({"/list"})
    @ApiOperation(value = "数据列表}", httpMethod = "POST", notes = "获取列表")
    public PageList<UcUserConfig> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucUserConfigService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "数据列表", httpMethod = "GET", notes = "获取单个记录")
    public UcUserConfig get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucUserConfigService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增信息", httpMethod = "POST", notes = "保存")
    public CommonResult<String> post(@ApiParam(name = "ucUserConfig", value = "业务对象", required = true) @RequestBody UcUserConfig model) {
        String msg = "添加成功";
        model.setCreateTime(new Date());
        model.setIsDele("1");
        model.setVersion(1);
        this.ucUserConfigService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的  信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "ucUserConfig", value = "业务对象", required = true) @RequestBody UcUserConfig model) {
        String msg = "更新成功";
        model.setUpdateTime(new Date());
        this.ucUserConfigService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的  信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "ucUserConfig", value = "业务对象", required = true) @RequestBody UcUserConfig model) {
        String msg = "更新成功";
        model.setUpdateTime(new Date());
        this.ucUserConfigService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除记录", httpMethod = "DELETE", notes = "删除记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.ucUserConfigService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除记录", httpMethod = "DELETE", notes = "批量删除记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.ucUserConfigService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
