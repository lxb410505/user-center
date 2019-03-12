package com.hypersmart.usercenter.model;

public class UserIdParentId {
    private String userId;
    private String parentOrgId;

    public UserIdParentId() {
    }

    public UserIdParentId(String userId, String parentOrgId) {
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
