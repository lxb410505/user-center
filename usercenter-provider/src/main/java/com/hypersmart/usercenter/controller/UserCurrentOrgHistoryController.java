package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UserCurrentOrgHistory;
import com.hypersmart.usercenter.service.UserCurrentOrgHistoryService;

import java.util.Date;
import java.util.List;

/**
 * 用户当前组织表
 *
 * @author admin
 * @email @sian.cn
 * @date 2019-01-29 20:06:10
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/userCurrentOrgHistory"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"userCurrentOrgHistoryController"})
public class UserCurrentOrgHistoryController extends BaseController {
    @Resource
        UserCurrentOrgHistoryService userCurrentOrgHistoryService;

    /**
     * 设置当前当前组织
     * @param user
     * @return
     */
    @PostMapping("setCurrentOrg")
    public CommonResult<String> setCurrentOrg(@RequestBody UserCurrentOrgHistory user){
        CommonResult<String> commonResult = new CommonResult<>();
        try{
            QueryFilter queryFilter = QueryFilter.build();
            queryFilter.addFilter("userId",user.getUserId(), QueryOP.EQUAL,FieldRelation.AND);
            List<UserCurrentOrgHistory> list = userCurrentOrgHistoryService.query(queryFilter).getRows();
            if(null != list && list.size()>0){
                UserCurrentOrgHistory history = list.get(0);
                history.setUserId(user.getUserId());
                history.setOrgId(user.getOrgId());
                history.setUpdateTime(new Date());
                userCurrentOrgHistoryService.update(history);
            }else{
                UserCurrentOrgHistory history = new UserCurrentOrgHistory();
                history.setUserId(user.getUserId());
                history.setOrgId(user.getOrgId());
                history.setUpdateTime(new Date());
                userCurrentOrgHistoryService.insert(history);
            }
            commonResult.setState(true);
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setState(false);
        }
        return commonResult;
    }

    /**
     * 获取人员的当前组织接口
     * @param userId
     * @return
     */
    @GetMapping("getCurrentOrg/{userId}")
    public CommonResult<String> getCurrentOrg(@PathVariable String userId){
        CommonResult<String> commonResult = new CommonResult<>();
        try{
            QueryFilter queryFilter = QueryFilter.build();
            queryFilter.addFilter("userId",userId, QueryOP.EQUAL,FieldRelation.AND);
            List<UserCurrentOrgHistory> list = userCurrentOrgHistoryService.query(queryFilter).getRows();
            if(null != list && list.size()>0){
                commonResult.setValue(list.get(0).getOrgId());
            }else{
                commonResult.setValue("");
            }
            commonResult.setState(true);
        }catch (Exception e){
            e.printStackTrace();
            commonResult.setState(false);
        }
        return commonResult;
    }
}
