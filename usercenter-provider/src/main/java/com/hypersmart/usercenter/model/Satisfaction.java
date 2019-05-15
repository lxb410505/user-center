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
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author smh
 * @email smh
 * @date 2019-05-14 17:23:33
 */

@Table(name = "t_satisfaction")
@ApiModel(value = "Satisfaction", description = "")
public class Satisfaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "id")
    @ApiModelProperty("id")
    private String id;

    @Column(name = "order")
    @ApiModelProperty("序列")
    private String order;

    @Column(name = "type")
    @ApiModelProperty("组织层级分类")
    private String type;

    @Column(name = "org_code")
    @ApiModelProperty("组织代码")
    private String orgCode;

    @Column(name = "org_name")
    @ApiModelProperty("组织名称")
    private String orgName;

    @Column(name = "overall_satisfaction")
    @ApiModelProperty("综合满意度")
    private BigDecimal overallSatisfaction;

    @Column(name = "storming")
    @ApiModelProperty("磨合期")
    private BigDecimal storming;

    @Column(name = "stationary_phase")
    @ApiModelProperty("稳定期")
    private BigDecimal stationaryPhase;

    @Column(name = "old_proprietor")
    @ApiModelProperty("老业主")
    private BigDecimal oldProprietor;

    @Column(name = "order_service_unit")
    @ApiModelProperty("秩序服务单元")
    private BigDecimal orderServiceUnit;

    @Column(name = "esu_cleaning")
    @ApiModelProperty("环境服务单元-保洁   ")
    private BigDecimal esuCleaning;

    @Column(name = "esu_green")
    @ApiModelProperty("环境服务单元-绿化")
    private BigDecimal esuGreen;

    @Column(name = "engineering_service_unit")
    @ApiModelProperty("工程服务单元")
    private BigDecimal engineeringServiceUnit;

    @Column(name = "create_time")
    @ApiModelProperty("createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "create_by")
    @ApiModelProperty("createBy")
    private String createBy;

    @Column(name = "update_by")
    @ApiModelProperty("updateBy")
    private String updateBy;

    @Column(name = "update_time")
    @ApiModelProperty("updateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "row_version")
    @ApiModelProperty("rowVersion")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rowVersion;

    @Column(name = "remark")
    @ApiModelProperty("remark")
    private String remark;

    @Column(name = "effective_time")
    @ApiModelProperty("有效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTime;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOverallSatisfaction(BigDecimal overallSatisfaction) {
        this.overallSatisfaction = overallSatisfaction;
    }

    public BigDecimal getOverallSatisfaction() {
        return this.overallSatisfaction;
    }

    public void setStorming(BigDecimal storming) {
        this.storming = storming;
    }

    public BigDecimal getStorming() {
        return this.storming;
    }

    public void setStationaryPhase(BigDecimal stationaryPhase) {
        this.stationaryPhase = stationaryPhase;
    }

    public BigDecimal getStationaryPhase() {
        return this.stationaryPhase;
    }

    public void setOldProprietor(BigDecimal oldProprietor) {
        this.oldProprietor = oldProprietor;
    }

    public BigDecimal getOldProprietor() {
        return this.oldProprietor;
    }

    public void setOrderServiceUnit(BigDecimal orderServiceUnit) {
        this.orderServiceUnit = orderServiceUnit;
    }

    public BigDecimal getOrderServiceUnit() {
        return this.orderServiceUnit;
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

    public void setEngineeringServiceUnit(BigDecimal engineeringServiceUnit) {
        this.engineeringServiceUnit = engineeringServiceUnit;
    }

    public BigDecimal getEngineeringServiceUnit() {
        return this.engineeringServiceUnit;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setRowVersion(Date rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Date getRowVersion() {
        return this.rowVersion;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getEffectiveTime() {
        return this.effectiveTime;
    }

    public String toString() {
        return
                new ToStringBuilder(this)
                        .append("id", this.id)
                        .append("order", this.order)
                        .append("type", this.type)
                        .append("orgCode", this.orgCode)
                        .append("orgName", this.orgName)
                        .append("overallSatisfaction", this.overallSatisfaction)
                        .append("storming", this.storming)
                        .append("stationaryPhase", this.stationaryPhase)
                        .append("oldProprietor", this.oldProprietor)
                        .append("orderServiceUnit", this.orderServiceUnit)
                        .append("esuCleaning", this.esuCleaning)
                        .append("esuGreen", this.esuGreen)
                        .append("engineeringServiceUnit", this.engineeringServiceUnit)
                        .append("createTime", this.createTime)
                        .append("createBy", this.createBy)
                        .append("updateBy", this.updateBy)
                        .append("updateTime", this.updateTime)
                        .append("rowVersion", this.rowVersion)
                        .append("remark", this.remark)
                        .append("effectiveTime", this.effectiveTime)
                        .toString();
    }
}
