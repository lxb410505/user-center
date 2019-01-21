package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.bo.GridRangeBO;
import com.hypersmart.usercenter.model.GridRange;
import com.hypersmart.usercenter.service.GridRangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 网格覆盖范围表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-17 12:48:37
 */
@RestController
@RequestMapping(value = {"/grid-api/grid-basic-info"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridRangeController"})
public class GridRangeController extends BaseController {
    @Resource
    GridRangeService gridRangeService;

    @PostMapping({"/getAllRange"})
    @ApiOperation(value = "获取所有已经绑定网格的楼栋}", httpMethod = "POST", notes = "获取所有已经绑定网格的楼栋")
    public List<String> getAllRange(@ApiParam(name = "gridRangeBO", value = "查询对象") @RequestBody GridRangeBO gridRangeBO) {
        return this.gridRangeService.getAllRange(gridRangeBO);
    }
}
