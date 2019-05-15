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
 * @author magellan
 * @email magellan
 * @date 2019-05-14 13:37:39
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

    @Column(name = "order_")
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

    @Column(name = "create_by")
    @ApiModelProperty("创建人")
    private String createBy;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "update_by")
    @ApiModelProperty("更新人")
    private String updateBy;

    @Column(name = "update_time")
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


    @Column(name = "row_version")
    @ApiModelProperty("记录的版本号")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rowVersion;

    @Column(name = "remark")
    @ApiModelProperty("备注")
    private String remark;
    @Column(name = "effective_time")
    @ApiModelProperty("生效时间/导入时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectiveTime;
//    @Column(name = "year")
//    private Integer year;
//    @Column(name = "month")
//    private Integer month;

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Date rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                        .toString();
    }
}
