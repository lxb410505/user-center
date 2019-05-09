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
 * 汇报关系分类管理
 *
 * @author sunwenjie
 * @email xx
 * @date 2019-05-07 17:02:47
 */

@Table(name = "uc_role")
@ApiModel(value = "UcRole", description = "汇报关系分类管理")
public class UcRole implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "NAME_")
    @ApiModelProperty("角色名称")
        private String name;

        @Column(name = "CODE_")
    @ApiModelProperty("英文别名")
        private String code;

        @Column(name = "ENABLED_")
    @ApiModelProperty("可用 0：禁用，1：启用")
        private Integer enabled;

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

        @Column(name = "CREATE_BY_")
    @ApiModelProperty("创建人id")
        private String createBy;

        @Column(name = "UPDATE_BY_")
    @ApiModelProperty("更新人id")
        private String updateBy;

        @Column(name = "CREATE_ORG_ID_")
    @ApiModelProperty("创建者组织id")
        private String createOrgId;


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
        public void setEnabled(Integer enabled) {
            this.enabled = enabled;
        }

        public Integer getEnabled() {
            return this.enabled;
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
        public void setCreateOrgId(String createOrgId) {
            this.createOrgId = createOrgId;
        }

        public String getCreateOrgId() {
            return this.createOrgId;
        }
    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("name",this.name)
                .append("code",this.code)
                .append("enabled",this.enabled)
                .append("description",this.description)
                .append("updateTime",this.updateTime)
                .append("isDele",this.isDele)
                .append("version",this.version)
                .append("createBy",this.createBy)
                .append("updateBy",this.updateBy)
                .append("createOrgId",this.createOrgId)
            .toString();
    }
}
