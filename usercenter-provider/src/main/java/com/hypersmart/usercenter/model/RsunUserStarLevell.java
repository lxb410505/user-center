package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "rsun_user_star_level")
@ApiModel(value = "RsunUserStarLevel", description = "用户金币等级管理")
public class  RsunUserStarLevell implements Serializable {
    @Id
    @Column(name = "uc_user_id")
    @ApiModelProperty("id")
    private String ucUserId;

    @Column(name = "pj_star_id")
    @ApiModelProperty("等级")
    private Integer pjStarId;

    @Column(name = "level_sy_time")
    @ApiModelProperty("等级授权时间")
    private Date levelSyTime;

    @Column(name = "total_coin")
    @ApiModelProperty("个人金币总额")
    private Double totalCoin;

    @Column(name = "xz_num")
    @ApiModelProperty("个人勋章数")
    private Integer xzNum;

    public String getUcUserId() {
        return ucUserId;
    }

    public void setUcUserId(String ucUserId) {
        this.ucUserId = ucUserId;
    }

    public Integer getPjStarId() {
        return pjStarId;
    }

    public void setPjStarId(Integer pjStarId) {
        this.pjStarId = pjStarId;
    }

    public Date getLevelSyTime() {
        return levelSyTime;
    }

    public void setLevelSyTime(Date levelSyTime) {
        this.levelSyTime = levelSyTime;
    }

    public Double getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(Double totalCoin) {
        this.totalCoin = totalCoin;
    }

    public Integer getXzNum() {
        return xzNum;
    }

    public void setXzNum(Integer xzNum) {
        this.xzNum = xzNum;
    }

    @Override
    public String toString() {
        return "RsunUserStarLevell{" +
                "ucUserId='" + ucUserId + '\'' +
                ", pjStarId=" + pjStarId +
                ", levelSyTime=" + levelSyTime +
                ", totalCoin=" + totalCoin +
                ", xzNum=" + xzNum +
                '}';
    }
}
