package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.GradeDemCode;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.UcOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.Context;

import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.UcUserService;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucUser"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucUserController"})
public class UcUserController extends BaseController {
    @Resource
    UcUserService ucUserService;

    @Resource
    UcOrgService ucOrgService;

    @PostMapping({"/list"})
    @ApiOperation(value = "用户管理数据列表}", httpMethod = "POST", notes = "获取用户管理列表")
    public PageList<UcUser> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucUserService.query(queryFilter);
    }

    /**
     *查询所有未删除的用户列表
     * @return
     */
    @PostMapping({"/allList"})
    @ApiOperation(value = "用户管理数据列表}", httpMethod = "POST", notes = "获取用户管理列表")
    public List<UcUser> allList() {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("IS_DELE_", 1, QueryOP.EQUAL);
        return this.ucUserService.query(queryFilter).getRows();
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "用户管理数据列表", httpMethod = "GET", notes = "获取单个用户管理记录")
    public UcUser get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucUserService.get(id);
    }

    /**
     * 设置当前组织
     * @param orgId
     * @return
     */
    @GetMapping({"/setCurrentOrg/{orgId}"})
    @ApiOperation(value = "设置当前组织", httpMethod = "GET", notes = "设置当前组织")
    public CommonResult<String> setCurrentOrg(@PathVariable("orgId") String orgId) {
        CommonResult<String> commonResult = new CommonResult<>();
        try {
            UcOrg ucorg = ucOrgService.get(orgId);
            if (null != ucorg) {
                IUser iUser = ContextUtil.getCurrentUser();
                Map<String, String> map = iUser.getAttributes();
                map.put("currentOrg", orgId);
                iUser.setAttributes(map);
                ContextUtil.setCurrentUser(iUser);
                commonResult.setState(true);
            }
        } catch (Exception e) {
            commonResult.setState(false);
        }
        return commonResult;
    }


    /**
     * 通过组织编码和职务编码，深度获取用户
     * @param orgCode
     * @param jobCode
     * @return
     * 编辑人--》李良亚
     */
    @GetMapping({"/getDepUserByOrgCodeAndJobCode"})
    @ApiOperation(value = "通过组织编码和职务编码，深度获取用户", httpMethod = "GET", notes = "深度获取用户")
    public CommonResult<String> getDepUserByOrgCodeAndJobCode(@RequestParam("orgCode") String orgCode, @RequestParam("jobCode") String jobCode) {
        return null;
    }

    //根据组织和用户有权看到的条线 查询人员列表接口
    @PostMapping({"/queryUserByGradeAndDemCode"})
    @ApiOperation(value = "根据组织和用户有权看到的条线，查询人员接口}", httpMethod = "POST", notes = "查询人员接口")
    public List<UcUser> queryUserByGradeAndDemCode(@RequestBody GradeDemCode gradeDemCode) {
        String userId=gradeDemCode.getUserId();
        String grade=gradeDemCode.getGrade();
        String DemensionCode=gradeDemCode.getDemensionCode();
        String fullname=gradeDemCode.getFullname();
        String mobile=gradeDemCode.getMobile();
        List<UcUser> ucUsers = this.ucUserService.queryUserByGradeAndDemCode(userId,grade,DemensionCode,fullname,mobile);
        return ucUsers;
    }

    /**
     * 根据组织和职务查询对应组织中的用户
     * @param queryFilter
     * @return
     */
    @RequestMapping(value = {"searchUserByCondition"}, method = {
            RequestMethod.POST}, produces = {
            "application/json; charset=utf-8"})
    @ApiOperation(value = "根据组织和职务查询对应组织中的用户", httpMethod = "POST", notes = "根据组织和职务查询对应组织中的用户")
    public PageList<UcUser> searchUserByCondition(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody
                                                          QueryFilter queryFilter){
        return ucUserService.searchUserByCondition(queryFilter);
    }
//    @PostMapping({"add"})
//    @ApiOperation(value = "新增用户管理信息", httpMethod = "POST", notes = "保存用户管理")
//    public CommonResult<String> post(@ApiParam(name = "ucUser", value = "用户管理业务对象", required = true) @RequestBody UcUser model) {
//        String msg = "添加用户管理成功";
//        this.ucUserService.insert(model);
//        return new CommonResult(msg);
//    }
//
//    @PutMapping({"update"})
//    @ApiOperation(value = "更新指定id的 用户管理 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 用户管理 信息（更新全部信息）")
//    public CommonResult<String> put(@ApiParam(name = "ucUser", value = "用户管理业务对象", required = true) @RequestBody UcUser model) {
//        String msg = "更新用户管理成功";
//        this.ucUserService.update(model);
//        return new CommonResult(msg);
//    }
//
//    @PatchMapping({"update"})
//    @ApiOperation(value = "更新指定id的 用户管理 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 用户管理 信息（更新部分信息）")
//    public CommonResult<String> patch(@ApiParam(name = "ucUser", value = "用户管理业务对象", required = true) @RequestBody UcUser model) {
//        String msg = "更新用户管理成功";
//        this.ucUserService.updateSelective(model);
//        return new CommonResult(msg);
//    }
//
//    @DeleteMapping({"remove/{id}"})
//    @ApiOperation(value = "删除用户管理记录", httpMethod = "DELETE", notes = "删除用户管理记录")
//    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
//        this.ucUserService.delete(id);
//        return new CommonResult(true, "删除成功");
//    }
//
//    @DeleteMapping({"/remove"})
//    @ApiOperation(value = "批量删除用户管理记录", httpMethod = "DELETE", notes = "批量删除用户管理记录")
//    public CommonResult<String> removes(
//            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
//        String[] aryIds = ids.split(",");
//        this.ucUserService.delete(aryIds);
//        return new CommonResult(true, "批量删除成功");
//    }


}
