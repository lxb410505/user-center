package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "rsun_user_exception_hi_log")
@ApiModel(value = "rsunUserExceptionHiLog", description = "用户异常信息记录")
public class RsunUserExceptionHiLog implements Serializable {
    @Column(name = "uc_user_id")
    @ApiModelProperty("用户id")
    private String ucUserId;

    @Column(name = "ins_code")
    @ApiModelProperty("实例id")
    private String insCode;

    @Column(name = "yw_table_id")
    @ApiModelProperty("业务表id")
    private String ywTableId;

    @Column(name = "timeout_sta")
    @ApiModelProperty("超时标志位")
    private Integer timeoutSta;

    @Column(name = "creatime")
    @ApiModelProperty("超时的发生时间")
    private Date creatime;

    @Column(name = "reason")
    @ApiModelProperty("超时原因")
    private String reason;

    public String getUcUserId() {
        return ucUserId;
    }

    public void setUcUserId(String ucUserId) {
        this.ucUserId = ucUserId;
    }

    public String getInsCode() {
        return insCode;
    }

    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }

    public String getYwTableId() {
        return ywTableId;
    }

    public void setYwTableId(String ywTableId) {
        this.ywTableId = ywTableId;
    }

    public Integer getTimeoutSta() {
        return timeoutSta;
    }

    public void setTimeoutSta(Integer timeoutSta) {
        this.timeoutSta = timeoutSta;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RsunUserExceptionHiLog{" +
                "ucUserId='" + ucUserId + '\'' +
                ", insCode='" + insCode + '\'' +
                ", ywTableId='" + ywTableId + '\'' +
                ", timeoutSta=" + timeoutSta +
                ", creatime=" + creatime +
                ", reason='" + reason + '\'' +
                '}';
    }
}
