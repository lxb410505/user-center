package com.hypersmart.usercenter.dto;

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.usercenter.model.UcUser;

public class ImportUserData {
    //中心
    private UcOrg center;
    //部门
    private UcOrg department;
    //工单角色
    private UcOrgJob orgJob;
    //PSA编号
    private String sapCode;
    //用户
    private UcUser user;
    //岗位
    private String posName;
    //职级
    private String postKey;
    //是否存在人员
    private Boolean isExistUser;
    //表格行
    private Integer excleRow;


    public Integer getExcleRow() {
        return excleRow;
    }

    public void setExcleRow(Integer excleRow) {
        this.excleRow = excleRow;
    }

    public UcOrg getCenter() {
        return center;
    }

    public void setCenter(UcOrg center) {
        this.center = center;
    }

    public UcOrg getDepartment() {
        return department;
    }

    public void setDepartment(UcOrg department) {
        this.department = department;
    }

    public UcOrgJob getOrgJob() {
        return orgJob;
    }

    public void setOrgJob(UcOrgJob orgJob) {
        this.orgJob = orgJob;
    }

    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public UcUser getUser() {
        return user;
    }

    public void setUser(UcUser user) {
        this.user = user;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public Boolean getExistUser() {
        return isExistUser;
    }

    public void setExistUser(Boolean existUser) {
        isExistUser = existUser;
    }
}
