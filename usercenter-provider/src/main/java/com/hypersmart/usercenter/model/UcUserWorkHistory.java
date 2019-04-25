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
    @ApiModelProperty("上下班状态 on_work:上班，off_work'")
    private String status;
    @Column(name = "CREATE_BY")
    @ApiModelProperty("主键")
    private String createBy;
    @Column(name = "'CREATE_TIME'")
    @ApiModelProperty("'创建时间'")
    private LocalDateTime createTime;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UcUserWorkHistory{");
        sb.append("id='").append(id).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
