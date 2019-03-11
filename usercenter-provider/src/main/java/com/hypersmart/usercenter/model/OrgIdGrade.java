package com.hypersmart.usercenter.model;

public class OrgIdGrade {
    private String orgId;
    private String grade;

    public OrgIdGrade() {
    }

    public OrgIdGrade(String orgId, String grade) {
        this.orgId = orgId;
        this.grade = grade;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
