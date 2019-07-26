package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import tk.mybatis.mapper.annotation.KeySql;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 用户技能管理
 *
 * @author hwy
 * @email 123@12
 * @date 2019-07-25 16:44:59
 */

@Table(name = "uc_user_skill")
@ApiModel(value = "UcUserSkill", description = "用户技能管理")
public class UcUserSkill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "ID_")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "engineering_skill_code")
    @ApiModelProperty("技能code")
    private String engineeringSkillCode;

    @Column(name = "user_id")
    @ApiModelProperty("用户id")
    private String userId;

    @Column(name = "is_dele")
    @ApiModelProperty("是否已删，1已删除，0未删除")
    private String isDele;

    @Column(name = "row_time")
    @ApiModelProperty("入库的时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rowTime;

    @Column(name = "row_version")
    @ApiModelProperty("记录的版本号")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rowVersion;

    @Column(name = "created_by")
    @ApiModelProperty("创建人")
    private String createdBy;

    @Column(name = "updated_by")
    @ApiModelProperty("更新人")
    private String updatedBy;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setEngineeringSkillCode(String engineeringSkillCode) {
        this.engineeringSkillCode = engineeringSkillCode;
    }

    public String getEngineeringSkillCode() {
        return this.engineeringSkillCode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setIsDele(String isDele) {
        this.isDele = isDele;
    }

    public String getIsDele() {
        return this.isDele;
    }

    public void setRowTime(Date rowTime) {
        this.rowTime = rowTime;
    }

    public Date getRowTime() {
        return this.rowTime;
    }

    public void setRowVersion(Date rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Date getRowVersion() {
        return this.rowVersion;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String toString() {
        return
                new ToStringBuilder(this)
                        .append("id", this.id)
                        .append("engineeringSkillCode", this.engineeringSkillCode)
                        .append("userId", this.userId)
                        .append("isDele", this.isDele)
                        .append("rowTime", this.rowTime)
                        .append("rowVersion", this.rowVersion)
                        .append("createdBy", this.createdBy)
                        .append("updatedBy", this.updatedBy)
                        .toString();
    }
}
