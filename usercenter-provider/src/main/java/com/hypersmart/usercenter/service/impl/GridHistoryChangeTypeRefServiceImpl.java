package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.GridHistoryChangeTypeRef;
import com.hypersmart.usercenter.mapper.GridHistoryChangeTypeRefMapper;
import com.hypersmart.usercenter.service.GridHistoryChangeTypeRefService;
import org.springframework.stereotype.Service;

/**
 * 网格历史网格内容变更类型关系表
 *
 * @author jiangxiaoxuan
 * @email 1111111@163.com
 * @date 2019-01-17 16:47:11
 */
@Service("gridHistoryChangeTypeRefServiceImpl")
public class GridHistoryChangeTypeRefServiceImpl extends GenericService<String, GridHistoryChangeTypeRef> implements GridHistoryChangeTypeRefService {

    public GridHistoryChangeTypeRefServiceImpl(GridHistoryChangeTypeRefMapper mapper) {
        super(mapper);
    }
}