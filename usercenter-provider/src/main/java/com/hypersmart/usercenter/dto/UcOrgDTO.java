package com.hypersmart.usercenter.dto;

import com.hypersmart.usercenter.model.UcOrg;

public class UcOrgDTO extends UcOrg {
    private String parentCode;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
