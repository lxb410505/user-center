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

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * 
 *
 * @author Magellan
 * @email Magellan
 * @date 2019-05-21 16:18:32
 */

@Table(name = "quality_check")
@ApiModel(value = "QualityCheck", description = "")
public class QualityCheck implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "id")
    @ApiModelProperty("id")
        private String id;

        @Column(name = "area_")
    @ApiModelProperty("area")
        private String area;

        @Column(name = "area_code")
    @ApiModelProperty("areaCode")
        private String areaCode;

        @Column(name = "project_")
    @ApiModelProperty("project")
        private String project;

        @Column(name = "project_code")
    @ApiModelProperty("projectCode")
        private String projectCode;

        @Column(name = "massif_")
    @ApiModelProperty("massif")
        private String massif;

        @Column(name = "massif_code")
    @ApiModelProperty("massifCode")
        private String massifCode;

        @Column(name = "storied_grid")
    @ApiModelProperty("楼栋网格")
        private BigDecimal storiedGrid;

        @Column(name = "public_area_grid")
    @ApiModelProperty("公区网格")
        private BigDecimal publicAreaGrid;

        @Column(name = "service_center_grid")
    @ApiModelProperty("服务中心网格")
        private BigDecimal serviceCenterGrid;

        @Column(name = "order_service_unit")
    @ApiModelProperty("秩序服务单元")
        private BigDecimal orderServiceUnit;

        @Column(name = "esu_comprehensive_score")
    @ApiModelProperty("环境服务单元综合得分")
        private BigDecimal esuComprehensiveScore;

        @Column(name = "esu_cleaning")
    @ApiModelProperty("环境服务单元-保洁")
        private BigDecimal esuCleaning;

        @Column(name = "esu_green")
    @ApiModelProperty("环境服务单元-绿化")
        private BigDecimal esuGreen;

        @Column(name = "psu_operation_and_maintenance")
    @ApiModelProperty("工程服务单元-运维")
        private BigDecimal psuOperationAndMaintenance;

        @Column(name = "psu_facilities")
    @ApiModelProperty("工程服务单元-设施设备")
        private BigDecimal psuFacilities;

        @Column(name = "first_grade_red_line")
    @ApiModelProperty("一级红线")
        private BigDecimal firstGradeRedLine;

        @Column(name = "two_level_strong_control")
    @ApiModelProperty("二级强控")
        private BigDecimal twoLevelStrongControl;


        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
        public void setArea(String area) {
            this.area = area;
        }

        public String getArea() {
            return this.area;
        }
        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaCode() {
            return this.areaCode;
        }
        public void setProject(String project) {
            this.project = project;
        }

        public String getProject() {
            return this.project;
        }
        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public String getProjectCode() {
            return this.projectCode;
        }
        public void setMassif(String massif) {
            this.massif = massif;
        }

        public String getMassif() {
            return this.massif;
        }
        public void setMassifCode(String massifCode) {
            this.massifCode = massifCode;
        }

        public String getMassifCode() {
            return this.massifCode;
        }
        public void setStoriedGrid(BigDecimal storiedGrid) {
            this.storiedGrid = storiedGrid;
        }

        public BigDecimal getStoriedGrid() {
            return this.storiedGrid;
        }
        public void setPublicAreaGrid(BigDecimal publicAreaGrid) {
            this.publicAreaGrid = publicAreaGrid;
        }

        public BigDecimal getPublicAreaGrid() {
            return this.publicAreaGrid;
        }
        public void setServiceCenterGrid(BigDecimal serviceCenterGrid) {
            this.serviceCenterGrid = serviceCenterGrid;
        }

        public BigDecimal getServiceCenterGrid() {
            return this.serviceCenterGrid;
        }
        public void setOrderServiceUnit(BigDecimal orderServiceUnit) {
            this.orderServiceUnit = orderServiceUnit;
        }

        public BigDecimal getOrderServiceUnit() {
            return this.orderServiceUnit;
        }
        public void setEsuComprehensiveScore(BigDecimal esuComprehensiveScore) {
            this.esuComprehensiveScore = esuComprehensiveScore;
        }

        public BigDecimal getEsuComprehensiveScore() {
            return this.esuComprehensiveScore;
        }
        public void setEsuCleaning(BigDecimal esuCleaning) {
            this.esuCleaning = esuCleaning;
        }

        public BigDecimal getEsuCleaning() {
            return this.esuCleaning;
        }
        public void setEsuGreen(BigDecimal esuGreen) {
            this.esuGreen = esuGreen;
        }

        public BigDecimal getEsuGreen() {
            return this.esuGreen;
        }
        public void setPsuOperationAndMaintenance(BigDecimal psuOperationAndMaintenance) {
            this.psuOperationAndMaintenance = psuOperationAndMaintenance;
        }

        public BigDecimal getPsuOperationAndMaintenance() {
            return this.psuOperationAndMaintenance;
        }
        public void setPsuFacilities(BigDecimal psuFacilities) {
            this.psuFacilities = psuFacilities;
        }

        public BigDecimal getPsuFacilities() {
            return this.psuFacilities;
        }
        public void setFirstGradeRedLine(BigDecimal firstGradeRedLine) {
            this.firstGradeRedLine = firstGradeRedLine;
        }

        public BigDecimal getFirstGradeRedLine() {
            return this.firstGradeRedLine;
        }
        public void setTwoLevelStrongControl(BigDecimal twoLevelStrongControl) {
            this.twoLevelStrongControl = twoLevelStrongControl;
        }

        public BigDecimal getTwoLevelStrongControl() {
            return this.twoLevelStrongControl;
        }
    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("area",this.area)
                .append("areaCode",this.areaCode)
                .append("project",this.project)
                .append("projectCode",this.projectCode)
                .append("massif",this.massif)
                .append("massifCode",this.massifCode)
                .append("storiedGrid",this.storiedGrid)
                .append("publicAreaGrid",this.publicAreaGrid)
                .append("serviceCenterGrid",this.serviceCenterGrid)
                .append("orderServiceUnit",this.orderServiceUnit)
                .append("esuComprehensiveScore",this.esuComprehensiveScore)
                .append("esuCleaning",this.esuCleaning)
                .append("esuGreen",this.esuGreen)
                .append("psuOperationAndMaintenance",this.psuOperationAndMaintenance)
                .append("psuFacilities",this.psuFacilities)
                .append("firstGradeRedLine",this.firstGradeRedLine)
                .append("twoLevelStrongControl",this.twoLevelStrongControl)
            .toString();
    }
}
