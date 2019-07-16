package com.hypersmart.usercenter.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hypersmart.framework.model.GenericDTO;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 网格基础信DTO
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */

public class GridBasicInfoDTO extends GenericDTO<String> implements Serializable {
	//用户ID
	private String account;

	//网格编码
	private String gridCode;

	//网格名称
	private String gridName;

	//网格类型：公区网格、楼栋网格、服务中心网格
	private String gridType;

	//网格覆盖范围（json格式）
	private String gridRange;

	//网格备注
	private String gridRemark;

	//所属区域ID
	private String areaId;

	//所属区域名称
	private String areaName;

	//所属项目ID
	private String projectId;

	//所属项目ID
	private String projectName;

	//所属地块ID
	private String massifId;

	//所属地块ID
	private String massifName;

	//所属分期ID
	private String stagingId;

	//所属分期ID
	private String stagingName;

	//城市id
	private String cityId;

	//城市名称
	private String cityName;

	//管家ID（主数据用户主键）
	private String housekeeperId;

	//管家姓名
	private String housekeeperName;

	//网格更新次数：只记录产生过历史记录/工单的次数"
	private Integer updateTimes;

	//一级业态属性
	private String formatAttribute;

	//二级业态属性
	private String secondFormatAttribute;

	//三级业态属性
	private String thirdFormatAttribute;

	private String formatAttributeName;

	private List<String> stagingIds;

	private String serviceGridId;

	//首次创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creationDate;

	//创建人
	private String createdBy;

	//上次修改时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updationDate;

	//更新人
	private String updatedBy;

	//入库的时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date rowTime;

	//记录的版本号
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date rowVersion;

	//租户ID
	private String tenantId;

	//启用标记（0：停用 1：启用）
	private Integer enabledFlag;

	//是否删除（0：正常 1：删除）
	private Integer isDeleted;

	////////////对接K2申请人信息///////////
	// 岗位id
	private String postId;

	// 岗位名称
	private String postName;

	// 原因
	private String reason;

	// 操作id集合
	private String[] ids;

	// 管家解除网格关联  和  管家关联网格 使用
	List<GridBasicInfoBO> gridBasicInfoBOList;

	//网格Id
	private String gridId;

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

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

	public String getGridRange() {
		return gridRange;
	}

	public void setGridRange(String gridRange) {
		this.gridRange = gridRange;
	}

	public String getGridRemark() {
		return gridRemark;
	}

	public void setGridRemark(String gridRemark) {
		this.gridRemark = gridRemark;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMassifId() {
		return massifId;
	}

	public void setMassifId(String massifId) {
		this.massifId = massifId;
	}

	public String getMassifName() {
		return massifName;
	}

	public void setMassifName(String massifName) {
		this.massifName = massifName;
	}

	public String getStagingId() {
		return stagingId;
	}

	public void setStagingId(String stagingId) {
		this.stagingId = stagingId;
	}

	public String getStagingName() {
		return stagingName;
	}

	public void setStagingName(String stagingName) {
		this.stagingName = stagingName;
	}

	public String getHousekeeperId() {
		return housekeeperId;
	}

	public void setHousekeeperId(String housekeeperId) {
		this.housekeeperId = housekeeperId;
	}

	public String getHousekeeperName() {
		return housekeeperName;
	}

	public void setHousekeeperName(String housekeeperName) {
		this.housekeeperName = housekeeperName;
	}

	public Integer getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(Integer updateTimes) {
		this.updateTimes = updateTimes;
	}

	public String getFormatAttribute() {
		return formatAttribute;
	}

	public void setFormatAttribute(String formatAttribute) {
		this.formatAttribute = formatAttribute;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getRowTime() {
		return rowTime;
	}

	public void setRowTime(Date rowTime) {
		this.rowTime = rowTime;
	}

	public Date getRowVersion() {
		return rowVersion;
	}

	public void setRowVersion(Date rowVersion) {
		this.rowVersion = rowVersion;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSecondFormatAttribute() {
		return secondFormatAttribute;
	}

	public void setSecondFormatAttribute(String secondFormatAttribute) {
		this.secondFormatAttribute = secondFormatAttribute;
	}

	public String getThirdFormatAttribute() {
		return thirdFormatAttribute;
	}

	public void setThirdFormatAttribute(String thirdFormatAttribute) {
		this.thirdFormatAttribute = thirdFormatAttribute;
	}

	public String getFormatAttributeName() {
		return formatAttributeName;
	}

	public void setFormatAttributeName(String formatAttributeName) {
		this.formatAttributeName = formatAttributeName;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public List<GridBasicInfoBO> getGridBasicInfoBOList() {
		return gridBasicInfoBOList;
	}

	public void setGridBasicInfoBOList(List<GridBasicInfoBO> gridBasicInfoBOList) {
		this.gridBasicInfoBOList = gridBasicInfoBOList;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<String> getStagingIds() {
		return stagingIds;
	}

	public void setStagingIds(List<String> stagingIds) {
		this.stagingIds = stagingIds;
	}

	public String getServiceGridId() {
		return serviceGridId;
	}

	public void setServiceGridId(String serviceGridId) {
		this.serviceGridId = serviceGridId;
	}
}
