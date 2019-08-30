package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import tk.mybatis.mapper.annotation.KeySql;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * 客户装修资料归档管理
 *
 * @author zcf
 * @email 1490***@qq.com
 * @date 2019-08-26 23:08:37
 */

@Table(name = "decorate")
@ApiModel(value = "Decorate", description = "客户装修资料归档管理")
public class Decorate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "ID_")
    @ApiModelProperty("装修id")
    private String id;

    @Column(name = "HOUSE_ID_")
    @ApiModelProperty("关联房产id")
    private String houseId;

    @Column(name = "DECORATE_COMPANY_")
    @ApiModelProperty("装修公司")
    private String decorateCompany;

    @Column(name = "ENABLED_FLAG_")
    @ApiModelProperty("启用标记（0：停用 1：启用）")
    private Integer enabledFlag;

    @Column(name = "APPLICATION_DATE_")
    @ApiModelProperty("申请日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applicationDate;

    @Column(name = "DECORATE_PERIOD_BEGIN_")
    @ApiModelProperty("装修工期开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date decoratePeriodBegin;

    @Column(name = "DECORATE_PERIOD_END_")
    @ApiModelProperty("装修工期结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date decoratePeriodEnd;

    @Column(name = "DECORATE_STATUS_")
    @ApiModelProperty("装修状态：1 施工中, 2 竣工验收,3 整改中,4 已合格,5 已入住")
    private Integer decorateStatus;

    @Column(name = "DECORATE_PRINCIPAL")
    @ApiModelProperty("装修负责人")
    private String decoratePrincipal;

    @Column(name = "PHONE_NUM_")
    @ApiModelProperty("联系方式")
    private String phoneNum;

    @Column(name = "GARBAGE_FEE")
    @ApiModelProperty("垃圾费用")
    private BigDecimal garbageFee;

    @Column(name = "REMARK_")
    @ApiModelProperty("备注")
    private String remark;

    @Column(name = "IS_DELETED_")
    @ApiModelProperty("是否已删（0：正常 1：删除）")
    private String isDeleted;

    @Column(name = "CREATED_BY_")
    @ApiModelProperty("创建人")
    private String createdBy;

    @Column(name = "CREATE_TIME_")
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "UPDATED_BY_")
    @ApiModelProperty("更新人")
    private String updatedBy;

    @Column(name = "UPDATE_TIME_")
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "ROW_VERSION_")
    @ApiModelProperty("版本号")
    private Integer rowVersion;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return this.houseId;
    }

    public void setDecorateCompany(String decorateCompany) {
        this.decorateCompany = decorateCompany;
    }

    public String getDecorateCompany() {
        return this.decorateCompany;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Integer getEnabledFlag() {
        return this.enabledFlag;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getApplicationDate() {
        return this.applicationDate;
    }

    public void setDecoratePeriodBegin(Date decoratePeriodBegin) {
        this.decoratePeriodBegin = decoratePeriodBegin;
    }

    public Date getDecoratePeriodBegin() {
        return this.decoratePeriodBegin;
    }

    public void setDecoratePeriodEnd(Date decoratePeriodEnd) {
        this.decoratePeriodEnd = decoratePeriodEnd;
    }

    public Date getDecoratePeriodEnd() {
        return this.decoratePeriodEnd;
    }

    public void setDecorateStatus(Integer decorateStatus) {
        this.decorateStatus = decorateStatus;
    }

    public Integer getDecorateStatus() {
        return this.decorateStatus;
    }

    public void setDecoratePrincipal(String decoratePrincipal) {
        this.decoratePrincipal = decoratePrincipal;
    }

    public String getDecoratePrincipal() {
        return this.decoratePrincipal;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setGarbageFee(BigDecimal garbageFee) {
        this.garbageFee = garbageFee;
    }

    public BigDecimal getGarbageFee() {
        return this.garbageFee;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsDeleted() {
        return this.isDeleted;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Integer getRowVersion() {
        return this.rowVersion;
    }

    public String toString() {
        return
                new ToStringBuilder(this)
                        .append("id", this.id)
                        .append("houseId", this.houseId)
                        .append("decorateCompany", this.decorateCompany)
                        .append("enabledFlag", this.enabledFlag)
                        .append("applicationDate", this.applicationDate)
                        .append("decoratePeriodBegin", this.decoratePeriodBegin)
                        .append("decoratePeriodEnd", this.decoratePeriodEnd)
                        .append("decorateStatus", this.decorateStatus)
                        .append("decoratePrincipal", this.decoratePrincipal)
                        .append("phoneNum", this.phoneNum)
                        .append("garbageFee", this.garbageFee)
                        .append("remark", this.remark)
                        .append("isDeleted", this.isDeleted)
                        .append("createdBy", this.createdBy)
                        .append("createTime", this.createTime)
                        .append("updatedBy", this.updatedBy)
                        .append("updateTime", this.updateTime)
                        .append("rowVersion", this.rowVersion)
                        .toString();
    }
}
