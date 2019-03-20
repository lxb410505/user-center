package com.hypersmart.usercenter.dto;

import com.hypersmart.usercenter.model.UcOrg;

public class UcOrgExtend extends UcOrg {
    private static final long serialVersionUID = -4533006250821698106L;

    /**
     * 组织维度编码
     */
    private String dimCode;

    /**
     * 组织维度名称
     */
    private String dimName;
    public String getDimCode() {
        return dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getDimName() {
        return dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }
}
