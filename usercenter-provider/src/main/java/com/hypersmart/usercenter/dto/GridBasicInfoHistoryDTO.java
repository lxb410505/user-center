/**
 * <pre>
 * 作   者：hwy
 * 创建日期：2019-1-11
 * </pre>
 */
/*                                                                        */
/* Copyright (c) 2018-2019 HyperSmart Company                                  */
/* 深圳市超智慧信息科技有限公司版权所有                                     */
/*                                                                        */
/* PROPRIETARY RIGHTS of HyperSmart Company are involved in the           */
/* subject matter of this material. All manufacturing, reproduction, use, */
/* and sales rights pertaining to this subject matter are governed by the */
/* license agreement. The recipient of this software implicitly accepts   */
/* the terms of the license.                                              */
/* 本软件文档资料是深圳市超智慧信息科技有限公司的资产，任何人士阅读和        */
/* 使用本资料必须获得相应的书面授权，承担保密责任和接受相应的法律约束。      */
/*                                                                        */
/**************************************************************************/

/**
 * <pre>
 * 作   者：hwy
 * 创建日期：2019-1-11
 * </pre>
 */

package com.hypersmart.usercenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hypersmart.framework.model.GenericDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 网格基础信息历史表-变化前快照 DTO
 * </pre>
 */
public class GridBasicInfoHistoryDTO extends GenericDTO<String> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 网格主键ID
     */
    private String gridId;

    /**
     * 网格编码
     */
    private String gridCode;

    /**
     * 网格名称
     */
    private String gridName;

    /**
     * 网格类型：公区网格、楼栋网格、服务中心网格
     */
    private String gridType;

    /**
     * 网格覆盖范围（json格式）：楼栋-单元-楼层-房产
     */
    private String gridRange;

    /**
     * 网格备注
     */
    private String gridRemark;

    /**
     * 所属区域ID
     */
    private String areaId;

    /**
     * 冗余所属区域编码
     */
    private String areaCode;

    /**
     * 冗余所属区域名称
     */
    private String areaName;

    private String cityId;

    private String cityCode;

    private String cityName;

    /**
     * 所属项目ID
     */
    private String projectId;

    /**
     * 冗余所属项目编码
     */
    private String projectCode;

    /**
     * 冗余所属项目名称
     */
    private String projectName;

    /**
     * 所属地块ID
     */
    private String massifId;

    /**
     * 冗余所属地块编码
     */
    private String massifCode;

    /**
     * 冗余所属地块名称
     */
    private String massifName;

    /**
     * 所属分期ID
     */
    private String stagingId;

    /**
     * 冗余所属分期编码
     */
    private String stagingCode;

    /**
     * 冗余所属分期名称
     */
    private String stagingName;

    /**
     * 管家历史ID
     */
    private String housekeeperHistoryId;

    /**
     * 业态属性：商办、住宅、公寓等
     */
    private String formatAttribute;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 网格变更类型
     */
    private String gridChangeType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getGridChangeType() {
        return gridChangeType;
    }

    public void setGridChangeType(String gridChangeType) {
        this.gridChangeType = gridChangeType;
    }

    public GridBasicInfoHistoryDTO() {
    }

    /**
     * 获取网格主键ID
     *
     * @return gridId - 网格主键ID
     */
    public String getGridId() {
        return this.gridId;
    }

    /**
     * 设置网格主键ID
     *
     * @param gridId 网格主键ID
     */
    public void setGridId(String gridId) {
        this.gridId = gridId;
    }


    /**
     * 获取网格编码
     *
     * @return gridCode - 网格编码
     */
    public String getGridCode() {
        return this.gridCode;
    }

    /**
     * 设置网格编码
     *
     * @param gridCode 网格编码
     */
    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }


    /**
     * 获取网格名称
     *
     * @return gridName - 网格名称
     */
    public String getGridName() {
        return this.gridName;
    }

    /**
     * 设置网格名称
     *
     * @param gridName 网格名称
     */
    public void setGridName(String gridName) {
        this.gridName = gridName;
    }


    /**
     * 获取网格类型：公区网格、楼栋网格、服务中心网格
     *
     * @return gridType - 网格类型：公区网格、楼栋网格、服务中心网格
     */
    public String getGridType() {
        return this.gridType;
    }

    /**
     * 设置网格类型：公区网格、楼栋网格、服务中心网格
     *
     * @param gridType 网格类型：公区网格、楼栋网格、服务中心网格
     */
    public void setGridType(String gridType) {
        this.gridType = gridType;
    }


    /**
     * 获取网格覆盖范围
     *
     * @return gridRange - 网格覆盖范围
     */
    public String getGridRange() {
        return this.gridRange;
    }

    /**
     * 设置网格覆盖范围
     *
     * @param gridRange 网格覆盖范围
     */
    public void setGridRange(String gridRange) {
        this.gridRange = gridRange;
    }


    /**
     * 获取网格备注
     *
     * @return gridRemark - 网格备注
     */
    public String getGridRemark() {
        return this.gridRemark;
    }

    /**
     * 设置网格备注
     *
     * @param gridRemark 网格备注
     */
    public void setGridRemark(String gridRemark) {
        this.gridRemark = gridRemark;
    }


    /**
     * 获取所属区域ID
     *
     * @return areaId - 所属区域ID
     */
    public String getAreaId() {
        return this.areaId;
    }

    /**
     * 设置所属区域ID
     *
     * @param areaId 所属区域ID
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }


    /**
     * 获取冗余所属区域编码
     *
     * @return areaCode - 冗余所属区域编码
     */
    public String getAreaCode() {
        return this.areaCode;
    }

    /**
     * 设置冗余所属区域编码
     *
     * @param areaCode 冗余所属区域编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * 获取冗余所属区域名称
     *
     * @return areaName - 冗余所属区域名称
     */
    public String getAreaName() {
        return this.areaName;
    }

    /**
     * 设置冗余所属区域名称
     *
     * @param areaName 冗余所属区域名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


    /**
     * 获取所属项目ID
     *
     * @return projectId - 所属项目ID
     */
    public String getProjectId() {
        return this.projectId;
    }

    /**
     * 设置所属项目ID
     *
     * @param projectId 所属项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    /**
     * 获取冗余所属项目编码
     *
     * @return projectCode - 冗余所属项目编码
     */
    public String getProjectCode() {
        return this.projectCode;
    }

    /**
     * 设置冗余所属项目编码
     *
     * @param projectCode 冗余所属项目编码
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }


    /**
     * 获取冗余所属项目名称
     *
     * @return projectName - 冗余所属项目名称
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * 设置冗余所属项目名称
     *
     * @param projectName 冗余所属项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    /**
     * 获取所属地块ID
     *
     * @return massifId - 所属地块ID
     */
    public String getMassifId() {
        return this.massifId;
    }

    /**
     * 设置所属地块ID
     *
     * @param massifId 所属地块ID
     */
    public void setMassifId(String massifId) {
        this.massifId = massifId;
    }


    /**
     * 获取冗余所属地块编码
     *
     * @return massifCode - 冗余所属地块编码
     */
    public String getMassifCode() {
        return this.massifCode;
    }

    /**
     * 设置冗余所属地块编码
     *
     * @param massifCode 冗余所属地块编码
     */
    public void setMassifCode(String massifCode) {
        this.massifCode = massifCode;
    }


    /**
     * 获取冗余所属地块名称
     *
     * @return massifName - 冗余所属地块名称
     */
    public String getMassifName() {
        return this.massifName;
    }

    /**
     * 设置冗余所属地块名称
     *
     * @param massifName 冗余所属地块名称
     */
    public void setMassifName(String massifName) {
        this.massifName = massifName;
    }


    /**
     * 获取所属分期ID
     *
     * @return stagingId - 所属分期ID
     */
    public String getStagingId() {
        return this.stagingId;
    }

    /**
     * 设置所属分期ID
     *
     * @param stagingId 所属分期ID
     */
    public void setStagingId(String stagingId) {
        this.stagingId = stagingId;
    }


    /**
     * 获取冗余所属分期编码
     *
     * @return stagingCode - 冗余所属分期编码
     */
    public String getStagingCode() {
        return this.stagingCode;
    }

    /**
     * 设置冗余所属分期编码
     *
     * @param stagingCode 冗余所属分期编码
     */
    public void setStagingCode(String stagingCode) {
        this.stagingCode = stagingCode;
    }


    /**
     * 获取冗余所属分期名称
     *
     * @return stagingName - 冗余所属分期名称
     */
    public String getStagingName() {
        return this.stagingName;
    }

    /**
     * 设置冗余所属分期名称
     *
     * @param stagingName 冗余所属分期名称
     */
    public void setStagingName(String stagingName) {
        this.stagingName = stagingName;
    }


    /**
     * 获取管家历史ID
     *
     * @return housekeeperHistoryId - 管家历史ID
     */
    public String getHousekeeperHistoryId() {
        return this.housekeeperHistoryId;
    }

    /**
     * 设置管家历史ID
     *
     * @param housekeeperHistoryId 管家历史ID
     */
    public void setHousekeeperHistoryId(String housekeeperHistoryId) {
        this.housekeeperHistoryId = housekeeperHistoryId;
    }


    /**
     * 获取业态属性：商办、住宅、公寓等
     *
     * @return formatAttribute - 业态属性：商办、住宅、公寓等
     */
    public String getFormatAttribute() {
        return this.formatAttribute;
    }

    /**
     * 设置业态属性：商办、住宅、公寓等
     *
     * @param formatAttribute 业态属性：商办、住宅、公寓等
     */
    public void setFormatAttribute(String formatAttribute) {
        this.formatAttribute = formatAttribute;
    }


    /**
     * 获取租户ID
     *
     * @return tenantId - 租户ID
     */
    public String getTenantId() {
        return this.tenantId;
    }

    /**
     * 设置租户ID
     *
     * @param tenantId 租户ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}