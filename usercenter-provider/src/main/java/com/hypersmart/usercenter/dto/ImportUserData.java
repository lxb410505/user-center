package com.hypersmart.usercenter.dto;

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.usercenter.model.UcRole;
import com.hypersmart.usercenter.model.UcUser;

public class ImportUserData {
    //一级单位
    private UcOrg firstUnit;
    //二级单位
    private UcOrg secondUnit;
    //三级单位
    private UcOrg thirdUnit;
    //四级单位
    private UcOrg fourthUnit;
    //工单角色
    private UcOrgJob orgJob;
    //role
    private UcRole ucRole;
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

    public UcRole getUcRole() {
        return ucRole;
    }

    public void setUcRole(UcRole ucRole) {
        this.ucRole = ucRole;
    }
    public UcOrg getFirstUnit() {
        return firstUnit;
    }

    public void setFirstUnit(UcOrg firstUnit) {
        this.firstUnit = firstUnit;
    }

    public UcOrg getSecondUnit() {
        return secondUnit;
    }

    public void setSecondUnit(UcOrg secondUnit) {
        this.secondUnit = secondUnit;
    }

    public UcOrg getThirdUnit() {
        return thirdUnit;
    }

    public void setThirdUnit(UcOrg thirdUnit) {
        this.thirdUnit = thirdUnit;
    }

    public UcOrg getFourthUnit() {
        return fourthUnit;
    }

    public void setFourthUnit(UcOrg fourthUnit) {
        this.fourthUnit = fourthUnit;
    }

    public Integer getExcleRow() {
        return excleRow;
    }

    public void setExcleRow(Integer excleRow) {
        this.excleRow = excleRow;
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
