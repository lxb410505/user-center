package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.model.GridRange;
import com.hypersmart.framework.service.IGenericService;

/**
 * 网格覆盖范围表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-17 12:48:37
 */
public interface GridRangeService extends IGenericService<String, GridRange> {

    /**
     * 删除历史记录
     */
    Integer deleteRangeByGridId(String gridId);


    /**
     * 记录数据
     * @param gridRangeRecord
     * @return
     */
    Integer recordRange(String gridRangeRecord,String gridId);

}

