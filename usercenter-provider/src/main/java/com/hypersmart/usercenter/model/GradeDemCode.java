package com.hypersmart.usercenter.model;

public class GradeDemCode {
    private String userId;
    private String grade;
    private String DemensionCode;
    private String fullname;
    private String mobile;

    public GradeDemCode() {
    }

    public GradeDemCode(String userId, String grade, String demensionCode, String fullname, String mobile) {
        this.userId = userId;
        this.grade = grade;
        DemensionCode = demensionCode;
        this.fullname = fullname;
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDemensionCode() {
        return DemensionCode;
    }

    public void setDemensionCode(String demensionCode) {
        DemensionCode = demensionCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
