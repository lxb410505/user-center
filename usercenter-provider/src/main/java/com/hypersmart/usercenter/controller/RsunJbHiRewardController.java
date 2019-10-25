package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.DecorateFiles;
import com.hypersmart.usercenter.model.rsunJbHiReward;
import com.hypersmart.usercenter.service.RsunJbHiRewardService;
import com.hypersmart.usercenter.service.RsunUserStarlLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value = {"/api/usercenter/v1/rsunjbhireward"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"rsunJbHiRewardController"})
public class RsunJbHiRewardController  extends BaseController {

    @Resource
    RsunJbHiRewardService rsunJbHiRewardService;

    @PostMapping({"/list"})
    @ApiOperation(value = "金币历史记录}", httpMethod = "POST", notes = "金币历史记录")
    public PageList<rsunJbHiReward> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        return this.rsunJbHiRewardService.query(queryFilter);
    }

}
