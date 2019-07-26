package com.hypersmart.usercenter.controller;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.usercenter.model.K2Result;
import com.hypersmart.usercenter.service.GridApprovalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = {"/grid-api/grid-approval-record"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridApprovalRecordController"})
public class GridApprovalRecordController {
	private static final Logger logger = LoggerFactory.getLogger(GridApprovalRecordController.class);

	@Resource
	private GridApprovalRecordService gridApprovalRecordService;

	@PostMapping({"/receiveFlowResult"})
	@ApiOperation(value = "接收流程处理结果", httpMethod = "POST", notes = "接收流程处理结果")
	@Async
	public CommonResult<String> receiveFlowResult(@RequestBody K2Result k2Result) {
		logger.info("开始执行k2回调业务");
		return gridApprovalRecordService.processFlowResult(k2Result);
	}
}
