package com.hypersmart.usercenter.model;

public class UserIdDemensionCode {
    private String userId;
//    private String DemensionCode;
private String demensionCode;
    public UserIdDemensionCode() {
    }

    public UserIdDemensionCode(String userId, String demensionCode) {
        this.userId = userId;
        this.demensionCode = demensionCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDemensionCode() {
        return demensionCode;
    }

    public void setDemensionCode(String demensionCode) {
        this.demensionCode = demensionCode;
    }
}
