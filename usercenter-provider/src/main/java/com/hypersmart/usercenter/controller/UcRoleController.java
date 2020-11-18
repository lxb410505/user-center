package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.UcRole;
import com.hypersmart.usercenter.service.UcRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({ "/api/role/v1/" })
@Api(tags = { "UcRoleController" })
public class UcRoleController extends BaseController {

	@Autowired
	UcRoleService ucRoleService;

	@RequestMapping(value = { "role/getRolesByUserId" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET }, produces = {
					"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户所属角色列表", httpMethod = "GET", notes = "获取用户所属角色列表")
	public List<UcRole> getRolesByUserId(
			@ApiParam(name = "userId", value = "用户帐号", required = true) @RequestParam String userId)
			throws Exception {
		if(StringUtil.isEmpty(userId)){
			IUser iUser = ContextUtil.getCurrentUser();
			userId=iUser.getUserId();
		}
		return this.ucRoleService.getRolesByUserId(userId);
	}

}
