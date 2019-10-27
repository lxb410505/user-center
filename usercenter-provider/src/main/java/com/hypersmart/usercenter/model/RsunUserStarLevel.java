package com.hypersmart.usercenter.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/*@Table(name = "rsun_user_star_level")
@ApiModel(value = "RsunUserStarLevel", description = "用户金币等级管理")*/
public class  RsunUserStarLevel {

    /*@Column(name = "uc_user_id")
    @ApiModelProperty("id")*/
    private String ucUserId;

    private String zhanghao;

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }

    /* @Column(name = "pj_star_id")
         @ApiModelProperty("等级")*/
    private Integer pjStarId;

    /*@Column(name = "level_sy_time")
    @ApiModelProperty("等级授权时间")*/
    private Date levelSyTime;

    /* @Column(name = "total_coin")
     @ApiModelProperty("个人金币总额")*/
    private Double totalCoin;

    /* @Column(name = "xz_num")
     @ApiModelProperty("个人勋章数")*/
    private Double xzNum;

    private String name; //姓名

    private String dikuai;  //地块

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

    public Double getXzNum() {
        return xzNum;
    }

    public void setXzNum(Double xzNum) {
        this.xzNum = xzNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDikuai() {
        return dikuai;
    }

    public void setDikuai(String dikuai) {
        this.dikuai = dikuai;
    }

    @Override
    public String toString() {
        return "RsunUserStarLevel{" +
                "ucUserId='" + ucUserId + '\'' +
                ", zhanghao='" + zhanghao + '\'' +
                ", pjStarId=" + pjStarId +
                ", levelSyTime=" + levelSyTime +
                ", totalCoin=" + totalCoin +
                ", xzNum=" + xzNum +
                ", name='" + name + '\'' +
                ", dikuai='" + dikuai + '\'' +
                '}';
    }
}
