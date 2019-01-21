package com.hypersmart.usercenter.bo;


import com.hypersmart.framework.model.GenericBO;

/**
 * 网格覆盖范围BO
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */

public class GridRangeBO extends GenericBO{

    //网格id
    private String gridId;

    //分期id
    private String stagingId;

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getStagingId() {
        return stagingId;
    }

    public void setStagingId(String stagingId) {
        this.stagingId = stagingId;
    }
}
