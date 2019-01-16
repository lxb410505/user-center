package com.hypersmart.usercenter.service;

import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.GridBasicInfoHistoryAfter;

/**
 * 网格基础信息历史表-变化后快照
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:23:28
 */
public interface GridBasicInfoHistoryAfterService extends IGenericService<String, GridBasicInfoHistoryAfter> {
    GridBasicInfoHistoryAfter findDataByGridId(String gridId);

}

