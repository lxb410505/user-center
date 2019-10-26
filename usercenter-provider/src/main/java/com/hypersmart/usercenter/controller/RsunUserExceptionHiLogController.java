package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.RsunUserExceptionHiLog;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.service.RsunUserExceptionHiLogService;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping(value = {"/api/usercenter/v1/rsunUserExceptionHiLog"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"rsunUserExceptionHiLogController"})
public class RsunUserExceptionHiLogController extends BaseController {

    @Resource
    RsunUserExceptionHiLogService rsunUserExceptionHiLogService;

    @PostMapping({"/add"})
    @ApiOperation(value = "用户异常信息记录}", httpMethod = "POST", notes = "用户异常信息记录")
    public Boolean add(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody RsunUserExceptionHiLog rsunUserExceptionHiLog) {
        try {
            rsunUserExceptionHiLog.setCreatime(new Date());
            rsunUserExceptionHiLogService.insert(rsunUserExceptionHiLog);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
