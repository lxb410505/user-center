package com.hypersmart.usercenter.bo;


import com.hypersmart.framework.model.GenericBO;

/**
 * 网格基础信息BO
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */

public class GridBasicInfoBO extends GenericBO{

    //是否分页 1-不分页 其他-分页
    private Integer isAllPage;

    //网格编码
    private String gridCode;

    //网格名称
    private String gridName;

    //网格类型：公区网格、楼栋网格、服务中心网格
    private String gridType;

    //所属区域ID
    private String areaId;

    //城市id
    private String cityId;

    //所属项目ID
    private String projectId;

    //所属地块ID
    private String massifId;

    //所属分期ID
    private String stagingId;

    //管家ID（主数据用户主键）
    private String housekeeperId;

    //业态属性：商办、住宅、公寓等
    private String formatAttribute;

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getGridType() {
        return gridType;
    }

    public void setGridType(String gridType) {
        this.gridType = gridType;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMassifId() {
        return massifId;
    }

    public void setMassifId(String massifId) {
        this.massifId = massifId;
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

    public String getFormatAttribute() {
        return formatAttribute;
    }

    public void setFormatAttribute(String formatAttribute) {
        this.formatAttribute = formatAttribute;
    }

    public Integer getIsAllPage() {
        return isAllPage;
    }

    public void setIsAllPage(Integer isAllPage) {
        this.isAllPage = isAllPage;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
