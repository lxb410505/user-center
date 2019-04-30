package com.hypersmart.usercenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GroupIdentity {
	public static final String TYPE_USER = "user";
	public static final String TYPE_GROUP = "group";
	public static final String TYPE_ORG = "org";
	public static final String TYPE_JOB = "job";
	public static final String TYPE_POS = "pos";
	public static final String TYPE_ROLE = "role";
	@ApiModelProperty(name = "id", notes = "用户组id（如用户id）")
	private String id;
	@ApiModelProperty(name = "name", notes = "用户组名称（如用户名称）")
	private String name;
	@ApiModelProperty(name = "code", notes = "用户组编码（如用户账号）")
	private String code;
	@ApiModelProperty(name = "groupType", notes = "用户组类型：user（用户）、org（组织）、pos（岗位）、role（角色）")
	private String groupType;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
}
