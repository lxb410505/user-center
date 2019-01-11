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
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */

@Table(name = "uc_user")
@ApiModel(value = "UcUser", description = "用户管理")
public class UcUser implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("用户id")
        private String id;

        @Column(name = "FULLNAME_")
    @ApiModelProperty("姓名")
        private String fullname;

        @Column(name = "ACCOUNT_")
    @ApiModelProperty("帐号")
        private String account;

        @Column(name = "PASSWORD_")
    @ApiModelProperty("密码")
        private String password;

        @Column(name = "EMAIL_")
    @ApiModelProperty("邮箱")
        private String email;

        @Column(name = "MOBILE_")
    @ApiModelProperty("手机号码")
        private String mobile;

        @Column(name = "WEIXIN_")
    @ApiModelProperty("微信号")
        private String weixin;

        @Column(name = "CREATE_TIME_")
    @ApiModelProperty("创建时间")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date createTime;

        @Column(name = "ADDRESS_")
    @ApiModelProperty("地址")
        private String address;

        @Column(name = "PHOTO_")
    @ApiModelProperty("头像")
        private String photo;

        @Column(name = "SEX_")
    @ApiModelProperty("性别：男，女，未知")
        private String sex;

        @Column(name = "FROM_")
    @ApiModelProperty("来源")
        private String from;

        @Column(name = "STATUS_")
    @ApiModelProperty("0:禁用，1正常")
        private Integer status;

        @Column(name = "HAS_SYNC_TO_WX_")
    @ApiModelProperty("微信同步关注状态: 0：未同步  1：已同步，尚未关注  2：已同步且已关注")
        private Integer hasSyncToWx;

        @Column(name = "NOTIFY_TYPE_")
    @ApiModelProperty("自定义接收消息类型")
        private String notifyType;

        @Column(name = "USER_NUMBER_")
    @ApiModelProperty("工号")
        private String userNumber;

        @Column(name = "ID_CARD_")
    @ApiModelProperty("身份证号")
        private String idCard;

        @Column(name = "PHONE_")
    @ApiModelProperty("办公电话")
        private String phone;

        @Column(name = "BIRTHDAY_")
    @ApiModelProperty("出生日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date birthday;

        @Column(name = "ENTRY_DATE_")
    @ApiModelProperty("入职日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date entryDate;

        @Column(name = "LEAVE_DATE_")
    @ApiModelProperty("离职日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date leaveDate;

        @Column(name = "EDUCATION_")
    @ApiModelProperty("学历")
        private String education;

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
        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getFullname() {
            return this.fullname;
        }
        public void setAccount(String account) {
            this.account = account;
        }

        public String getAccount() {
            return this.account;
        }
        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return this.password;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return this.email;
        }
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return this.mobile;
        }
        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getWeixin() {
            return this.weixin;
        }
        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getCreateTime() {
            return this.createTime;
        }
        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return this.address;
        }
        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPhoto() {
            return this.photo;
        }
        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSex() {
            return this.sex;
        }
        public void setFrom(String from) {
            this.from = from;
        }

        public String getFrom() {
            return this.from;
        }
        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return this.status;
        }
        public void setHasSyncToWx(Integer hasSyncToWx) {
            this.hasSyncToWx = hasSyncToWx;
        }

        public Integer getHasSyncToWx() {
            return this.hasSyncToWx;
        }
        public void setNotifyType(String notifyType) {
            this.notifyType = notifyType;
        }

        public String getNotifyType() {
            return this.notifyType;
        }
        public void setUserNumber(String userNumber) {
            this.userNumber = userNumber;
        }

        public String getUserNumber() {
            return this.userNumber;
        }
        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCard() {
            return this.idCard;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return this.phone;
        }
        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Date getBirthday() {
            return this.birthday;
        }
        public void setEntryDate(Date entryDate) {
            this.entryDate = entryDate;
        }

        public Date getEntryDate() {
            return this.entryDate;
        }
        public void setLeaveDate(Date leaveDate) {
            this.leaveDate = leaveDate;
        }

        public Date getLeaveDate() {
            return this.leaveDate;
        }
        public void setEducation(String education) {
            this.education = education;
        }

        public String getEducation() {
            return this.education;
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
                .append("fullname",this.fullname)
                .append("account",this.account)
                .append("password",this.password)
                .append("email",this.email)
                .append("mobile",this.mobile)
                .append("weixin",this.weixin)
                .append("createTime",this.createTime)
                .append("address",this.address)
                .append("photo",this.photo)
                .append("sex",this.sex)
                .append("from",this.from)
                .append("status",this.status)
                .append("hasSyncToWx",this.hasSyncToWx)
                .append("notifyType",this.notifyType)
                .append("userNumber",this.userNumber)
                .append("idCard",this.idCard)
                .append("phone",this.phone)
                .append("birthday",this.birthday)
                .append("entryDate",this.entryDate)
                .append("leaveDate",this.leaveDate)
                .append("education",this.education)
                .append("updateTime",this.updateTime)
                .append("isDele",this.isDele)
                .append("version",this.version)
            .toString();
    }
}
