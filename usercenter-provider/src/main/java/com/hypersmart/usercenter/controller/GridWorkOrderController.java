package com.hypersmart.usercenter.controller;

import com.hypersmart.base.query.*;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = {"/grid-work-order"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridWorkOrderController"})
public class GridWorkOrderController {

    @Resource
    UcUserService ucUserService;

    @Resource
    GridBasicInfoService gridBasicInfoService;

    /*
    *   unitType : 参数类型 1：楼栋 2:单元
    *   unitId :参数id  对应grid_range 的resourceId字段
    * */
    @PostMapping("/getUserByUnitId")
    public UcUser getUserByUnitId(@RequestParam("unitId") String unitId,@RequestParam("unitType")String unitType){
        if(StringUtil.isNotEmpty(unitId) && StringUtil.isNotEmpty(unitType)){
            UcUser user = ucUserService.getUserByUnitId(unitId,unitType);
            if(null != user && StringUtil.isNotEmpty(user.getId())){
                return user;
            }
        }
        return new UcUser();
    }

    @PostMapping("/getUserByDivideId")
    public UcUser getUserByDivideId(@RequestParam("divideId") String divideId,@RequestParam("gridType")String gridType){
        if(StringUtil.isNotEmpty(divideId) && StringUtil.isNotEmpty(divideId)){
            QueryFilter query = QueryFilter.build();
            query.addFilter("gridType",gridType, QueryOP.EQUAL,FieldRelation.AND);
            query.addFilter("stagingId",divideId,QueryOP.EQUAL,FieldRelation.AND);
            query.addFilter("housekeeperId","",QueryOP.NOTNULL,FieldRelation.AND);
            PageList<GridBasicInfo> page = gridBasicInfoService.query(query);
            if(null != page && null != page.getRows() && page.getRows().size()>0){
                GridBasicInfo info = page.getRows().get(0);
                UcUser ucUser = ucUserService.get(info.getHousekeeperId());
                if(null != ucUser && StringUtil.isNotEmpty(ucUser.getId())){
                    return ucUser;
                }
            }
        }
        return new UcUser();
    }
}
