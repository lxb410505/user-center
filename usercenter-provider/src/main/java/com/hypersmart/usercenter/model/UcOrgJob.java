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
 * 职务定义
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:44:42
 */

@Table(name = "uc_org_job")
@ApiModel(value = "UcOrgJob", description = "职务定义")
public class UcOrgJob implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "NAME_")
    @ApiModelProperty("名称")
        private String name;

        @Column(name = "CODE_")
    @ApiModelProperty("编码")
        private String code;

        @Column(name = "JOB_LEVEL_")
    @ApiModelProperty("职务级别")
        private String jobLevel;

        @Column(name = "DESCRIPTION_")
    @ApiModelProperty("描述")
        private String description;

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
        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
        public void setJobLevel(String jobLevel) {
            this.jobLevel = jobLevel;
        }

        public String getJobLevel() {
            return this.jobLevel;
        }
        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
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
                .append("name",this.name)
                .append("code",this.code)
                .append("jobLevel",this.jobLevel)
                .append("description",this.description)
                .append("updateTime",this.updateTime)
                .append("isDele",this.isDele)
                .append("version",this.version)
            .toString();
    }
}
