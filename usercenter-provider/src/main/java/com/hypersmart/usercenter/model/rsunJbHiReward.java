package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "rsun_jb_hi_reward")
@ApiModel(value = "rsunJbHiReward", description = "用户金币等级管理")
public class rsunJbHiReward implements Serializable {
    @Column(name = "uc_user_id")
    @ApiModelProperty("用户id")
    private String ucUserId;

    @Column(name = "jb_jl_reason")
    @ApiModelProperty("金币操作原因说明")
    private String jbJlReason;

    @Column(name = "jb_jl_time")
    @ApiModelProperty("金币奖励时间")
    private Date jbJlTime;

    @Column(name = "gd_code")
    @ApiModelProperty("工单编号")
    private String gdCode;

    @Column(name = "gcoin_val")
    @ApiModelProperty("金币奖励值")
    private Double gcoinVal;

    public String getUcUserId() {
        return ucUserId;
    }

    public void setUcUserId(String ucUserId) {
        this.ucUserId = ucUserId;
    }

    public String getJbJlReason() {
        return jbJlReason;
    }

    public void setJbJlReason(String jbJlReason) {
        this.jbJlReason = jbJlReason;
    }

    public Date getJbJlTime() {
        return jbJlTime;
    }

    public void setJbJlTime(Date jbJlTime) {
        this.jbJlTime = jbJlTime;
    }

    public String getGdCode() {
        return gdCode;
    }

    public void setGdCode(String gdCode) {
        this.gdCode = gdCode;
    }

    public Double getGcoinVal() {
        return gcoinVal;
    }

    public void setGcoinVal(Double gcoinVal) {
        this.gcoinVal = gcoinVal;
    }

    @Override
    public String toString() {
        return "rsunJbHiReward{" +
                "ucUserId='" + ucUserId + '\'' +
                ", jbJlReason='" + jbJlReason + '\'' +
                ", jbJlTime=" + jbJlTime +
                ", gdCode='" + gdCode + '\'' +
                ", gcoinVal=" + gcoinVal +
                '}';
    }
}
