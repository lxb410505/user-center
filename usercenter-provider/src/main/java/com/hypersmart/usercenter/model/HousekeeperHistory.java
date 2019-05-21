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
 * 管家历史记录表-管家快照
 *
 * @author jiangxiaoxuan
 * @email 111111@163.com
 * @date 2019-01-17 17:40:36
 */

@Table(name = "housekeeper_history")
@ApiModel(value = "HousekeeperHistory", description = "管家历史记录表-管家快照")
public class HousekeeperHistory implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "id")
    @ApiModelProperty("用户主键")
        private String id;

        @Column(name = "user_id")
    @ApiModelProperty("用户id")
        private String userId;

        @Column(name = "user_account")
    @ApiModelProperty("冗余用户账号")
        private String userAccount;

        @Column(name = "user_name")
    @ApiModelProperty("冗余用户姓名")
        private String userName;

        @Column(name = "mobile_phone")
    @ApiModelProperty("冗余手机号")
        private String mobilePhone;

        @Column(name = "email")
    @ApiModelProperty("冗余邮箱")
        private String email;

        @Column(name = "user_level")
    @ApiModelProperty("管家级别（数据字典）：大管家等")
        private String userLevel;

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
    @ApiModelProperty("租户id")
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
        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return this.userId;
        }
        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserAccount() {
            return this.userAccount;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return this.userName;
        }
        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getMobilePhone() {
            return this.mobilePhone;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return this.email;
        }
        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public String getUserLevel() {
            return this.userLevel;
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
                .append("userId",this.userId)
                .append("userAccount",this.userAccount)
                .append("userName",this.userName)
                .append("mobilePhone",this.mobilePhone)
                .append("email",this.email)
                .append("userLevel",this.userLevel)
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
