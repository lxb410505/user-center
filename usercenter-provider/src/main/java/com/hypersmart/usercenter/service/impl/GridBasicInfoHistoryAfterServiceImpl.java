package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.GridBasicInfoHistoryAfterMapper;
import com.hypersmart.usercenter.model.GridBasicInfoHistoryAfter;
import com.hypersmart.usercenter.service.GridBasicInfoHistoryAfterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网格基础信息历史表-变化后快照
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-11 10:23:28
 */
@Service("gridBasicInfoHistoryAfterServiceImpl")
public class GridBasicInfoHistoryAfterServiceImpl extends GenericService<String, GridBasicInfoHistoryAfter> implements GridBasicInfoHistoryAfterService {

    @Autowired
    private GridBasicInfoHistoryAfterMapper gridBasicInfoHistoryAfterMapper;

    public GridBasicInfoHistoryAfterServiceImpl(GridBasicInfoHistoryAfterMapper mapper) {
        super(mapper);
    }

    @Override
    public GridBasicInfoHistoryAfter findDataByGridId(String gridId) {
        return gridBasicInfoHistoryAfterMapper.findDataByGridId(gridId);
    }
}