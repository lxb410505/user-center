package com.hypersmart.usercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 重大事件质量
 *
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:07:53
 */

@Table(name = "tge_significant_quality")
@ApiModel(value = "TgeSignificantQuality", description = "重大事件质量")
public class TgeSignificantQuality implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "id")
    @ApiModelProperty("id")
        private String id;

        @Column(name = "region_id")
    @ApiModelProperty("区域ID")
        private String regionId;

        @Column(name = "region_name")
    @ApiModelProperty("区域名称")
        private String regionName;

        @Column(name = "area_id")
    @ApiModelProperty("片区ID")
        private String areaId;

        @Column(name = "area_name")
    @ApiModelProperty("片区名称/城市公司")
        private String areaName;

        @Column(name = "project_id")
    @ApiModelProperty("项目ID")
        private String projectId;

        @Column(name = "project_name")
    @ApiModelProperty("项目名称")
        private String projectName;

        @Column(name = "divide_id")
    @ApiModelProperty("地块ID")
        private String divideId;

        @Column(name = "divide_name")
    @ApiModelProperty("地块名称")
        private String divideName;

        @Column(name = "order_service_unit_score")
    @ApiModelProperty("秩序服务单元")
        private Integer orderServiceUnitScore;

        @Column(name = "environmental_service_unit_score")
    @ApiModelProperty("环境服务单元")
        private Integer environmentalServiceUnitScore;

        @Column(name = "engineering_service_unit_score")
    @ApiModelProperty("工程服务单元")
        private Integer engineeringServiceUnitScore;

        @Column(name = "create_by")
    @ApiModelProperty("创建人")
        private String createBy;

        @Column(name = "create_time")
    @ApiModelProperty("创建时间")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date createTime;

        @Column(name = "operation_org_id")
    @ApiModelProperty("操作人组织ID")
        private String operationOrgId;

        @Column(name = "month")
    @ApiModelProperty("年月")
        private String month;


        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getRegionId() {
            return this.regionId;
        }
        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getRegionName() {
            return this.regionName;
        }
        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaId() {
            return this.areaId;
        }
        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAreaName() {
            return this.areaName;
        }
        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getProjectId() {
            return this.projectId;
        }
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectName() {
            return this.projectName;
        }
        public void setDivideId(String divideId) {
            this.divideId = divideId;
        }

        public String getDivideId() {
            return this.divideId;
        }
        public void setDivideName(String divideName) {
            this.divideName = divideName;
        }

        public String getDivideName() {
            return this.divideName;
        }
        public void setOrderServiceUnitScore(Integer orderServiceUnitScore) {
            this.orderServiceUnitScore = orderServiceUnitScore;
        }

        public Integer getOrderServiceUnitScore() {
            return this.orderServiceUnitScore;
        }
        public void setEnvironmentalServiceUnitScore(Integer environmentalServiceUnitScore) {
            this.environmentalServiceUnitScore = environmentalServiceUnitScore;
        }

        public Integer getEnvironmentalServiceUnitScore() {
            return this.environmentalServiceUnitScore;
        }
        public void setEngineeringServiceUnitScore(Integer engineeringServiceUnitScore) {
            this.engineeringServiceUnitScore = engineeringServiceUnitScore;
        }

        public Integer getEngineeringServiceUnitScore() {
            return this.engineeringServiceUnitScore;
        }
        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateBy() {
            return this.createBy;
        }
        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getCreateTime() {
            return this.createTime;
        }
        public void setOperationOrgId(String operationOrgId) {
            this.operationOrgId = operationOrgId;
        }

        public String getOperationOrgId() {
            return this.operationOrgId;
        }
        public void setMonth(String month) {
            this.month = month;
        }

        public String getMonth() {
            return this.month;
        }
    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("regionId",this.regionId)
                .append("regionName",this.regionName)
                .append("areaId",this.areaId)
                .append("areaName",this.areaName)
                .append("projectId",this.projectId)
                .append("projectName",this.projectName)
                .append("divideId",this.divideId)
                .append("divideName",this.divideName)
                .append("orderServiceUnitScore",this.orderServiceUnitScore)
                .append("environmentalServiceUnitScore",this.environmentalServiceUnitScore)
                .append("engineeringServiceUnitScore",this.engineeringServiceUnitScore)
                .append("createBy",this.createBy)
                .append("createTime",this.createTime)
                .append("operationOrgId",this.operationOrgId)
                .append("month",this.month)
            .toString();
    }
}
