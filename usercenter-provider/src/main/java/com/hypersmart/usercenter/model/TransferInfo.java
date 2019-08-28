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
 * 过户信息
 *
 * @author liyong
 * @email xx
 * @date 2019-08-27 20:21:29
 */

@Table(name = "transfer_info")
@ApiModel(value = "TransferInfo", description = "过户信息")
public class TransferInfo implements Serializable{
private static final long serialVersionUID=1L;

        @Id
        @KeySql(genId = com.hypersmart.base.id.genId.Suid.class)
                @Column(name = "id")
    @ApiModelProperty("id")
        private String id;

        @Column(name = "new_customer_name")
    @ApiModelProperty("新客户姓名")
        private String newCustomerName;

        @Column(name = "old_customer_name")
    @ApiModelProperty("原客户姓名")
        private String oldCustomerName;

        @Column(name = "transaction_date")
    @ApiModelProperty("交房日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date transactionDate;

        @Column(name = "house_id")
    @ApiModelProperty("房产ID")
        private String houseId;

        @Column(name = "is_delete")
    @ApiModelProperty("删除标识")
        private Integer isDelete;

        @Column(name = "create_by")
    @ApiModelProperty("创建人")
        private String createBy;

        @Column(name = "create_time")
    @ApiModelProperty("创建时间")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date createTime;

        @Column(name = "update_by")
    @ApiModelProperty("更新人")
        private String updateBy;

        @Column(name = "update_time")
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
        public void setNewCustomerName(String newCustomerName) {
            this.newCustomerName = newCustomerName;
        }

        public String getNewCustomerName() {
            return this.newCustomerName;
        }
        public void setOldCustomerName(String oldCustomerName) {
            this.oldCustomerName = oldCustomerName;
        }

        public String getOldCustomerName() {
            return this.oldCustomerName;
        }
        public void setTransactionDate(Date transactionDate) {
            this.transactionDate = transactionDate;
        }

        public Date getTransactionDate() {
            return this.transactionDate;
        }
        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getHouseId() {
            return this.houseId;
        }
        public void setIsDelete(Integer isDelete) {
            this.isDelete = isDelete;
        }

        public Integer getIsDelete() {
            return this.isDelete;
        }
        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateBy() {
            return this.createBy;
        }
        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getCreateTime() {
            return this.createTime;
        }
        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateBy() {
            return this.updateBy;
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
                .append("newCustomerName",this.newCustomerName)
                .append("oldCustomerName",this.oldCustomerName)
                .append("transactionDate",this.transactionDate)
                .append("houseId",this.houseId)
                .append("isDelete",this.isDelete)
                .append("createBy",this.createBy)
                .append("createTime",this.createTime)
                .append("updateBy",this.updateBy)
                .append("updateTime",this.updateTime)
            .toString();
    }
}
