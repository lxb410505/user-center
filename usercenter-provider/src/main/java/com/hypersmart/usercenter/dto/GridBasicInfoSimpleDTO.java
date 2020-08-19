package com.hypersmart.usercenter.dto;

public class GridBasicInfoSimpleDTO {
    private String id;
    private String gridName;
    private String stagingId;
    private String housekeeperId;
    private String gridType;
    private String gridId;
    private String housekeeperMasterId;
    private String housekeeperMasterName;

    public String getHousekeeperMasterId() {
        return housekeeperMasterId;
    }

    public void setHousekeeperMasterId(String housekeeperMasterId) {
        this.housekeeperMasterId = housekeeperMasterId;
    }

    public String getHousekeeperMasterName() {
        return housekeeperMasterName;
    }

    public void setHousekeeperMasterName(String housekeeperMasterName) {
        this.housekeeperMasterName = housekeeperMasterName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getStagingId() {
        return stagingId;
    }

    public void setStagingId(String stagingId) {
        this.stagingId = stagingId;
    }

    public String getHousekeeperId() {
        return housekeeperId;
    }

    public void setHousekeeperId(String housekeeperId) {
        this.housekeeperId = housekeeperId;
    }

    public String getGridType() {
        return gridType;
    }

    public void setGridType(String gridType) {
        this.gridType = gridType;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }
}
