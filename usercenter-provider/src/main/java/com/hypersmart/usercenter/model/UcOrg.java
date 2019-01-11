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
 * 组织架构
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */

@Table(name = "uc_org")
@ApiModel(value = "UcOrg", description = "组织架构")
public class UcOrg implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "ID_")
    @ApiModelProperty("主键")
        private String id;

        @Column(name = "NAME_")
    @ApiModelProperty("名称")
        private String name;

        @Column(name = "PARENT_ID_")
    @ApiModelProperty("父组织id")
        private String parentId;

        @Column(name = "ORDER_NO_")
    @ApiModelProperty("排序号")
        private Integer orderNo;

        @Column(name = "CODE_")
    @ApiModelProperty("组织代码")
        private String code;

        @Column(name = "GRADE_")
    @ApiModelProperty("级别")
        private String grade;

        @Column(name = "PATH_")
    @ApiModelProperty("组织id路径，包含层级关系")
        private String path;

        @Column(name = "PATH_NAME_")
    @ApiModelProperty("组织名路径，包含层级关系")
        private String pathName;

        @Column(name = "DEM_ID_")
    @ApiModelProperty("维度id")
        private String demId;

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
        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getParentId() {
            return this.parentId;
        }
        public void setOrderNo(Integer orderNo) {
            this.orderNo = orderNo;
        }

        public Integer getOrderNo() {
            return this.orderNo;
        }
        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getGrade() {
            return this.grade;
        }
        public void setPath(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
        public void setPathName(String pathName) {
            this.pathName = pathName;
        }

        public String getPathName() {
            return this.pathName;
        }
        public void setDemId(String demId) {
            this.demId = demId;
        }

        public String getDemId() {
            return this.demId;
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
                .append("parentId",this.parentId)
                .append("orderNo",this.orderNo)
                .append("code",this.code)
                .append("grade",this.grade)
                .append("path",this.path)
                .append("pathName",this.pathName)
                .append("demId",this.demId)
                .append("updateTime",this.updateTime)
                .append("isDele",this.isDele)
                .append("version",this.version)
            .toString();
    }
}
