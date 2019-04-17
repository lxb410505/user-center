package com.hypersmart.usercenter.service;

import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.GridApprovalRecord;
import com.hypersmart.usercenter.model.K2Result;

/**
 * @author han
 * @Date 2019-3-20
 */
public interface GridApprovalRecordService extends IGenericService<String, GridApprovalRecord> {

	/**
	 * 调用审批流程
	 *
	 * @param approvalType    审批类型
	 * @param gridId          网格id
	 * @param approvalContent 审批内容
	 */
	void callApproval(String approvalType, String gridId, Object approvalContent);

	/**
	 * 流程结果处理
	 *
	 * @param k2Result
	 */
	void processFlowResult(K2Result k2Result);
}
