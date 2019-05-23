package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryField;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.model.QualityCheck;
import com.hypersmart.usercenter.service.QualityCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Magellan
 * @email Magellan
 * @date 2019-05-21 16:18:32
 */
@RestController
@RequestMapping(value = {"/api/QualityCheck/v1/qualityCheck"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"qualityCheckController"})
public class QualityCheckController extends BaseController {
    @Resource
    QualityCheckService qualityCheckService;

    @PostMapping({"/list"})
    @ApiOperation(value = "数据列表}", httpMethod = "POST", notes = "获取列表")
    public PageList<QualityCheck> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
       /* Map<String, Object> params = queryFilter.getParams();
        Object effectiveTime = params.get("effective_time");
        String effectiveTimeChange = effectiveTime.toString() + "-01";
        params.put("effective_time",effectiveTimeChange);
        queryFilter.setParams(params);
        List<QueryField> querys = queryFilter.getQuerys();*/

        PageList<QualityCheck> query = this.qualityCheckService.query(queryFilter);
        return query;
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "数据列表", httpMethod = "GET", notes = "获取单个记录")
    public QualityCheck get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.qualityCheckService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增信息", httpMethod = "POST", notes = "保存")
    public CommonResult<String> post(@ApiParam(name = "qualityCheck", value = "业务对象", required = true) @RequestBody QualityCheck model) {
        String msg = "添加成功";
        this.qualityCheckService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的  信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "qualityCheck", value = "业务对象", required = true) @RequestBody QualityCheck model) {
        String msg = "更新成功";
        this.qualityCheckService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的  信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "qualityCheck", value = "业务对象", required = true) @RequestBody QualityCheck model) {
        String msg = "更新成功";
        this.qualityCheckService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除记录", httpMethod = "DELETE", notes = "删除记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.qualityCheckService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除记录", httpMethod = "DELETE", notes = "批量删除记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.qualityCheckService.delete(aryIds);
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
        return qualityCheckService.CheckHasExist(date);
    }

    /**
     * 通过Excle导入科目余额设置
     *
     * @param file Excel文件
     * @return
     */
    @RequestMapping(value = "/importData/{date}", method = RequestMethod.POST)
    public CommonResult<String> importData(MultipartFile file, @PathVariable("date") String date) throws Exception {

        CommonResult<String> result = this.qualityCheckService.importData(file, date);
        return result;
    }
}
