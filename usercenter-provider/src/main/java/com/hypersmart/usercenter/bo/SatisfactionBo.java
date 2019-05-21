package com.hypersmart.usercenter.bo;

import java.util.List;

public class SatisfactionBo {
    private List<String> orgIds;
    private String time;

    public List<String> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
