package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.dto.CoinStatisticsListDTO;
import com.hypersmart.usercenter.model.RsunJbHiReward;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.service.RsunJbHiRewardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = {"/api/usercenter/v1/rsunjbhireward"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"rsunJbHiRewardController"})
public class RsunJbHiRewardController  extends BaseController {

    @Resource
    RsunJbHiRewardService rsunJbHiRewardService;

    @PostMapping({"/list"})
    @ApiOperation(value = "金币历史记录}", httpMethod = "POST", notes = "金币历史记录")
    public PageList<RsunJbHiReward> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.rsunJbHiRewardService.query(queryFilter);
     }


    /**
     * 查询员工各月份金币数
     * @param queryFilter
     * @return
     */
    @PostMapping({"/coinStatisticsList"})
    @ApiOperation(value = "员工各月份金币数列表}", httpMethod = "POST", notes = "获取员工各月份金币数列表")
    public PageList<CoinStatisticsListDTO> coinStatisticsList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        PageList<CoinStatisticsListDTO> coin= rsunJbHiRewardService.coinStatisticsList(queryFilter);
        return coin;
    }

    /**
     * 员工各月份金币数--导出
     * @param queryFilter
     * @return
     */
    @PostMapping({"/export"})
    @ApiOperation(value = "员工各月份金币数导出}", httpMethod = "POST", notes = "员工各月份金币数导出")
    public void export(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter, HttpServletResponse response) throws Exception {
        this.rsunJbHiRewardService.exportExcel(queryFilter,response);
    }





}
