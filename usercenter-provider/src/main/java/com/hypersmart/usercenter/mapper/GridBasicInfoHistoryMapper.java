package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.dto.GridBasicInfoHistoryDTO;
import com.hypersmart.usercenter.model.GridBasicInfoHistory;

import java.util.List;
import java.util.Map;

/**
 * 网格基础信息历史表-变化前快照
 * 
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:03:42
 */
public interface GridBasicInfoHistoryMapper extends GenericMapper<GridBasicInfoHistory> {

    List<GridBasicInfoHistoryDTO> queryList(Map<String, Object> params);
}
