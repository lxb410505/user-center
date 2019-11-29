package com.hypersmart.usercenter.dto;


import java.text.DecimalFormat;

/**
 * 查询员工各月份金币数对象
 */
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


    DecimalFormat df   = new DecimalFormat("######0.00");

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Double getJanuary() {
        return january;
    }

    public void setJanuary(Double january) {

        this.january = Double.valueOf(df.format(january));
    }

    public Double getFebruary() {
        return february;
    }

    public void setFebruary(Double february) {
        this.february = Double.valueOf(df.format(february));
    }

    public Double getMarch() {
        return march;
    }

    public void setMarch(Double march) {
        this.march = Double.valueOf(df.format(march));
    }

    public Double getApril() {
        return april;
    }

    public void setApril(Double april) {
        this.april = Double.valueOf(df.format(april));
    }

    public Double getMay() {
        return may;
    }

    public void setMay(Double may) {
        this.may = Double.valueOf(df.format(may));
    }

    public Double getJune() {
        return june;
    }

    public void setJune(Double june) {
        this.june = Double.valueOf(df.format(june));
    }

    public Double getJuly() {
        return july;
    }

    public void setJuly(Double july) {
        this.july = Double.valueOf(df.format(july));
    }

    public Double getAngust() {
        return angust;
    }

    public void setAngust(Double angust) {
        this.angust = Double.valueOf(df.format(angust));
    }

    public Double getSeptember() {
        return september;
    }

    public void setSeptember(Double september) {
        this.september = Double.valueOf(df.format(september));
    }

    public Double getOctober() {
        return october;
    }

    public void setOctober(Double october) {
        this.october = Double.valueOf(df.format(october));
    }

    public Double getNovember() {
        return november;
    }

    public void setNovember(Double november) {
        this.november = Double.valueOf(df.format(november));
    }

    public Double getDecember() {
        return december;
    }

    public void setDecember(Double december) {
        this.december = Double.valueOf(df.format(december));
    }
}
