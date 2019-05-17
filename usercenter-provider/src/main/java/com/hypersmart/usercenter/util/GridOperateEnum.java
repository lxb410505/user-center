package com.hypersmart.usercenter.util;

/**
 * 网格操作类型枚举
 *
 * @author han
 * @date 2019-03-21
 */
public enum GridOperateEnum {

	NEW_GRID("newGrid", "新增网格"),
	CHANGE_SCOPE("changeScope", "网格覆盖范围变更"),
	CHANGE_HOUSEKEEPER("changeHousekeeper", "网格管家变更"),
	LINK_HOUSEKEEPER("linkHousekeeper", "管家关联网格"),
	DISABLE_GRID("disableGrid", "网格停用"),
	HOUSEKEEPER_DISASSOCIATED("housekeeperDisassociated", "解除网格管家关联");

	GridOperateEnum(String operateType, String description) {
		this.operateType = operateType;
		this.description = description;
	}

	/**
	 * 操作类型
	 */
	private String operateType;

	/**
	 * 描述
	 */
	private String description;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
