package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "rsun_jb_hi_reward")
@ApiModel(value = "RsunJbHiReward", description = "用户金币等级管理")
public class RsunJbHiReward implements Serializable {
    @Column(name = "uc_user_id")
    @ApiModelProperty("用户id")
    private String ucUserId;

    @Column(name = "jb_jl_reason")
    @ApiModelProperty("金币操作原因说明")
    private String jbJlReason;

    @Column(name = "jb_jl_time")
    @ApiModelProperty("金币奖励时间")
    private Date jbJlTime;

    @Column(name = "gd_code")
    @ApiModelProperty("工单编号")
    private String gdCode;

    @Column(name = "gcoin_val")
    @ApiModelProperty("金币奖励值")
    private Double gcoinVal;

    @Column(name = "jb_jl_type")
    @ApiModelProperty("金币记录类别：是否为申诉")
    private Double jbJlType;

    @Column(name = "jb_originnal_time")
    @ApiModelProperty("申诉的公区报修工单创建时间")
    private Date jbOriginnalTime;

}
