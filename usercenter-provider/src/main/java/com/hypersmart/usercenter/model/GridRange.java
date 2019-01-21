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
 * 网格覆盖范围表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-17 12:48:37
 */

@Table(name = "grid_range")
@ApiModel(value = "GridRange", description = "网格覆盖范围表")
public class GridRange implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "id")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "grid_id")
    @ApiModelProperty("网格ID")
        private String gridId;

        @Column(name = "resource_id")
    @ApiModelProperty("资源范围的ID（楼栋ID or 单元ID or 楼层ID or 房产ID）")
        private String resourceId;

        @Column(name = "range_type")
    @ApiModelProperty("范围类型（数据字典）：楼栋、单元、楼层、房产")
        private String rangeType;

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


        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
        public void setGridId(String gridId) {
            this.gridId = gridId;
        }

        public String getGridId() {
            return this.gridId;
        }
        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getResourceId() {
            return this.resourceId;
        }
        public void setRangeType(String rangeType) {
            this.rangeType = rangeType;
        }

        public String getRangeType() {
            return this.rangeType;
        }
        public void setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }

        public Date getCreationDate() {
            return this.creationDate;
        }
        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedBy() {
            return this.createdBy;
        }
        public void setUpdationDate(Date updationDate) {
            this.updationDate = updationDate;
        }

        public Date getUpdationDate() {
            return this.updationDate;
        }
        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedBy() {
            return this.updatedBy;
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
        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getTenantId() {
            return this.tenantId;
        }
        public void setEnabledFlag(Integer enabledFlag) {
            this.enabledFlag = enabledFlag;
        }

        public Integer getEnabledFlag() {
            return this.enabledFlag;
        }
        public void setIsDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Integer getIsDeleted() {
            return this.isDeleted;
        }
    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("gridId",this.gridId)
                .append("resourceId",this.resourceId)
                .append("rangeType",this.rangeType)
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
