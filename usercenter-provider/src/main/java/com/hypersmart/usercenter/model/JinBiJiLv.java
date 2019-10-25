package com.hypersmart.usercenter.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class JinBiJiLv {

    private Double workOrderCode; //工单id

    private Double coinNum; //金币数量

    private Double coinObtainReason; //金币获取原因

    private String  coinObtainTime; //金币获取时间

    public Double getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(Double workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public Double getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(Double coinNum) {
        this.coinNum = coinNum;
    }

    public Double getCoinObtainReason() {
        return coinObtainReason;
    }

    public void setCoinObtainReason(Double coinObtainReason) {
        this.coinObtainReason = coinObtainReason;
    }

    public String getCoinObtainTime() {
        return coinObtainTime;
    }

    public void setCoinObtainTime(String coinObtainTime) {
        this.coinObtainTime = coinObtainTime;
    }

    @Override
    public String toString() {
        return "JinBiJiLv{" +
                "workOrderCode=" + workOrderCode +
                ", coinNum=" + coinNum +
                ", coinObtainReason=" + coinObtainReason +
                ", coinObtainTime=" + coinObtainTime +
                '}';
    }
}
