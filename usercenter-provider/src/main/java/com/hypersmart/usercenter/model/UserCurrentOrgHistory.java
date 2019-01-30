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
 * 用户当前组织表
 *
 * @author admin
 * @email @sian.cn
 * @date 2019-01-29 20:06:10
 */

@Table(name = "user_current_org_history")
@ApiModel(value = "UserCurrentOrgHistory", description = "用户当前组织表")
public class UserCurrentOrgHistory implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "USER_ID_")
    @ApiModelProperty("用户id")
        private String userId;

        @Column(name = "ORG_ID_")
    @ApiModelProperty("用户id")
        private String orgId;

        @Column(name = "UPDATE_TIME_")
    @ApiModelProperty("更新时间")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date updateTime;


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
        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgId() {
            return this.orgId;
        }
        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public Date getUpdateTime() {
            return this.updateTime;
        }
    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("userId",this.userId)
                .append("orgId",this.orgId)
                .append("updateTime",this.updateTime)
            .toString();
    }
}
