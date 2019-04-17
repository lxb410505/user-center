package com.hypersmart.usercenter.dto;

import com.hypersmart.framework.model.GenericDTO;

import java.io.Serializable;

public class UserDetailRb implements Serializable{
    private String userId;
    private String devideId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDevideId() {
        return devideId;
    }

    public void setDevideId(String devideId) {
        this.devideId = devideId;
    }
}
