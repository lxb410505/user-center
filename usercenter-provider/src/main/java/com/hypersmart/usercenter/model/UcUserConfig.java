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
 * @author Magellan
 * @email 121935403@qq.com
 * @date 2019-04-30 15:13:12
 */

@Table(name = "uc_user_config")
@ApiModel(value = "UcUserConfig", description = "")
public class UcUserConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "ID_")
    @ApiModelProperty("用户id")
    private String id;

    @Column(name = "Is_Night_Pattern")
    @ApiModelProperty("是否夜间模式（1：是；0：否）")
    private Integer isNightPattern;

    @Column(name = "UPDATE_TIME_")
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "IS_DELE_")
    @ApiModelProperty("是否已删，0已删除，1未删除")
    private String isDele;

    @Column(name = "VERSION_")
    @ApiModelProperty("版本号")
    private Integer version;

    @Column(name = "CREATE_BY_")
    @ApiModelProperty("创建人id")
    private String createBy;

    @Column(name = "UPDATE_BY_")
    @ApiModelProperty("更新人id")
    private String updateBy;
    @Column(name = "USER_ID")
    @ApiModelProperty("用户id")
    private String userId;

    @Column(name = "CREATE_TIME_")
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setIsNightPattern(Integer isNightPattern) {
        this.isNightPattern = isNightPattern;
    }

    public Integer getIsNightPattern() {
        return this.isNightPattern;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setIsDele(String isDele) {
        this.isDele = isDele;
    }

    public String getIsDele() {
        return this.isDele;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String toString() {
        return
                new ToStringBuilder(this)
                        .append("id", this.id)
                        .append("isNightPattern", this.isNightPattern)
                        .append("updateTime", this.updateTime)
                        .append("isDele", this.isDele)
                        .append("version", this.version)
                        .append("createBy", this.createBy)
                        .append("updateBy", this.updateBy)
                        .append("createTime", this.createTime)
                        .toString();
    }
}
