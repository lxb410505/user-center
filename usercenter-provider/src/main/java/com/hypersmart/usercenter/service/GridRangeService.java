package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.bo.GridRangeBO;
import com.hypersmart.usercenter.model.GridRange;
import com.hypersmart.framework.service.IGenericService;

import java.util.List;

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
    Integer deleteRangeByGridIds(String[] ids);


    /**
     * 记录数据
     * @param gridRangeRecord
     * @return
     */
    Integer recordRange(String gridRangeRecord,String gridId);

    /**
     * 获取所有覆盖范围
     * @param gridRangeBO
     * @return
     */

    List<String> getAllRange(GridRangeBO gridRangeBO);

    /**
     * 判断房产是否已经被覆盖
     * @param gridRangeRecord
     * @return
     */
    boolean judgeExistHouse(String gridRangeRecord,String gridId, String stagingId);

}

