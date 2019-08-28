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
 * 【基础信息】停车位
 *
 * @author zcf
 * @email 1490318946@qq.com
 * @date 2019-08-21 16:19:42
 */

@Table(name = "parking")
@ApiModel(value = "Parking", description = "【基础信息】停车位")
public class Parking implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "id")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "house_id")
    @ApiModelProperty("房产id")
    private String houseId;

    @Column(name = "sf_guid")
    @ApiModelProperty("Salesforce GUID")
    private String sfGuid;

    @Column(name = "garage_id")
    @ApiModelProperty("车库id")
    private String garageId;

    @Column(name = "divide_id")
    @ApiModelProperty("分期Id")
    private String divideId;

    @Column(name = "rental_property_num")
    @ApiModelProperty("租赁产权车位号")
    private String rentalPropertyNum;

    @Column(name = "divide_code")
    @ApiModelProperty("分期code")
    private String divideCode;

    @Column(name = "parking_num")
    @ApiModelProperty("车位编号，项目分期内唯一")
    private String parkingNum;

    @Column(name = "parking_name")
    @ApiModelProperty("车位名称")
    private String parkingName;

    @Column(name = "plate_number")
    @ApiModelProperty("车牌号")
    private String plateNumber;

    @Column(name = "car_brands")
    @ApiModelProperty("车辆品牌")
    private String carBrands;

    @Column(name = "car_color")
    @ApiModelProperty("车辆颜色")
    private String carColor;

    @Column(name = "card_number")
    @ApiModelProperty("卡号")
    private String cardNumber;

    @Column(name = "fee")
    @ApiModelProperty("费用")
    private BigDecimal fee;

    @Column(name = "lease_term_begin")
    @ApiModelProperty("租赁开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaseTermBegin;

    @Column(name = "lease_term_end")
    @ApiModelProperty("租赁结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaseTermEnd;

    @Column(name = "area")
    @ApiModelProperty("面积")
    private Double area;

    @Column(name = "parking_type")
    @ApiModelProperty("车位类型:（数据字典表） 出租 临停 业主自有")
    private String parkingType;

    @Column(name = "parking_floor")
    @ApiModelProperty("所属层")
    private Integer parkingFloor;

    @Column(name = "parking_property_right")
    @ApiModelProperty("车位产权属性：（数据字典表） 专有产权-业主 专有产权-开发商 共有产权 人防车位")
    private String parkingPropertyRight;

    @Column(name = "parking_property")
    @ApiModelProperty("车位属性：（数据字典表） 机械车位 平面车位")
    private String parkingProperty;

    @Column(name = "corp_id")
    @ApiModelProperty("企业Id")
    private String corpId;

    @Column(name = "batch_id")
    @ApiModelProperty("批量导入批次号")
    private String batchId;

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

    @Column(name = "row_version")
    @ApiModelProperty("这条记录的版本号，每次更新操作都会使其发生变换")
    private Integer rowVersion;

    @Column(name = "is_deleted")
    @ApiModelProperty("删除标记")
    private Integer isDeleted;

    @Column(name = "enabled_flag")
    @ApiModelProperty("启用标记")
    private Integer enabledFlag;

    @Column(name = "remark")
    @ApiModelProperty("备注")
    private String remark;

    @Column(name = "source_from")
    @ApiModelProperty("数据来源")
    private String sourceFrom;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return this.houseId;
    }

    public void setSfGuid(String sfGuid) {
        this.sfGuid = sfGuid;
    }

    public String getSfGuid() {
        return this.sfGuid;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

    public String getGarageId() {
        return this.garageId;
    }

    public void setDivideId(String divideId) {
        this.divideId = divideId;
    }

    public String getDivideId() {
        return this.divideId;
    }

    public void setRentalPropertyNum(String rentalPropertyNum) {
        this.rentalPropertyNum = rentalPropertyNum;
    }

    public String getRentalPropertyNum() {
        return this.rentalPropertyNum;
    }

    public void setDivideCode(String divideCode) {
        this.divideCode = divideCode;
    }

    public String getDivideCode() {
        return this.divideCode;
    }

    public void setParkingNum(String parkingNum) {
        this.parkingNum = parkingNum;
    }

    public String getParkingNum() {
        return this.parkingNum;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingName() {
        return this.parkingName;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return this.plateNumber;
    }

    public void setCarBrands(String carBrands) {
        this.carBrands = carBrands;
    }

    public String getCarBrands() {
        return this.carBrands;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarColor() {
        return this.carColor;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setLeaseTermBegin(Date leaseTermBegin) {
        this.leaseTermBegin = leaseTermBegin;
    }

    public Date getLeaseTermBegin() {
        return this.leaseTermBegin;
    }

    public void setLeaseTermEnd(Date leaseTermEnd) {
        this.leaseTermEnd = leaseTermEnd;
    }

    public Date getLeaseTermEnd() {
        return this.leaseTermEnd;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getArea() {
        return this.area;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public String getParkingType() {
        return this.parkingType;
    }

    public void setParkingFloor(Integer parkingFloor) {
        this.parkingFloor = parkingFloor;
    }

    public Integer getParkingFloor() {
        return this.parkingFloor;
    }

    public void setParkingPropertyRight(String parkingPropertyRight) {
        this.parkingPropertyRight = parkingPropertyRight;
    }

    public String getParkingPropertyRight() {
        return this.parkingPropertyRight;
    }

    public void setParkingProperty(String parkingProperty) {
        this.parkingProperty = parkingProperty;
    }

    public String getParkingProperty() {
        return this.parkingProperty;
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

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public String getSourceFrom() {
        return this.sourceFrom;
    }

    public String toString() {
        return
                new ToStringBuilder(this)
                        .append("id", this.id)
                        .append("houseId", this.houseId)
                        .append("sfGuid", this.sfGuid)
                        .append("garageId", this.garageId)
                        .append("divideId", this.divideId)
                        .append("rentalPropertyNum", this.rentalPropertyNum)
                        .append("divideCode", this.divideCode)
                        .append("parkingNum", this.parkingNum)
                        .append("parkingName", this.parkingName)
                        .append("plateNumber", this.plateNumber)
                        .append("carBrands", this.carBrands)
                        .append("carColor", this.carColor)
                        .append("cardNumber", this.cardNumber)
                        .append("fee", this.fee)
                        .append("leaseTermBegin", this.leaseTermBegin)
                        .append("leaseTermEnd", this.leaseTermEnd)
                        .append("area", this.area)
                        .append("parkingType", this.parkingType)
                        .append("parkingFloor", this.parkingFloor)
                        .append("parkingPropertyRight", this.parkingPropertyRight)
                        .append("parkingProperty", this.parkingProperty)
                        .append("corpId", this.corpId)
                        .append("batchId", this.batchId)
                        .append("creationDate", this.creationDate)
                        .append("createdBy", this.createdBy)
                        .append("updationDate", this.updationDate)
                        .append("updatedBy", this.updatedBy)
                        .append("rowVersion", this.rowVersion)
                        .append("isDeleted", this.isDeleted)
                        .append("enabledFlag", this.enabledFlag)
                        .append("remark", this.remark)
                        .append("sourceFrom", this.sourceFrom)
                        .toString();
    }
}
