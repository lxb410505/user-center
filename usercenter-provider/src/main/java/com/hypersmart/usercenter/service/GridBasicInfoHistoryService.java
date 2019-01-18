package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoHistoryDTO;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.GridBasicInfoHistory;

/**
 * 网格基础信息历史表-变化前快照
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:03:42
 */
public interface GridBasicInfoHistoryService extends IGenericService<String, GridBasicInfoHistory> {
    /**
     * 保存网格历史
     * @param gridBasicInfoDTO
     * @return
     */
    GridErrorCode saveGridBasicInfoHistory(GridBasicInfo gridBasicInfoDTO);

    PageList<GridBasicInfoHistoryDTO> queryList(QueryFilter queryFilter);
}

