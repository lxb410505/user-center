package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 【基础信息】房产
 *
 * @author sunxinbo
 * @email 18306874483@sina.cn
 * @date 2019-01-08 10:49:00
 */

@Table(name = "house_bak")
@ApiModel(value = "House", description = "【基础信息】房产")
public class House implements Serializable{
private static final long serialVersionUID=1L;

        @Id
//        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "id")
    @ApiModelProperty("主键")
    private String id;

        @Column(name = "sf_guid")
    @ApiModelProperty("Salesforce GUID")
    private String sfGuid;

        @Column(name = "sap_id")
    @ApiModelProperty("SAPID")
    private String sapId;

        @Column(name = "wbs_code")
    @ApiModelProperty("WBS编码")
    private String wbsCode;

        @Column(name = "house_code")
    @ApiModelProperty("房号")
    private String houseCode;

        @Column(name = "house_inner_name")
    @ApiModelProperty("房产别名")
    private String houseInnerName;

        @Column(name = "is_invented")
    @ApiModelProperty("是否虚拟楼")
    private Integer isInvented;

        @Column(name = "floor")
    @ApiModelProperty("所属楼层")
    private Integer floor;

        @Column(name = "house_state")
    @ApiModelProperty("房产状态：（数据字典表） 已入住 空置 已售未收")
    private String houseState;

        @Column(name = "property_type")
    @ApiModelProperty("房产类型：（数据字典表） 住宅 商业")
    private String propertyType;

        @Column(name = "actual_address")
    @ApiModelProperty("actualAddress")
    private String actualAddress;

        @Column(name = "measured_area")
    @ApiModelProperty("实测面积(㎡)")
    private BigDecimal measuredArea;

        @Column(name = "predicted_area")
    @ApiModelProperty("预测面积(㎡)")
    private BigDecimal predictedArea;

        @Column(name = "building_area")
    @ApiModelProperty("建筑面积(㎡)")
    private BigDecimal buildingArea;

        @Column(name = "fee_area")
    @ApiModelProperty("收费面积(㎡)")
    private BigDecimal feeArea;

        @Column(name = "used_area")
    @ApiModelProperty("套内面积, 即可用面积(㎡)")
    private BigDecimal usedArea;

        @Column(name = "total_arrearage")
    @ApiModelProperty("累计欠费")
    private BigDecimal totalArrearage;

        @Column(name = "start_date")
    @ApiModelProperty("★计费开始日期")
    private Date startDate;

        @Column(name = "restore_original_date")
    @ApiModelProperty("恢复原价日期")
    private Date restoreOriginalDate;

        @Column(name = "remark")
    @ApiModelProperty("备注")
    private String remark;

        @Column(name = "reason")
    @ApiModelProperty("事由")
    private String reason;

        @Column(name = "unit_id")
    @ApiModelProperty("单元Id")
    private String unitId;

        @Column(name = "unit_code")
    @ApiModelProperty("【冗余】单元号")
    private String unitCode;

        @Column(name = "building_id")
    @ApiModelProperty("【冗余】楼栋Id")
    private String buildingId;

        @Column(name = "divide_id")
    @ApiModelProperty("分期Id")
    private String divideId;

        @Column(name = "project_id")
    @ApiModelProperty("【冗余】项目Id")
    private String projectId;

        @Column(name = "building_name")
    @ApiModelProperty("【冗余】楼栋名称")
    private String buildingName;

        @Column(name = "divide_name")
    @ApiModelProperty("【冗余】分期名称")
    private String divideName;

        @Column(name = "project_name")
    @ApiModelProperty("【冗余】项目名称")
    private String projectName;

        @Column(name = "contract_id")
    @ApiModelProperty("合同Id")
    private String contractId;

        @Column(name = "contract_flag")
    @ApiModelProperty("合同标识 1-属于合同 0-不属于合同 ")
    private Integer contractFlag;

        @Column(name = "corp_id")
    @ApiModelProperty("企业Id")
    private String corpId;

        @Column(name = "batch_id")
    @ApiModelProperty("批量导入批次号")
    private String batchId;

        @Column(name = "creation_date")
    @ApiModelProperty("首次创建时间")
    private Date creationDate;

        @Column(name = "created_by")
    @ApiModelProperty("创建人")
    private String createdBy;

        @Column(name = "updation_date")
    @ApiModelProperty("上次修改时间")
    private Date updationDate;

        @Column(name = "updated_by")
    @ApiModelProperty("更新人")
    private String updatedBy;

        @Column(name = "row_version")
    @ApiModelProperty("这条记录的版本号，每次更新操作都会使其发生变换")
    private Integer rowVersion;

        @Column(name = "is_deleted")
    @ApiModelProperty("删除标记")
    private Integer isDeleted;

        @Column(name = "enabled_flag")
    @ApiModelProperty("启用标记")
    private Integer enabledFlag;


    @Transient
    @ApiModelProperty("分期编码")
    private String divideCode;

    @Transient
    @ApiModelProperty("信息化平台分期Id")
    private String platDivideId;

    @Transient
    @ApiModelProperty("信息化平台分期名称")
    private String platDivideName;

    @Transient
    @ApiModelProperty("项目code")
    private String projectCode;

    @Transient
    @ApiModelProperty("信息化平台项目Id")
    private String platProjectId;

    @Transient
    @ApiModelProperty("信息化平台项目名称")
    private String platProjectName;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
        public void setSfGuid(String sfGuid) {
            this.sfGuid = sfGuid;
        }

        public String getSfGuid() {
            return this.sfGuid;
        }
        public void setSapId(String sapId) {
            this.sapId = sapId;
        }

        public String getSapId() {
            return this.sapId;
        }
        public void setWbsCode(String wbsCode) {
            this.wbsCode = wbsCode;
        }

        public String getWbsCode() {
            return this.wbsCode;
        }
        public void setHouseCode(String houseCode) {
            this.houseCode = houseCode;
        }

        public String getHouseCode() {
            return this.houseCode;
        }
        public void setHouseInnerName(String houseInnerName) {
            this.houseInnerName = houseInnerName;
        }

        public String getHouseInnerName() {
            return this.houseInnerName;
        }
        public void setIsInvented(Integer isInvented) {
            this.isInvented = isInvented;
        }

        public Integer getIsInvented() {
            return this.isInvented;
        }
        public void setFloor(Integer floor) {
            this.floor = floor;
        }

        public Integer getFloor() {
            return this.floor;
        }
        public void setHouseState(String houseState) {
            this.houseState = houseState;
        }

        public String getHouseState() {
            return this.houseState;
        }
        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public String getPropertyType() {
            return this.propertyType;
        }
        public void setActualAddress(String actualAddress) {
            this.actualAddress = actualAddress;
        }

        public String getActualAddress() {
            return this.actualAddress;
        }
        public void setMeasuredArea(BigDecimal measuredArea) {
            this.measuredArea = measuredArea;
        }

        public BigDecimal getMeasuredArea() {
            return this.measuredArea;
        }
        public void setPredictedArea(BigDecimal predictedArea) {
            this.predictedArea = predictedArea;
        }

        public BigDecimal getPredictedArea() {
            return this.predictedArea;
        }
        public void setBuildingArea(BigDecimal buildingArea) {
            this.buildingArea = buildingArea;
        }

        public BigDecimal getBuildingArea() {
            return this.buildingArea;
        }
        public void setFeeArea(BigDecimal feeArea) {
            this.feeArea = feeArea;
        }

        public BigDecimal getFeeArea() {
            return this.feeArea;
        }
        public void setUsedArea(BigDecimal usedArea) {
            this.usedArea = usedArea;
        }

        public BigDecimal getUsedArea() {
            return this.usedArea;
        }
        public void setTotalArrearage(BigDecimal totalArrearage) {
            this.totalArrearage = totalArrearage;
        }

        public BigDecimal getTotalArrearage() {
            return this.totalArrearage;
        }
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getStartDate() {
            return this.startDate;
        }
        public void setRestoreOriginalDate(Date restoreOriginalDate) {
            this.restoreOriginalDate = restoreOriginalDate;
        }

        public Date getRestoreOriginalDate() {
            return this.restoreOriginalDate;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return this.remark;
        }
        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return this.reason;
        }
        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getUnitId() {
            return this.unitId;
        }
        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }

        public String getUnitCode() {
            return this.unitCode;
        }
        public void setBuildingId(String buildingId) {
            this.buildingId = buildingId;
        }

        public String getBuildingId() {
            return this.buildingId;
        }
        public void setDivideId(String divideId) {
            this.divideId = divideId;
        }

        public String getDivideId() {
            return this.divideId;
        }
        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getProjectId() {
            return this.projectId;
        }
        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getBuildingName() {
            return this.buildingName;
        }
        public void setDivideName(String divideName) {
            this.divideName = divideName;
        }

        public String getDivideName() {
            return this.divideName;
        }
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectName() {
            return this.projectName;
        }
        public void setContractId(String contractId) {
            this.contractId = contractId;
        }

        public String getContractId() {
            return this.contractId;
        }
        public void setContractFlag(Integer contractFlag) {
            this.contractFlag = contractFlag;
        }

        public Integer getContractFlag() {
            return this.contractFlag;
        }
        public void setCorpId(String corpId) {
            this.corpId = corpId;
        }

        public String getCorpId() {
            return this.corpId;
        }
        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public String getBatchId() {
            return this.batchId;
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
        public void setRowVersion(Integer rowVersion) {
            this.rowVersion = rowVersion;
        }

        public Integer getRowVersion() {
            return this.rowVersion;
        }
        public void setIsDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Integer getIsDeleted() {
            return this.isDeleted;
        }
        public void setEnabledFlag(Integer enabledFlag) {
            this.enabledFlag = enabledFlag;
        }

        public Integer getEnabledFlag() {
            return this.enabledFlag;
        }

    public String getPlatDivideId() {
        return platDivideId;
    }

    public void setPlatDivideId(String platDivideId) {
        this.platDivideId = platDivideId;
    }

    public String getPlatDivideName() {
        return platDivideName;
    }

    public void setPlatDivideName(String platDivideName) {
        this.platDivideName = platDivideName;
    }

    public String getPlatProjectId() {
        return platProjectId;
    }

    public void setPlatProjectId(String platProjectId) {
        this.platProjectId = platProjectId;
    }

    public String getPlatProjectName() {
        return platProjectName;
    }

    public void setPlatProjectName(String platProjectName) {
        this.platProjectName = platProjectName;
    }

    public String getDivideCode() {
        return divideCode;
    }

    public void setDivideCode(String divideCode) {
        this.divideCode = divideCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("sfGuid",this.sfGuid)
                .append("sapId",this.sapId)
                .append("wbsCode",this.wbsCode)
                .append("houseCode",this.houseCode)
                .append("houseInnerName",this.houseInnerName)
                .append("isInvented",this.isInvented)
                .append("floor",this.floor)
                .append("houseState",this.houseState)
                .append("propertyType",this.propertyType)
                .append("actualAddress",this.actualAddress)
                .append("measuredArea",this.measuredArea)
                .append("predictedArea",this.predictedArea)
                .append("buildingArea",this.buildingArea)
                .append("feeArea",this.feeArea)
                .append("usedArea",this.usedArea)
                .append("totalArrearage",this.totalArrearage)
                .append("startDate",this.startDate)
                .append("restoreOriginalDate",this.restoreOriginalDate)
                .append("remark",this.remark)
                .append("reason",this.reason)
                .append("unitId",this.unitId)
                .append("unitCode",this.unitCode)
                .append("buildingId",this.buildingId)
                .append("divideId",this.divideId)
                .append("projectId",this.projectId)
                .append("buildingName",this.buildingName)
                .append("divideName",this.divideName)
                .append("projectName",this.projectName)
                .append("contractId",this.contractId)
                .append("contractFlag",this.contractFlag)
                .append("corpId",this.corpId)
                .append("batchId",this.batchId)
                .append("creationDate",this.creationDate)
                .append("createdBy",this.createdBy)
                .append("updationDate",this.updationDate)
                .append("updatedBy",this.updatedBy)
                .append("rowVersion",this.rowVersion)
                .append("isDeleted",this.isDeleted)
                .append("enabledFlag",this.enabledFlag)
            .toString();
    }
}
