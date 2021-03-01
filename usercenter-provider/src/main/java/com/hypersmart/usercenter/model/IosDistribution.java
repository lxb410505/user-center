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

import java.io.Serializable;

/**
 * ios编码分配表
 *
 * @author
 * @email
 * @date 2021-03-01 17:36:15
 */

@Table(name = "ios_distribution")
@ApiModel(value = "IosDistribution", description = "ios编码分配表")
public class IosDistribution implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "ID_")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "CODE_")
    @ApiModelProperty("名称")
    private String code;

    @Column(name = "IS_BIND_")
    @ApiModelProperty("是否绑定，1绑定，0未绑定")
    private String isBind;

    @Column(name = "BIND_ACCOUNT_")
    @ApiModelProperty("绑定账号")
    private String bindAccount;

    @Column(name = "BIND_NAME_")
    @ApiModelProperty("绑定姓名")
    private String bindName;

    @Column(name = "BIND_TIME_")
    @ApiModelProperty("绑定时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bindTime;

    @Column(name = "IS_DELE_")
    @ApiModelProperty("是否已删，1已删除，0未删除")
    private String isDele;

    @Column(name = "CREATE_BY_")
    @ApiModelProperty("创建人ID")
    private String createBy;

    @Column(name = "CREATE_TIME_")
    @ApiModelProperty("首次创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "UPDATE_BY_")
    @ApiModelProperty("更新人ID")
    private String updateBy;

    @Column(name = "UPDATE_TIME_")
    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "CODE_LINK_")
    @ApiModelProperty("Code Redemption Link(暂不用)")
    private String codeLink;

    @Column(name = "ORDER_NUM_")
    @ApiModelProperty("订单号(不用，备份）")
    private String orderNum;

//    @Column(name = "TENANT_ID")
//    @ApiModelProperty("tenantId")
//    private String tenantId;

    public String getCodeLink() {
        return codeLink;
    }

    public void setCodeLink(String codeLink) {
        this.codeLink = codeLink;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBindName() {
        return bindName;
    }

    public void setBindName(String bindName) {
        this.bindName = bindName;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setIsBind(String isBind) {
        this.isBind = isBind;
    }

    public String getIsBind() {
        return this.isBind;
    }

    public void setBindAccount(String bindAccount) {
        this.bindAccount = bindAccount;
    }

    public String getBindAccount() {
        return this.bindAccount;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public Date getBindTime() {
        return this.bindTime;
    }

    public void setIsDele(String isDele) {
        this.isDele = isDele;
    }

    public String getIsDele() {
        return this.isDele;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

//    public void setTenantId(String tenantId) {
//        this.tenantId = tenantId;
//    }
//
//    public String getTenantId() {
//        return this.tenantId;
//    }

    public String toString() {
        return
                new ToStringBuilder(this)
                        .append("id", this.id)
                        .append("code", this.code)
                        .append("isBind", this.isBind)
                        .append("bindAccount", this.bindAccount)
                        .append("bindTime", this.bindTime)
                        .append("isDele", this.isDele)
                        .append("createBy", this.createBy)
                        .append("createTime", this.createTime)
                        .append("updateBy", this.updateBy)
                        .append("updateTime", this.updateTime)
//                        .append("tenantId", this.tenantId)
                        .toString();
    }
}
