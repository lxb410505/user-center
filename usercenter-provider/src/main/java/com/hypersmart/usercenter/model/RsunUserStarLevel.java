package com.hypersmart.usercenter.model;

import lombok.Data;

import java.util.Date;


@Data
public class  RsunUserStarLevel {

    private String ucUserId;  //用户Id

    private String account;  //账号

    private Integer pjStarId; //等级

    private Double totalCoin; //金币

    private Integer xzNum;  //勋章数

    private String fullName; //姓名

    private Date levelSyTime; //等级授予时间

}
