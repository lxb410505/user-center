package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.usercenter.model.TgeSignificantQuality;
import com.hypersmart.usercenter.service.TgeSignificantQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 重大事件质量
 *
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:07:53
 */
@RestController
@RequestMapping(value = {"/api/skeleton/v1/tgeSignificantQuality"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"tgeSignificantQualityController"})
public class TgeSignificantQualityController extends BaseController {
    @Resource
    TgeSignificantQualityService tgeSignificantQualityService;

    @PostMapping({"/list"})
    @ApiOperation(value = "重大事件质量数据列表}", httpMethod = "POST", notes = "获取重大事件质量列表")
    public PageList<TgeSignificantQuality> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {

        return tgeSignificantQualityService.queryList(queryFilter);
    }

    @RequestMapping(value ="importData/{month}", method = RequestMethod.POST )
    public CommonResult<String> importData(MultipartFile file, @PathVariable("month") String month)  {
        if("undefined".equals(month)){
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM");
            month= dt.format(LocalDate.now());
        }
//        Object orgId="0135ddc32d43403fb0d375a643274c05";
        return this.tgeSignificantQualityService.importData(file,month);
    }

    @GetMapping("checkExists")
    public CommonResult<String> checkExists(String month){
        Object orgId= ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);
        if("undefined".equals(month)){
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM");
            month= dt.format(LocalDate.now());
        }
        boolean b = this.tgeSignificantQualityService.checkExists(month, orgId);

        return new CommonResult<>(b,b?month:"","");
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "重大事件质量数据列表", httpMethod = "GET", notes = "获取单个重大事件质量记录")
    public TgeSignificantQuality get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.tgeSignificantQualityService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增重大事件质量信息", httpMethod = "POST", notes = "保存重大事件质量")
    public CommonResult<String> post(@ApiParam(name = "tgeSignificantQuality", value = "重大事件质量业务对象", required = true) @RequestBody TgeSignificantQuality model) {
        String msg = "添加重大事件质量成功";
        this.tgeSignificantQualityService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 重大事件质量 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 重大事件质量 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "tgeSignificantQuality", value = "重大事件质量业务对象", required = true) @RequestBody TgeSignificantQuality model) {
        String msg = "更新重大事件质量成功";
        this.tgeSignificantQualityService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 重大事件质量 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 重大事件质量 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "tgeSignificantQuality", value = "重大事件质量业务对象", required = true) @RequestBody TgeSignificantQuality model) {
        String msg = "更新重大事件质量成功";
        this.tgeSignificantQualityService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除重大事件质量记录", httpMethod = "DELETE", notes = "删除重大事件质量记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.tgeSignificantQualityService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除重大事件质量记录", httpMethod = "DELETE", notes = "批量删除重大事件质量记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.tgeSignificantQualityService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
