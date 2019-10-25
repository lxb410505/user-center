package com.hypersmart.usercenter.dto;

import java.util.Date;

public class rsunJbDTO {
    private String ucUserId;

    private String jbJlReason;;

    private Date jbJlTime;

    private String gdCode;

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
        return "rsunJbDTO{" +
                "ucUserId='" + ucUserId + '\'' +
                ", jbJlReason='" + jbJlReason + '\'' +
                ", jbJlTime=" + jbJlTime +
                ", gdCode='" + gdCode + '\'' +
                ", gcoinVal=" + gcoinVal +
                '}';
    }
}
