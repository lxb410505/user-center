package com.hypersmart.usercenter.bo;

import java.util.List;

public class EngineeringGrabOrdersDataInsightBO {
    private String startYears;
    private String endYears;
    private List<String> projectIdList;

    public String getStartYears() {
        return startYears;
    }

    public void setStartYears(String startYears) {
        this.startYears = startYears;
    }

    public String getEndYears() {
        return endYears;
    }

    public void setEndYears(String endYears) {
        this.endYears = endYears;
    }

    public List<String> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<String> projectIdList) {
        this.projectIdList = projectIdList;
    }
}
