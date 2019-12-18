package com.hypersmart.usercenter.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class  JinBiJiLv {

    private String workOrderCode; //工单id

    private Double coinNum; //金币数量

    private String coinObtainReason; //金币获取原因

    private String  coinObtainTime; //金币获取时间

    private Date date2;

    private String procId;

    private String bxContent;

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(String workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public Double getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(Double coinNum) {
        this.coinNum = coinNum;
    }

    public String getCoinObtainReason() {
        return coinObtainReason;
    }

    public void setCoinObtainReason(String coinObtainReason) {
        this.coinObtainReason = coinObtainReason;
    }

    public String getCoinObtainTime() {
        return coinObtainTime;
    }

    public void setCoinObtainTime(String coinObtainTime) {
        this.coinObtainTime = coinObtainTime;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public String getBxContent() {
        return bxContent;
    }

    public void setBxContent(String bxContent) {
        this.bxContent = bxContent;
    }

    @Override
    public String toString() {
        return "JinBiJiLv{" +
                "workOrderCode='" + workOrderCode + '\'' +
                ", coinNum=" + coinNum +
                ", coinObtainReason='" + coinObtainReason + '\'' +
                ", coinObtainTime='" + coinObtainTime + '\'' +
                ", date2=" + date2 +
                '}';
    }
}
