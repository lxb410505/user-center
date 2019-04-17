package com.hypersmart.usercenter.model;

import java.io.Serializable;

/**
 * @author han
 * @date 2019-03-21
 */
public class K2Result implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 流程实例Id(流程编号)
	 */
	private String procInstId;

	/**
	 * //0表示审批不通过，1表示审批通过，2表示退回修改重新发起，3表示退回修改，从当前节点继续
	 */
	private String resultCode;

	/**
	 * 如果审批不通过，需要有不通过的原因
	 */
	private String message;

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
