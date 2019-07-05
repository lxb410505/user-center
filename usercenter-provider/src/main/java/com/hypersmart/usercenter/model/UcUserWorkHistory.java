package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description:
 * @Author: liyong
 * @CreateDate: 2019/4/25 17:13
 * @Version: 1.0
 */
@Table(name = "uc_user_work_his")
@ApiModel(value = "uc_user_work_his", description = "用户上下班历史")
public class UcUserWorkHistory implements Serializable {
    @Id
    @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
    @Column(name = "ID_")
    @ApiModelProperty("主键")
    private String id;
    @Column(name = "STATUS")
    @ApiModelProperty("上下班状态 0:上班，1:下班")
    private String status;
    @Column(name = "CREATE_BY")
    @ApiModelProperty("主键")
    private String createBy;
    @Column(name = "CREATE_TIME")
    @ApiModelProperty("'创建时间'")
    private Date createTime;

    @ApiModelProperty("'用户'")
    @Column(name = "username")
    private String account;
    @ApiModelProperty("用户ID")
    @Column(name = "user_id")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
