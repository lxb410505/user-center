package com.hypersmart.usercenter.dto;

import com.hypersmart.usercenter.model.GroupIdentity;

import java.util.List;

public class GroupIdentityDTO extends GroupIdentity {
    private String postName;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
