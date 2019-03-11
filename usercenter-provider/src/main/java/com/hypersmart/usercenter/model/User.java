package com.hypersmart.usercenter.model;

public class User {
    private String userId;
    private String parentOrgId;

    public User() {
    }

    public User(String userId, String parentOrgId) {
        this.userId = userId;
        this.parentOrgId = parentOrgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
}
