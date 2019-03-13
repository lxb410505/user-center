package com.hypersmart.usercenter.model;

public class UserIdDemensionCode {
    private String userId;
    private String DemensionCode;

    public UserIdDemensionCode() {
    }

    public UserIdDemensionCode(String userId, String demensionCode) {
        this.userId = userId;
        DemensionCode = demensionCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDemensionCode() {
        return DemensionCode;
    }

    public void setDemensionCode(String demensionCode) {
        DemensionCode = demensionCode;
    }
}
