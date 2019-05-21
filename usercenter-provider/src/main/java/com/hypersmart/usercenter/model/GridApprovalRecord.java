package com.hypersmart.usercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hypersmart.base.id.genId.Suid;
import com.hypersmart.base.id.genId.UUIdGenId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 网格审批记录
 *
 * @author han
 * @date 2019-03-20
 */
@Table(name = "grid_approval_record")
@ApiModel(value = "GridApprovalRecord", description = "网格审批记录表")
public class GridApprovalRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = Suid.class)
	@Column(name = "id")
	@ApiModelProperty("主键")
	private String id;

	@Column(name = "grid_id")
	@ApiModelProperty("网格id")
	private String gridId;

	@Column(name = "approval_type")
	@ApiModelProperty("申请操作类型（新增网格、覆盖范围变更、网格变更管家、管家解除关联、停用网格）")
	private String approvalType;

	@Column(name = "approval_content")
	@ApiModelProperty("申请内容")
	private String approvalContent;

	@Column(name = "create_date")
	@ApiModelProperty("提交时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;

	@Column(name = "approval_status")
	@ApiModelProperty("1审批中 2通过 3驳回")
	private Integer approvalStatus;

	@Column(name = "approval_opinion")
	@ApiModelProperty("审批意见")
	private String approvalOpinion;

	@Column(name = "approver")
	@ApiModelProperty("审批人")
	private String approver;

	@Column(name = "approval_date")
	@ApiModelProperty("审批时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date approvalDate;

	@Column(name = "proc_inst_Id")
	@ApiModelProperty("流程实例ID")
	private String procInstId;

	@Column(name = "call_flow_content")
	@ApiModelProperty("流程内容JSonString")
	private String callFlowContent;

	@Column(name = "call_status")
	@ApiModelProperty("调用K2")
	private Integer callStatus;

	@Column(name = "call_error_message")
	@ApiModelProperty("调用K2消息：失败时存储")
	private String callErrorMessage;

	@Column(name = "submitter_id")
	@ApiModelProperty("提交申请人ID")
	private String submitterId;

	@Column(name = "submitter_name")
	@ApiModelProperty("提交申请人名称")
	private String submitterName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getApprovalContent() {
		return approvalContent;
	}

	public void setApprovalContent(String approvalContent) {
		this.approvalContent = approvalContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getCallFlowContent() {
		return callFlowContent;
	}

	public void setCallFlowContent(String callFlowContent) {
		this.callFlowContent = callFlowContent;
	}

	public Integer getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(Integer callStatus) {
		this.callStatus = callStatus;
	}

	public String getCallErrorMessage() {
		return callErrorMessage;
	}

	public void setCallErrorMessage(String callErrorMessage) {
		this.callErrorMessage = callErrorMessage;
	}

	public String getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}

	public String getSubmitterName() {
		return submitterName;
	}

	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}
}
