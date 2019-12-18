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
 * 平台组团映射表
 *
 * @author lily
 * @email lily
 * @date 2019-12-18 12:51:08
 */

@Table(name = "platform_mdm_group_mapping")
@ApiModel(value = "PlatformMdmGroupMapping", description = "平台组团映射表")
public class PlatformMdmGroupMapping implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "id")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "orgid")
    @ApiModelProperty("平台组团ID")
        private String orgid;

        @Column(name = "panterid")
    @ApiModelProperty("项目id")
        private String panterid;

        @Column(name = "grpid")
    @ApiModelProperty("主数据组团ID")
        private String grpid;

        @Column(name = "grpname")
    @ApiModelProperty("主数据组团名称")
        private String grpname;

        @Column(name = "isNew")
    @ApiModelProperty("是否平台自增 1：新增数据，0：迁移数据")
        private String isnew;

        @Column(name = "createTime")
    @ApiModelProperty("createtime")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date createtime;

        @Column(name = "updateTime")
    @ApiModelProperty("updatetime")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date updatetime;

        @Column(name = "isDele")
    @ApiModelProperty("是否已删，1已删除，0未删除")
        private String isdele;

        @Column(name = "version")
    @ApiModelProperty("version")
        private Integer version;


        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getOrgid() {
            return this.orgid;
        }
        public void setPanterid(String panterid) {
            this.panterid = panterid;
        }

        public String getPanterid() {
            return this.panterid;
        }
        public void setGrpid(String grpid) {
            this.grpid = grpid;
        }

        public String getGrpid() {
            return this.grpid;
        }
        public void setGrpname(String grpname) {
            this.grpname = grpname;
        }

        public String getGrpname() {
            return this.grpname;
        }
        public void setIsnew(String isnew) {
            this.isnew = isnew;
        }

        public String getIsnew() {
            return this.isnew;
        }
        public void setCreatetime(Date createtime) {
            this.createtime = createtime;
        }

        public Date getCreatetime() {
            return this.createtime;
        }
        public void setUpdatetime(Date updatetime) {
            this.updatetime = updatetime;
        }

        public Date getUpdatetime() {
            return this.updatetime;
        }
        public void setIsdele(String isdele) {
            this.isdele = isdele;
        }

        public String getIsdele() {
            return this.isdele;
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
                .append("orgid",this.orgid)
                .append("panterid",this.panterid)
                .append("grpid",this.grpid)
                .append("grpname",this.grpname)
                .append("isnew",this.isnew)
                .append("createtime",this.createtime)
                .append("updatetime",this.updatetime)
                .append("isdele",this.isdele)
                .append("version",this.version)
            .toString();
    }
}
