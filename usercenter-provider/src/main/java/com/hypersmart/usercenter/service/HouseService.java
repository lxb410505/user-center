package com.hypersmart.usercenter.service;


import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.House;

import java.util.Map;

/**
 * 【基础信息】房产
 *
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */
public interface HouseService extends IGenericService<String, House> {
    PageList<House> list (QueryFilter queryFilter);
    Map selectGridBuilding (String  id);
    Map selectBuildingUnit (String  id);
}

