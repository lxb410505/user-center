package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.exception.RequiredException;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.dto.UserDetailRb;
import com.hypersmart.usercenter.dto.UserDetailValue;
import com.hypersmart.usercenter.model.GradeDemCode;
import com.hypersmart.usercenter.model.GroupIdentity;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.util.ResourceErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.UcUserService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 查询所有未删除的用户列表
     *
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
     *
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
     *
     * @param orgCode
     * @param jobCode
     * @return 编辑人--》李良亚
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
        String userId = gradeDemCode.getUserId();
        String grade = gradeDemCode.getGrade();
        String DemensionCode = gradeDemCode.getDemensionCode();
        String fullname = gradeDemCode.getFullname();
        String mobile = gradeDemCode.getMobile();
        List<UcUser> ucUsers = this.ucUserService.queryUserByGradeAndDemCode(userId, grade, DemensionCode, fullname, mobile);
        return ucUsers;
    }

    /**
     * 根据组织和职务查询对应组织中的用户
     *
     * @param queryFilter
     * @return
     */
    @RequestMapping(value = {"searchUserByCondition"}, method = {
            RequestMethod.POST}, produces = {
            "application/json; charset=utf-8"})
    @ApiOperation(value = "根据组织和职务查询对应组织中的用户", httpMethod = "POST", notes = "根据组织和职务查询对应组织中的用户")
    public PageList<UcUser> searchUserByCondition(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody
                                                          QueryFilter queryFilter) {
        return ucUserService.searchUserByCondition(queryFilter);
    }

    @RequestMapping(value = {"pagedQueryByJobCodes"}, method = {
            RequestMethod.POST}, produces = {
            "application/json; charset=utf-8"})
    @ApiOperation(value = "根据职务编码查询对应的用户", httpMethod = "POST", notes = "根据职务编码查询对应的用户")
    public PageList<UcUser> pagedQueryByJobCodes(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter, @ApiParam(name = "jobCodes", value = "职务编码（多个以英文逗号(,)隔开）") @RequestParam("jobCodes") String jobCodes) {
        return ucUserService.pagedQueryByJobCodes(jobCodes, queryFilter);
    }

    @RequestMapping(value = {"userDetails"}, method = {
            RequestMethod.POST}, produces = {
            "application/json; charset=utf-8"})
    @ApiOperation(value = "通过用户id和地块id，获取信息", httpMethod = "POST", notes = "获取特定字段")
    public CommonResult<UserDetailValue> searchUserDetailByCondition(@RequestBody UserDetailRb userDetailRb) {
        if (StringUtil.isEmpty(userDetailRb.getUserId())) {
            return new CommonResult<>(false, "用户信息为空");
        } else if (StringUtil.isEmpty(userDetailRb.getDevideId())) {
            return new CommonResult<>(false, "地块信息为空");
        }
        return new CommonResult<UserDetailValue>(true, "处理成功", ucUserService.searchUserDetailByCondition(userDetailRb), 200);
    }

    /**
     * sunwenjie
     * 工单系统总部角色导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importOrgUserData", method = RequestMethod.POST)
    public CommonResult<String> importOrgUserData(@RequestParam("file") MultipartFile file) throws Exception {
        CommonResult commonResult = new CommonResult();
        ResourceErrorCode resourceErrorCode = ucUserService.importOrgUser(file);
        if (ResourceErrorCode.SUCCESS.getCode() == resourceErrorCode.getCode()) {
            commonResult.setState(true);
            commonResult.setMessage(resourceErrorCode.getMessage());
        } else {
            commonResult.setState(false);
            commonResult.setMessage(resourceErrorCode.getMessage());
        }
        return commonResult;
    }

    @RequestMapping(value = {"/users/getByJobCodeAndOrgIdAndDimCodeDeeply"}, method = {
            org.springframework.web.bind.annotation.RequestMethod.GET}, produces = {
            "application/json; charset=utf-8"})
    @ApiOperation(value = "根据组织Id、条线编码和职务编码获取对应条线上的对应人员", httpMethod = "GET", notes = "根据组织Id、条线编码和职务编码获取对应条线上的对应人员")
    public Set<GroupIdentity> getByJobCodeAndOrgIdAndDimCodeDeeply(
            @ApiParam(name = "jobCode", value = "职务编码") @RequestParam(required = false) String jobCode,
            @ApiParam(name = "orgId", value = "组织Id", required = true) @RequestParam String orgId,
            @ApiParam(name = "dimCode", value = "维度编码") @RequestParam(required = false) String dimCode,
            @ApiParam(name = "fullName", value = "姓名") @RequestParam(required = false) String fullName
    )
            throws Exception {
        if (StringUtil.isEmpty(orgId)) {
            throw new RequiredException("职务编码、组织Id为空！");
        }
        return ucUserService.getByJobCodeAndOrgIdAndDimCodeDeeply(jobCode, orgId, dimCode, fullName);
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
