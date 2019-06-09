package com.hypersmart.usercenter.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hypersmart.base.id.genId.Suid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 网格基础信息表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */

@Table(name = "`public_gird_percent`")
@ApiModel(value = "publicGirdPercent", description = "公区网格")
public class PublicGirdPercent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @KeySql(genId = Suid.class)
    @Column(name = "id")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "public_grid_id")
    @ApiModelProperty("公区网格id")
    private String publicGridId;

    @Column(name = "public_grid_code")
    @ApiModelProperty("公区网格code")
    private String publicGridCode;

    @Column(name = "public_grid_name")
    @ApiModelProperty("公区网格名称")
    private String publicGridName;

    @Column(name = "area_percent")
    @ApiModelProperty("面积占比")
    private BigDecimal areaPercent;

    @Column(name = "staging_id")
    @ApiModelProperty("地块ID")
    private String stagingId;

    @Column(name = "staging_name")
    @ApiModelProperty("地块名称")
    private String stagingName;


    @Column(name = "creation_date")
    @ApiModelProperty("首次创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationDate;

    @Column(name = "created_by")
    @ApiModelProperty("创建人")
    private String createdBy;

    @Column(name = "updation_date")
    @ApiModelProperty("上次修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updationDate;

    @Column(name = "updated_by")
    @ApiModelProperty("更新人")
    private String updatedBy;

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

    @Column(name = "enabled_flag")
    @ApiModelProperty("启用标记（0：停用 1：启用）")
    private Integer enabledFlag;

    @Column(name = "is_deleted")
    @ApiModelProperty("是否删除（0：正常 1：删除）")
    private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPublicGridId() {
        return publicGridId;
    }

    public void setPublicGridId(String publicGridId) {
        this.publicGridId = publicGridId;
    }

    public String getPublicGridCode() {
        return publicGridCode;
    }

    public void setPublicGridCode(String publicGridCode) {
        this.publicGridCode = publicGridCode;
    }

    public String getPublicGridName() {
        return publicGridName;
    }

    public void setPublicGridName(String publicGridName) {
        this.publicGridName = publicGridName;
    }

    public BigDecimal getAreaPercent() {
        return areaPercent;
    }

    public void setAreaPercent(BigDecimal areaPercent) {
        this.areaPercent = areaPercent;
    }

    public String getStagingId() {
        return stagingId;
    }

    public void setStagingId(String stagingId) {
        this.stagingId = stagingId;
    }

    public String getStagingName() {
        return stagingName;
    }

    public void setStagingName(String stagingName) {
        this.stagingName = stagingName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdationDate() {
        return updationDate;
    }

    public void setUpdationDate(Date updationDate) {
        this.updationDate = updationDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getRowTime() {
        return rowTime;
    }

    public void setRowTime(Date rowTime) {
        this.rowTime = rowTime;
    }

    public Date getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Date rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
