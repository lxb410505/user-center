package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.usercenter.model.Parking;
import com.hypersmart.framework.service.IGenericService;

/**
 * 【基础信息】停车位
 *
 * @author zcf
 * @email 1490318946@qq.com
 * @date 2019-08-21 16:19:42
 */
public interface ParkingService extends IGenericService<String, Parking> {

    CommonResult<String> addParking(Parking model);

    /**
     *  更新指定id的 【基础信息】停车位 信息（更新全部信息）
     * @param model
     */
    CommonResult<String> updateParking(Parking model);

}

