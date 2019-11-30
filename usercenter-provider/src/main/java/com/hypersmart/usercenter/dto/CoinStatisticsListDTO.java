package com.hypersmart.usercenter.dto;


import lombok.Data;

/**
 * 查询员工各月份金币数对象
 */
@Data
public class CoinStatisticsListDTO {
    //员工Id
    private String userId;

    //员工姓名
    private  String fullName;

    //工号
    private  String userNumber;

    //一月金币数
    private Double january;

    //二月金币数
    private Double february;

    //三月金币数
    private Double march;

    //四月金币数
    private Double april;

    //五月金币数
    private Double may;

    //六月金币数
    private Double june;

    //七月金币数
    private Double july;

    //八月金币数
    private Double angust;

    //九月金币数
    private Double september;

    //十月金币数
    private Double october;

    //十一月金币数
    private Double november;

    //十二月金币数
    private Double december;


}
