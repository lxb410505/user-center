package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.TgeQualityCheck;
import com.hypersmart.usercenter.service.TgeQualityCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 
 *第三方品质检查
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:05:16
 */
@RestController
@RequestMapping(value = {"/api/skeleton/v1/tgeQualityCheck"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"tgeQualityCheckController"})
public class TgeQualityCheckController extends BaseController {
    @Resource
        TgeQualityCheckService tgeQualityCheckService;

    @PostMapping({"/list"})
    @ApiOperation(value = "数据列表}", httpMethod = "POST", notes = "获取列表")
    public PageList<TgeQualityCheck> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.tgeQualityCheckService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "数据列表", httpMethod = "GET", notes = "获取单个记录")
    public TgeQualityCheck get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.tgeQualityCheckService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增信息", httpMethod = "POST", notes = "保存")
    public CommonResult<String> post(@ApiParam(name = "tgeQualityCheck", value = "业务对象", required = true) @RequestBody TgeQualityCheck model) {
        String msg = "添加成功";
        this.tgeQualityCheckService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的  信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "tgeQualityCheck", value = "业务对象", required = true) @RequestBody TgeQualityCheck model) {
        String msg = "更新成功";
        this.tgeQualityCheckService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的  信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "tgeQualityCheck", value = "业务对象", required = true) @RequestBody TgeQualityCheck model) {
        String msg = "更新成功";
        this.tgeQualityCheckService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除记录", httpMethod = "DELETE", notes = "删除记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.tgeQualityCheckService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除记录", httpMethod = "DELETE", notes = "批量删除记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.tgeQualityCheckService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }

    /**
     * 查询当月是否已有数据；
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check/data/{date}", method = RequestMethod.GET)
    public CommonResult CheckHasExist(@PathVariable("date") String date) throws Exception {
        return tgeQualityCheckService.CheckHasExist(date);
    }

    /**
     * 通过Excle导入科目余额设置
     *
     * @param file Excel文件
     * @return
     */
    @RequestMapping(value = "/importData/{date}", method = RequestMethod.POST)
    public CommonResult<String> importData(MultipartFile file, @PathVariable("date") String date) throws Exception {

        CommonResult<String> result = this.tgeQualityCheckService.importData(file, date);
        return result;
    }

}
