package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.dto.GridBasicInfoHistoryDTO;
import com.hypersmart.usercenter.model.GridBasicInfoHistory;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 网格基础信息历史表-变化前快照
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:03:42
 */
@RestController
@RequestMapping(value = {"/api/grid-basic-info-history"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridBasicInfoHistoryController"})
public class GridBasicInfoHistoryController extends BaseController {
    @Resource
    GridBasicInfoHistoryService gridBasicInfoHistoryService;

//    @PostMapping({"/list"})
//    @ApiOperation(value = "网格基础信息历史表-变化前快照数据列表}", httpMethod = "POST", notes = "获取网格基础信息历史表-变化前快照列表")
//    public PageList<GridBasicInfoHistory> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
//        return this.gridBasicInfoHistoryService.query(queryFilter);
//    }
//
//    @GetMapping({"/get/{id}"})
//    @ApiOperation(value = "网格基础信息历史表-变化前快照数据列表", httpMethod = "GET", notes = "获取单个网格基础信息历史表-变化前快照记录")
//    public GridBasicInfoHistory get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
//        return this.gridBasicInfoHistoryService.get(id);
//    }

    @PostMapping({"/list"})
    @ApiOperation(value = "根据网格id获得网格基础信息历史表-变化前快照数据列表}", httpMethod = "POST", notes = "根据网格id获得获取网格基础信息历史表-变化前快照列表")
    public PageList<GridBasicInfoHistoryDTO> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.gridBasicInfoHistoryService.queryList(queryFilter);
    }

}
