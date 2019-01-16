package com.hypersmart.usercenter.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hypersmart.base.id.genId.Suid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 网格基础信息表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */

@Table(name = "grid_basic_info")
@ApiModel(value = "GridBasicInfo", description = "网格基础信息表")
public class GridBasicInfo implements Serializable{
private static final long serialVersionUID=1L;

    @Id
    @KeySql(genId = Suid.class)
    @Column(name = "id")
    @ApiModelProperty("网格主键")
    private String id;

    @Column(name = "grid_code")
    @ApiModelProperty("网格编码")
    private String gridCode;

    @Column(name = "grid_name")
    @ApiModelProperty("网格名称")
    private String gridName;

    @Column(name = "grid_type")
    @ApiModelProperty("网格类型：公区网格、楼栋网格、服务中心网格")
    private String gridType;

    @Column(name = "grid_range")
    @ApiModelProperty("网格覆盖范围（json格式）：楼栋-单元-楼层-房产")
    private String gridRange;

    @Column(name = "grid_remark")
    @ApiModelProperty("网格备注")
    private String gridRemark;

    @Column(name = "area_id")
    @ApiModelProperty("所属区域ID")
    private String areaId;

    @Column(name = "project_id")
    @ApiModelProperty("所属项目ID")
    private String projectId;

    @Column(name = "massif_id")
    @ApiModelProperty("所属地块ID")
    private String massifId;

    @Column(name = "staging_id")
    @ApiModelProperty("所属分期ID")
    private String stagingId;

    @Column(name = "city_id")
    @ApiModelProperty("城市id")
    private String cityId;

    @Column(name = "housekeeper_id")
    @ApiModelProperty("管家ID（主数据用户主键）")
    private String housekeeperId;

    @Column(name = "update_times")
    @ApiModelProperty("网格更新次数：只记录产生过历史记录/工单的次数")
    private Integer updateTimes;

    @Column(name = "format_attribute")
    @ApiModelProperty("业态属性：商办、住宅、公寓等")
    private String formatAttribute;

    @Column(name = "creation_date")
    @ApiModelProperty("首次创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creationDate;

    @Column(name = "created_by")
    @ApiModelProperty("创建人")
    private String createdBy;

    @Column(name = "updation_date")
    @ApiModelProperty("上次修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updationDate;

    @Column(name = "updated_by")
    @ApiModelProperty("更新人")
    private String updatedBy;

    @Column(name = "row_time")
    @ApiModelProperty("入库的时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date rowTime;

    @Column(name = "row_version")
    @ApiModelProperty("记录的版本号")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date rowVersion;

    @Column(name = "tenant_id")
    @ApiModelProperty("租户ID")
    private String tenantId;

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

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getGridType() {
        return gridType;
    }

    public void setGridType(String gridType) {
        this.gridType = gridType;
    }

    public String getGridRange() {
        return gridRange;
    }

    public void setGridRange(String gridRange) {
        this.gridRange = gridRange;
    }

    public String getGridRemark() {
        return gridRemark;
    }

    public void setGridRemark(String gridRemark) {
        this.gridRemark = gridRemark;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMassifId() {
        return massifId;
    }

    public void setMassifId(String massifId) {
        this.massifId = massifId;
    }

    public String getStagingId() {
        return stagingId;
    }

    public void setStagingId(String stagingId) {
        this.stagingId = stagingId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getHousekeeperId() {
        return housekeeperId;
    }

    public void setHousekeeperId(String housekeeperId) {
        this.housekeeperId = housekeeperId;
    }

    public Integer getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(Integer updateTimes) {
        this.updateTimes = updateTimes;
    }

    public String getFormatAttribute() {
        return formatAttribute;
    }

    public void setFormatAttribute(String formatAttribute) {
        this.formatAttribute = formatAttribute;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String toString(){
        return new ToStringBuilder(this)
                    .append("id",this.id)
                    .append("gridCode",this.gridCode)
                    .append("gridName",this.gridName)
                    .append("gridType",this.gridType)
                    .append("gridRange",this.gridRange)
                    .append("gridRemark",this.gridRemark)
                    .append("areaId",this.areaId)
                    .append("projectId",this.projectId)
                    .append("massifId",this.massifId)
                    .append("stagingId",this.stagingId)
                    .append("cityId",this.cityId)
                    .append("housekeeperId",this.housekeeperId)
                    .append("updateTimes",this.updateTimes)
                    .append("formatAttribute",this.formatAttribute)
                    .append("creationDate",this.creationDate)
                    .append("createdBy",this.createdBy)
                    .append("updationDate",this.updationDate)
                    .append("updatedBy",this.updatedBy)
                    .append("rowTime",this.rowTime)
                    .append("rowVersion",this.rowVersion)
                    .append("tenantId",this.tenantId)
                    .append("enabledFlag",this.enabledFlag)
                    .append("isDeleted",this.isDeleted)
                .toString();
       }
}
