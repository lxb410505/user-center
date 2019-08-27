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
 * 【基础信息】房产
 *
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */

@Table(name = "house")
@ApiModel(value = "House", description = "【基础信息】房产")
public class House implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
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
    @ApiModelProperty("房产类型：（数据字典表）住宅 商业")
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

    @Column(name = "public_booth_area")
    @ApiModelProperty("公摊面积(㎡)")
    private BigDecimal publicBoothArea;

    @Column(name = "total_arrearage")
    @ApiModelProperty("累计欠费")
    private BigDecimal totalArrearage;

    @Column(name = "start_date")
    @ApiModelProperty("★计费开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @Column(name = "restore_original_date")
    @ApiModelProperty("恢复原价日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @Column(name = "area_name")
    @ApiModelProperty("【冗余】区域名称")
    private String areaName;

    @Column(name = "area_code")
    @ApiModelProperty("【冗余】区域code")
    private String areaCode;

    @Column(name = "area_id")
    @ApiModelProperty("【冗余】区域id")
    private String areaId;

    @Column(name = "city_id")
    @ApiModelProperty("【冗余】城区id")
    private String cityId;

    @Column(name = "city_name")
    @ApiModelProperty("【冗余】城区名称")
    private String cityName;

    @Column(name = "city_code")
    @ApiModelProperty("【冗余】城区code")
    private String cityCode;

    @Column(name = "project_id")
    @ApiModelProperty("【冗余】项目Id")
    private String projectId;

    @Column(name = "building_name")
    @ApiModelProperty("【冗余】楼栋名称")
    private String buildingName;

    @Column(name = "divide_code")
    @ApiModelProperty("【冗余】分期code")
    private String divideCode;

    @Column(name = "divide_name")
    @ApiModelProperty("【冗余】分期名称")
    private String divideName;

    @Column(name = "project_code")
    @ApiModelProperty("【冗余】项目code")
    private String projectCode;

    @Column(name = "project_name")
    @ApiModelProperty("【冗余】项目名称")
    private String projectName;

    @Column(name = "uc_member_id")
    @ApiModelProperty("【冗余】产权人id，逗号隔开")
    private String ucMemberId;
    @Column(name = "uc_member_name")
    @ApiModelProperty("【冗余】产权人名称，逗号隔开")
    private String ucMemberName;
    @Column(name = "uc_member_mobile")
    @ApiModelProperty("【冗余】产权人手机号，逗号隔开")
    private String ucMemberMobile;
    @Column(name = "uc_member_telphone")
    @ApiModelProperty("【冗余】产权人固话，逗号隔开")
    private String ucMemberTelPhone;
    @Column(name = "is_own_staff")
    @ApiModelProperty("【冗余】是否是内部员工，逗号隔开")
    private String isOwnStaff;

    @Column(name = "contract_id")
    @ApiModelProperty("合同Id")
    private String contractId;

    @Column(name = "contract_flag")
    @ApiModelProperty("合同标识 1-属于合同 0-不属于合同")
    private Integer contractFlag;

    @Column(name = "debit_enabled_flag")
    @ApiModelProperty("自动预收抵扣标志 1启用 0禁用")
    private Integer debitEnabledFlag;

    @Column(name = "corp_id")
    @ApiModelProperty("企业Id")
    private String corpId;

    @Column(name = "batch_id")
    @ApiModelProperty("批量导入批次号")
    private String batchId;

    @Column(name = "property_fee")
    @ApiModelProperty("物业费")
    private BigDecimal propertyFee;

    @Column(name = "other_fee")
    @ApiModelProperty("其他费用")
    private BigDecimal otherFee;

    @Column(name = "delivery_date")
    @ApiModelProperty("集中交付日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deliveryDate;

    @Column(name = "real_delivery_date")
    @ApiModelProperty("实际交付日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date realDeliveryDate;

    @Column(name = "access_card_number")
    @ApiModelProperty("门禁卡号")
    private String accessCardNumber;

    @Column(name = "purpose")
    @ApiModelProperty("用途")
    private String purpose;

    @Column(name = "renovation_type")
    @ApiModelProperty("装修类型")
    private String renovationType;

    @Column(name = "source_from")
    @ApiModelProperty("数据来源")
    private String sourceFrom;

    @Column(name = "emergency_contact_1")
    @ApiModelProperty("紧急联系人1")
    private String emergencyContact1;

    @Column(name = "emergency_contact_phone_1")
    @ApiModelProperty("紧急联系人电话1")
    private String emergencyContactPhone1;

    @Column(name = "emergency_contact_2")
    @ApiModelProperty("紧急联系人2")
    private String emergencyContact2;

    @Column(name = "emergency_contact_phone_2")
    @ApiModelProperty("紧急联系人电话2")
    private String emergencyContactPhone2;

    @Column(name = "emergency_contact_3")
    @ApiModelProperty("紧急联系人3")
    private String emergencyContact3;

    @Column(name = "emergency_contact_phone_3")
    @ApiModelProperty("紧急联系人电话3")
    private String emergencyContactPhone3;

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

    @Column(name = "house_use")
    @ApiModelProperty("房屋用途，关联字典表")
    private String houseUse;

    @Column(name = "connect_address")
    @ApiModelProperty("connectAddress")
    private String connectAddress;

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

    public void setPublicBoothArea(BigDecimal publicBoothArea) {
        this.publicBoothArea = publicBoothArea;
    }

    public BigDecimal getPublicBoothArea() {
        return this.publicBoothArea;
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

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return this.cityCode;
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

    public void setDivideCode(String divideCode) {
        this.divideCode = divideCode;
    }

    public String getDivideCode() {
        return this.divideCode;
    }

    public void setDivideName(String divideName) {
        this.divideName = divideName;
    }

    public String getDivideName() {
        return this.divideName;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getUcMemberId() {
        return ucMemberId;
    }

    public void setUcMemberId(String ucMemberId) {
        this.ucMemberId = ucMemberId;
    }

    public String getUcMemberName() {
        return ucMemberName;
    }

    public void setUcMemberName(String ucMemberName) {
        this.ucMemberName = ucMemberName;
    }

    public String getUcMemberMobile() {
        return ucMemberMobile;
    }

    public void setUcMemberMobile(String ucMemberMobile) {
        this.ucMemberMobile = ucMemberMobile;
    }

    public String getUcMemberTelPhone() {
        return ucMemberTelPhone;
    }

    public void setUcMemberTelPhone(String ucMemberTelPhone) {
        this.ucMemberTelPhone = ucMemberTelPhone;
    }

    public String getIsOwnStaff() {
        return isOwnStaff;
    }

    public void setIsOwnStaff(String isOwnStaff) {
        this.isOwnStaff = isOwnStaff;
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

    public void setDebitEnabledFlag(Integer debitEnabledFlag) {
        this.debitEnabledFlag = debitEnabledFlag;
    }

    public Integer getDebitEnabledFlag() {
        return this.debitEnabledFlag;
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

    public void setPropertyFee(BigDecimal propertyFee) {
        this.propertyFee = propertyFee;
    }

    public BigDecimal getPropertyFee() {
        return this.propertyFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getOtherFee() {
        return this.otherFee;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setRealDeliveryDate(Date realDeliveryDate) {
        this.realDeliveryDate = realDeliveryDate;
    }

    public Date getRealDeliveryDate() {
        return this.realDeliveryDate;
    }

    public void setAccessCardNumber(String accessCardNumber) {
        this.accessCardNumber = accessCardNumber;
    }

    public String getAccessCardNumber() {
        return this.accessCardNumber;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setRenovationType(String renovationType) {
        this.renovationType = renovationType;
    }

    public String getRenovationType() {
        return this.renovationType;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public String getSourceFrom() {
        return this.sourceFrom;
    }

    public void setEmergencyContact1(String emergencyContact1) {
        this.emergencyContact1 = emergencyContact1;
    }

    public String getEmergencyContact1() {
        return this.emergencyContact1;
    }

    public void setEmergencyContactPhone1(String emergencyContactPhone1) {
        this.emergencyContactPhone1 = emergencyContactPhone1;
    }

    public String getEmergencyContactPhone1() {
        return this.emergencyContactPhone1;
    }

    public void setEmergencyContact2(String emergencyContact2) {
        this.emergencyContact2 = emergencyContact2;
    }

    public String getEmergencyContact2() {
        return this.emergencyContact2;
    }

    public void setEmergencyContactPhone2(String emergencyContactPhone2) {
        this.emergencyContactPhone2 = emergencyContactPhone2;
    }

    public String getEmergencyContactPhone2() {
        return this.emergencyContactPhone2;
    }

    public void setEmergencyContact3(String emergencyContact3) {
        this.emergencyContact3 = emergencyContact3;
    }

    public String getEmergencyContact3() {
        return this.emergencyContact3;
    }

    public void setEmergencyContactPhone3(String emergencyContactPhone3) {
        this.emergencyContactPhone3 = emergencyContactPhone3;
    }

    public String getEmergencyContactPhone3() {
        return this.emergencyContactPhone3;
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

    public void setHouseUse(String houseUse) {
        this.houseUse = houseUse;
    }

    public String getHouseUse() {
        return this.houseUse;
    }

    public void setConnectAddress(String connectAddress) {
        this.connectAddress = connectAddress;
    }

    public String getConnectAddress() {
        return this.connectAddress;
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

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", sfGuid='" + sfGuid + '\'' +
                ", sapId='" + sapId + '\'' +
                ", wbsCode='" + wbsCode + '\'' +
                ", houseCode='" + houseCode + '\'' +
                ", houseInnerName='" + houseInnerName + '\'' +
                ", isInvented=" + isInvented +
                ", floor=" + floor +
                ", houseState='" + houseState + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", actualAddress='" + actualAddress + '\'' +
                ", measuredArea=" + measuredArea +
                ", predictedArea=" + predictedArea +
                ", buildingArea=" + buildingArea +
                ", feeArea=" + feeArea +
                ", usedArea=" + usedArea +
                ", publicBoothArea=" + publicBoothArea +
                ", totalArrearage=" + totalArrearage +
                ", startDate=" + startDate +
                ", restoreOriginalDate=" + restoreOriginalDate +
                ", remark='" + remark + '\'' +
                ", reason='" + reason + '\'' +
                ", unitId='" + unitId + '\'' +
                ", unitCode='" + unitCode + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", divideId='" + divideId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", areaId='" + areaId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", projectId='" + projectId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", divideCode='" + divideCode + '\'' +
                ", divideName='" + divideName + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", ucMemberId='" + ucMemberId + '\'' +
                ", ucMemberName='" + ucMemberName + '\'' +
                ", ucMemberMobile='" + ucMemberMobile + '\'' +
                ", ucMemberTelPhone='" + ucMemberTelPhone + '\'' +
                ", contractId='" + contractId + '\'' +
                ", contractFlag=" + contractFlag +
                ", debitEnabledFlag=" + debitEnabledFlag +
                ", corpId='" + corpId + '\'' +
                ", batchId='" + batchId + '\'' +
                ", propertyFee=" + propertyFee +
                ", otherFee=" + otherFee +
                ", deliveryDate=" + deliveryDate +
                ", realDeliveryDate=" + realDeliveryDate +
                ", accessCardNumber='" + accessCardNumber + '\'' +
                ", purpose='" + purpose + '\'' +
                ", renovationType='" + renovationType + '\'' +
                ", sourceFrom='" + sourceFrom + '\'' +
                ", emergencyContact1='" + emergencyContact1 + '\'' +
                ", emergencyContactPhone1='" + emergencyContactPhone1 + '\'' +
                ", emergencyContact2='" + emergencyContact2 + '\'' +
                ", emergencyContactPhone2='" + emergencyContactPhone2 + '\'' +
                ", emergencyContact3='" + emergencyContact3 + '\'' +
                ", emergencyContactPhone3='" + emergencyContactPhone3 + '\'' +
                ", creationDate=" + creationDate +
                ", createdBy='" + createdBy + '\'' +
                ", updationDate=" + updationDate +
                ", houseUse='" + houseUse + '\'' +
                ", connectAddress='" + connectAddress + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", rowVersion=" + rowVersion +
                ", isDeleted=" + isDeleted +
                ", enabledFlag=" + enabledFlag +
                '}';
    }
}
