package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.bo.GridRangeBO;
import com.hypersmart.usercenter.model.GridRange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网格覆盖范围表
 * 
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-17 12:48:37
 */
public interface GridRangeMapper extends GenericMapper<GridRange> {

    /**
     * 删除覆盖范围
     * @param ids
     * @return
     */
    Integer deleteRangeByGridIds(@Param("ids") String[] ids);

    /**
     * 获取分期下所有覆盖范围
     * @param gridRangeBO
     * @return
     */
    List<GridRange> getRange(GridRangeBO gridRangeBO);

}
