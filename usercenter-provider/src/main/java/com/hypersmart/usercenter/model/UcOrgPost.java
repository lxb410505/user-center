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
 * 部门岗位
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:43:26
 */

@Table(name = "uc_org_post")
@ApiModel(value = "UcOrgPost", description = "部门岗位")
public class UcOrgPost implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "ORG_ID_")
    @ApiModelProperty("组织id")
        private String orgId;

        @Column(name = "JOB_ID_")
    @ApiModelProperty("职务id")
        private String jobId;

        @Column(name = "POS_NAME_")
    @ApiModelProperty("岗位名称")
        private String posName;

        @Column(name = "CODE_")
    @ApiModelProperty("岗位编码")
        private String code;

        @Column(name = "IS_CHARGE_")
    @ApiModelProperty("是否主岗位：1主岗位，0 非主岗位")
        private Integer isCharge;

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
        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getJobId() {
            return this.jobId;
        }
        public void setPosName(String posName) {
            this.posName = posName;
        }

        public String getPosName() {
            return this.posName;
        }
        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
        public void setIsCharge(Integer isCharge) {
            this.isCharge = isCharge;
        }

        public Integer getIsCharge() {
            return this.isCharge;
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
                .append("jobId",this.jobId)
                .append("posName",this.posName)
                .append("code",this.code)
                .append("isCharge",this.isCharge)
                .append("updateTime",this.updateTime)
                .append("isDele",this.isDele)
                .append("version",this.version)
            .toString();
    }
}
