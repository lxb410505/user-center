package com.hypersmart.usercenter.bo;

import java.util.Objects;

public class HouseKeeperBO {
    private String divideId;
    private String houseKeeperId;

    public String getDivideId() {
        return divideId;
    }

    public void setDivideId(String divideId) {
        this.divideId = divideId;
    }

    public String getHouseKeeperId() {
        return houseKeeperId;
    }

    public void setHouseKeeperId(String houseKeeperId) {
        this.houseKeeperId = houseKeeperId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof HouseKeeperBO)) {
            return false;
        }
        HouseKeeperBO houseKeeperBO = (HouseKeeperBO) obj;
        return ((houseKeeperBO.getDivideId().equals(this.getDivideId()))&&(houseKeeperBO.getHouseKeeperId().equals(this.getHouseKeeperId())));

    }

    /**
     * 重写hashcode 方法，返回的hashCode不一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getDivideId(),this.getHouseKeeperId());
    }
}
