package com.hypersmart.usercenter.bo;

import java.io.Serializable;
import java.util.List;

public class UcOrgBO implements Serializable {
    private static final long serialVersionUID = 980864956340788451L;

    /**
     * 维度编码
     */
    private String dimId;
    /**
     * 维度编码
     */
    private String dimCode;

    /**
     * 单个组织id
     */
    private String orgId;

    /**
     * 组织id列表
     */
    private List<String> orgIdList;

    public String getDimId() {
        return dimId;
    }

    public void setDimId(String dimId) {
        this.dimId = dimId;
    }

    public String getDimCode() {
        return dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }
}
