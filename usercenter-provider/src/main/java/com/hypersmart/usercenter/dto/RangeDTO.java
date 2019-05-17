package com.hypersmart.usercenter.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hypersmart.framework.model.GenericDTO;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * 网格基础信DTO
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */

public class RangeDTO implements Serializable {
	private String checked;

	private String code;

	private String id;

	private String Level;

	private String name;

	private String parentId;

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RangeDTO rangeDTO = (RangeDTO) o;
		return Objects.equals(id, rangeDTO.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
