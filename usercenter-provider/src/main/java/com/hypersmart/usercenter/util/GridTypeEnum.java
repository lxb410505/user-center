package com.hypersmart.usercenter.util;


/**
 * 网格类型枚举
 *
 * @author han
 * @date 2019-3-21
 */
public enum GridTypeEnum {

	BUILDING_GRID("building_grid", "楼栋网格"),
	PUBLIC_AREA_GRID("public_area_grid", "公区网格"),
	SERVICE_CENTER_GRID("service_center_grid", "服务中心网格");

	GridTypeEnum(String gridType, String description) {
		this.gridType = gridType;
		this.description = description;
	}

	private String gridType;

	private String description;

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
