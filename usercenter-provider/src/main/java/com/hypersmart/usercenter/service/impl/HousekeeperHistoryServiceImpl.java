package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.HousekeeperHistory;
import com.hypersmart.usercenter.mapper.HousekeeperHistoryMapper;
import com.hypersmart.usercenter.service.HousekeeperHistoryService;
import org.springframework.stereotype.Service;

/**
 * 管家历史记录表-管家快照
 *
 * @author jiangxiaoxuan
 * @email 111111@163.com
 * @date 2019-01-17 17:40:36
 */
@Service("housekeeperHistoryServiceImpl")
public class HousekeeperHistoryServiceImpl extends GenericService<String, HousekeeperHistory> implements HousekeeperHistoryService {

    public HousekeeperHistoryServiceImpl(HousekeeperHistoryMapper mapper) {
        super(mapper);
    }
}