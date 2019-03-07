package com.hypersmart.usercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 网格基础信息历史表-变化后快照
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:23:28
 */

@Table(name = "grid_basic_info_history_after")
@ApiModel(value = "GridBasicInfoHistoryAfter", description = "网格基础信息历史表-变化后快照")
public class GridBasicInfoHistoryAfter implements Serializable{
private static final long serialVersionUID=1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.UUIdGenId.class)
    @Column(name = "id")
    @ApiModelProperty("网格历史变更后主键")
    private String id;

    @Column(name = "grid_history_id")
    @ApiModelProperty("网格历史表ID")
    private String gridHistoryId;

    @Column(name = "grid_id")
    @ApiModelProperty("冗余网格主键ID")
    private String gridId;

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

    @Column(name = "area_code")
    @ApiModelProperty("冗余所属区域编码")
    private String areaCode;

    @Column(name = "area_name")
    @ApiModelProperty("冗余所属区域名称")
    private String areaName;

    @Column(name = "project_id")
    @ApiModelProperty("所属项目ID")
    private String projectId;

    @Column(name = "project_code")
    @ApiModelProperty("冗余所属项目编码")
    private String projectCode;

    @Column(name = "project_name")
    @ApiModelProperty("冗余所属项目名称")
    private String projectName;

    @Column(name = "massif_id")
    @ApiModelProperty("所属地块ID")
    private String massifId;

    @Column(name = "massif_code")
    @ApiModelProperty("冗余所属地块编码")
    private String massifCode;

    @Column(name = "massif_name")
    @ApiModelProperty("冗余所属地块名称")
    private String massifName;

    @Column(name = "staging_id")
    @ApiModelProperty("所属分期ID")
    private String stagingId;

    @Column(name = "staging_code")
    @ApiModelProperty("冗余所属分期编码")
    private String stagingCode;

    @Column(name = "staging_name")
    @ApiModelProperty("冗余所属分期名称")
    private String stagingName;

    @Column(name = "housekeeper_history_id")
    @ApiModelProperty("管家历史ID")
    private String housekeeperHistoryId;

    @Column(name = "format_attribute")
    @ApiModelProperty("业态属性：商办、住宅、公寓等")
    private String formatAttribute;

    @Column(name = "second_format_attribute")
    @ApiModelProperty("二级业态属性")
    private String secondFormatAttribute;

    @Column(name = "third_format_attribute")
    @ApiModelProperty("三级业态属性")
    private String thirdFormatAttribute;

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

    @Column(name = "city_id")
    @ApiModelProperty("所属城市ID")
    private String cityId;

    @Column(name = "city_code")
    @ApiModelProperty("冗余所属城市编码")
    private String cityCode;

    @Column(name = "city_name")
    @ApiModelProperty("冗余所属城市名称")
    private String cityName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGridHistoryId() {
        return gridHistoryId;
    }

    public void setGridHistoryId(String gridHistoryId) {
        this.gridHistoryId = gridHistoryId;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMassifId() {
        return massifId;
    }

    public void setMassifId(String massifId) {
        this.massifId = massifId;
    }

    public String getMassifCode() {
        return massifCode;
    }

    public void setMassifCode(String massifCode) {
        this.massifCode = massifCode;
    }

    public String getMassifName() {
        return massifName;
    }

    public void setMassifName(String massifName) {
        this.massifName = massifName;
    }

    public String getStagingId() {
        return stagingId;
    }

    public void setStagingId(String stagingId) {
        this.stagingId = stagingId;
    }

    public String getStagingCode() {
        return stagingCode;
    }

    public void setStagingCode(String stagingCode) {
        this.stagingCode = stagingCode;
    }

    public String getStagingName() {
        return stagingName;
    }

    public void setStagingName(String stagingName) {
        this.stagingName = stagingName;
    }

    public String getHousekeeperHistoryId() {
        return housekeeperHistoryId;
    }

    public void setHousekeeperHistoryId(String housekeeperHistoryId) {
        this.housekeeperHistoryId = housekeeperHistoryId;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSecondFormatAttribute() {
        return secondFormatAttribute;
    }

    public void setSecondFormatAttribute(String secondFormatAttribute) {
        this.secondFormatAttribute = secondFormatAttribute;
    }

    public String getThirdFormatAttribute() {
        return thirdFormatAttribute;
    }

    public void setThirdFormatAttribute(String thirdFormatAttribute) {
        this.thirdFormatAttribute = thirdFormatAttribute;
    }
}
