package com.hypersmart.usercenter.dto;

import com.hypersmart.usercenter.model.UcOrg;

public class UcOrgDTO extends UcOrg {

    public UcOrgDTO(){

    }
    public UcOrgDTO(UcOrg org){
        this.setId(org.getId());
        this.setId(org.getId());
        this.setId(org.getId());
        this.setId(org.getId());
        this.setName(org.getName());
        this.setParentId(org.getParentId());
        this.setOrderNo(org.getOrderNo());
        this.setCode(org.getCode());
        this.setGrade(org.getGrade());
        this.setPath(org.getPath());
        this.setPathName(org.getPathName());
        this.setDemId(org.getDemId());
        this.setLevel(org.getLevel());
        this.setVersion(org.getVersion());
    }

    private String parentCode;

    //主数据组团ID
    private String grpid;

    //主数据组团名称
    private String grpname;

    public String getGrpid() {
        return grpid;
    }

    public void setGrpid(String grpid) {
        this.grpid = grpid;
    }

    public String getGrpname() {
        return grpname;
    }

    public void setGrpname(String grpname) {
        this.grpname = grpname;
    }

    public String getParentCode() {
        return parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
