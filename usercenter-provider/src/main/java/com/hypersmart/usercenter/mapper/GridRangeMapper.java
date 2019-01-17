package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.GridRange;

/**
 * 网格覆盖范围表
 * 
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-17 12:48:37
 */
public interface GridRangeMapper extends GenericMapper<GridRange> {

    Integer deleteRangeByGridId(String gridId);

}
