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
 * 维度管理
 *
 * @author yang
 * @email yang17766@sina.com
 * @date 2019-03-13 13:49:47
 */

@Table(name = "uc_demension")
@ApiModel(value = "UcDemension", description = "维度管理")
public class UcDemension implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("维度id")
        private String id;

        @Column(name = "DEM_NAME_")
    @ApiModelProperty("维度名称")
        private String demName;

        @Column(name = "CODE_")
    @ApiModelProperty("维度代码")
        private String code;

        @Column(name = "IS_DEFAULT_")
    @ApiModelProperty("是否默认(1:默认,0:非默认)")
        private Integer isDefault;

        @Column(name = "DEM_DESC_")
    @ApiModelProperty("描述")
        private String demDesc;

        @Column(name = "ORGAN_ID_")
    @ApiModelProperty("机构id")
        private String organId;

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
        public void setDemName(String demName) {
            this.demName = demName;
        }

        public String getDemName() {
            return this.demName;
        }
        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
        public void setIsDefault(Integer isDefault) {
            this.isDefault = isDefault;
        }

        public Integer getIsDefault() {
            return this.isDefault;
        }
        public void setDemDesc(String demDesc) {
            this.demDesc = demDesc;
        }

        public String getDemDesc() {
            return this.demDesc;
        }
        public void setOrganId(String organId) {
            this.organId = organId;
        }

        public String getOrganId() {
            return this.organId;
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
                .append("demName",this.demName)
                .append("code",this.code)
                .append("isDefault",this.isDefault)
                .append("demDesc",this.demDesc)
                .append("organId",this.organId)
                .append("updateTime",this.updateTime)
                .append("isDele",this.isDele)
                .append("version",this.version)
            .toString();
    }
}
