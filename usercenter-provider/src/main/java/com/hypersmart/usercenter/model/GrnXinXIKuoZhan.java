package com.hypersmart.usercenter.model;

public class  GrnXinXIKuoZhan {

    private Double userTotalCoin; //个人金币总额

    private Double curMonthCoin; //本月金币总额

    private Double userStarLeve; //用户星级

    private Double userMedalLeve; //勋章数

    public Double getUserTotalCoin() {
        return userTotalCoin;
    }

    public void setUserTotalCoin(Double userTotalCoin) {
        this.userTotalCoin = userTotalCoin;
    }

    public Double getCurMonthCoin() {
        return curMonthCoin;
    }

    public void setCurMonthCoin(Double curMonthCoin) {
        this.curMonthCoin = curMonthCoin;
    }

    public Double getUserStarLeve() {
        return userStarLeve;
    }

    public void setUserStarLeve(Double userStarLeve) {
        this.userStarLeve = userStarLeve;
    }

    public Double getUserMedalLeve() {
        return userMedalLeve;
    }

    public void setUserMedalLeve(Double userMedalLeve) {
        this.userMedalLeve = userMedalLeve;
    }

    @Override
    public String toString() {
        return "GrnXinXIKuoZhan{" +
                "userTotalCoin=" + userTotalCoin +
                ", curMonthCoin=" + curMonthCoin +
                ", userStarLeve=" + userStarLeve +
                ", userMedalLeve=" + userMedalLeve +
                '}';
    }
}
