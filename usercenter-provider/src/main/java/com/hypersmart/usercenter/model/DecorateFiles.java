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
 * 客户装修资料归档管理
 *
 * @author ljia
 * @email 123@456.com
 * @date 2019-08-27 14:21:33
 */

@Table(name = "decorate_files")
@ApiModel(value = "DecorateFiles", description = "客户装修资料归档管理")
public class DecorateFiles implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "ID_")
    @ApiModelProperty("资料id")
    private String id;

    @Column(name = "DECORATE_ID_")
    @ApiModelProperty("关联装修id")
    private String decorateId;

    @Column(name = "DECORATE_FILE_CODE_")
    @ApiModelProperty("名称key")
    private String decorateFileCode;

    @Column(name = "DECORATE_FILE_NAME_")
    @ApiModelProperty("名称")
    private String decorateFileName;

    @Column(name = "STATUS_")
    @ApiModelProperty("状态")
    private String status;

    @Column(name = "FILES_")
    @ApiModelProperty("文件")
    private String files;

    @Column(name = "IS_DELE_")
    @ApiModelProperty("是否已删，1已删除，0未删除")
    private String isDele;

    @Column(name = "CREATED_BY_")
    @ApiModelProperty("创建人")
    private String createdBy;

    @Column(name = "CREATE_TIME_")
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "UPDATED_BY_")
    @ApiModelProperty("更新人")
    private String updatedBy;

    @Column(name = "UPDATE_TIME_")
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "VERSION_")
    @ApiModelProperty("版本号")
    private Integer version;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setDecorateId(String decorateId) {
        this.decorateId = decorateId;
    }

    public String getDecorateId() {
        return this.decorateId;
    }

    public void setDecorateFileCode(String decorateFileCode) {
        this.decorateFileCode = decorateFileCode;
    }

    public String getDecorateFileCode() {
        return this.decorateFileCode;
    }

    public void setDecorateFileName(String decorateFileName) {
        this.decorateFileName = decorateFileName;
    }

    public String getDecorateFileName() {
        return this.decorateFileName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFiles() {
        return this.files;
    }

    public void setIsDele(String isDele) {
        this.isDele = isDele;
    }

    public String getIsDele() {
        return this.isDele;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return this.version;
    }

    public String toString() {
        return
                new ToStringBuilder(this)
                        .append("id", this.id)
                        .append("decorateId", this.decorateId)
                        .append("decorateFileCode", this.decorateFileCode)
                        .append("decorateFileName", this.decorateFileName)
                        .append("status", this.status)
                        .append("files", this.files)
                        .append("isDele", this.isDele)
                        .append("createdBy", this.createdBy)
                        .append("createTime", this.createTime)
                        .append("updatedBy", this.updatedBy)
                        .append("updateTime", this.updateTime)
                        .append("version", this.version)
                        .toString();
    }
}
