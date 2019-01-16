package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryAfterService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 网格基础信息历史表-变化后快照
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:23:28
 */
@RestController
@RequestMapping(value = {"/api/grid-basic-info-history-after"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridBasicInfoHistoryAfterController"})
public class GridBasicInfoHistoryAfterController extends BaseController {
    @Resource
    GridBasicInfoHistoryAfterService gridBasicInfoHistoryAfterService;

//    @PostMapping({"/list"})
//    @ApiOperation(value = "网格基础信息历史表-变化后快照数据列表}", httpMethod = "POST", notes = "获取网格基础信息历史表-变化后快照列表")
//    public PageList<GridBasicInfoHistoryAfter> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
//        return this.gridBasicInfoHistoryAfterService.query(queryFilter);
//    }
//
//    @GetMapping({"/get/{id}"})
//    @ApiOperation(value = "网格基础信息历史表-变化后快照数据列表", httpMethod = "GET", notes = "获取单个网格基础信息历史表-变化后快照记录")
//    public GridBasicInfoHistoryAfter get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
//        return this.gridBasicInfoHistoryAfterService.get(id);
//    }

}
