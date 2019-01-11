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
 * 用户组织关系
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:59:39
 */

@Table(name = "uc_org_user")
@ApiModel(value = "UcOrgUser", description = "用户组织关系")
public class UcOrgUser implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "ORG_ID_")
    @ApiModelProperty("组织编号")
        private String orgId;

        @Column(name = "USER_ID_")
    @ApiModelProperty("用户编号")
        private String userId;

        @Column(name = "IS_MASTER_")
    @ApiModelProperty("0:非主部门，1：主部门")
        private Integer isMaster;

        @Column(name = "POS_ID_")
    @ApiModelProperty("岗位编号")
        private String posId;

        @Column(name = "IS_CHARGE_")
    @ApiModelProperty("2组织主负责人，1组织负责人，0 不是负责人")
        private Integer isCharge;

        @Column(name = "START_DATE_")
    @ApiModelProperty("开始生效日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date startDate;

        @Column(name = "END_DATE_")
    @ApiModelProperty("结束日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date endDate;

        @Column(name = "IS_REL_ACTIVE_")
    @ApiModelProperty("是否有效，0:无效，1：有效")
        private Integer isRelActive;

        @Column(name = "UPDATE_TIME_")
    @ApiModelProperty("更新时间")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date updateTime;

        @Column(name = "IS_DELE_")
    @ApiModelProperty("是否已删，0已删除，1未删除")
        private String isDele;

        @Column(name = "VERSION_")
    @ApiModelProperty("版本号")
        private Integer version;


        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgId() {
            return this.orgId;
        }
        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return this.userId;
        }
        public void setIsMaster(Integer isMaster) {
            this.isMaster = isMaster;
        }

        public Integer getIsMaster() {
            return this.isMaster;
        }
        public void setPosId(String posId) {
            this.posId = posId;
        }

        public String getPosId() {
            return this.posId;
        }
        public void setIsCharge(Integer isCharge) {
            this.isCharge = isCharge;
        }

        public Integer getIsCharge() {
            return this.isCharge;
        }
        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getStartDate() {
            return this.startDate;
        }
        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Date getEndDate() {
            return this.endDate;
        }
        public void setIsRelActive(Integer isRelActive) {
            this.isRelActive = isRelActive;
        }

        public Integer getIsRelActive() {
            return this.isRelActive;
        }
        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public Date getUpdateTime() {
            return this.updateTime;
        }
        public void setIsDele(String isDele) {
            this.isDele = isDele;
        }

        public String getIsDele() {
            return this.isDele;
        }
        public void setVersion(Integer version) {
            this.version = version;
        }

        public Integer getVersion() {
            return this.version;
        }
    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("orgId",this.orgId)
                .append("userId",this.userId)
                .append("isMaster",this.isMaster)
                .append("posId",this.posId)
                .append("isCharge",this.isCharge)
                .append("startDate",this.startDate)
                .append("endDate",this.endDate)
                .append("isRelActive",this.isRelActive)
                .append("updateTime",this.updateTime)
                .append("isDele",this.isDele)
                .append("version",this.version)
            .toString();
    }
}
