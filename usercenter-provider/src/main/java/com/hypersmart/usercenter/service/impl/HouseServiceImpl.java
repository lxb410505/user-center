package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.GenericService;

import com.hypersmart.usercenter.mapper.HouseMapper;
import com.hypersmart.usercenter.model.House;
import com.hypersmart.usercenter.service.HouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 【基础信息】房产
 *
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */
@Service("houseServiceImpl")

public class HouseServiceImpl extends GenericService<String, House> implements HouseService {

    public HouseServiceImpl(HouseMapper mapper) {
        super(mapper);
    }

    @Resource
    HouseMapper houseMapper ;

    @Override
    public PageList<House> list(QueryFilter queryFilter) {

        return null;
    }

    @Override
    public Map selectGridBuilding(String id) {

        return houseMapper.selectGridBuilding(id);
    }
}