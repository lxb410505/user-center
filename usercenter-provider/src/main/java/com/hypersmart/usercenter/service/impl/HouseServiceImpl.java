package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.framework.service.GenericService;

import com.hypersmart.usercenter.mapper.HouseMapper;
import com.hypersmart.usercenter.model.House;
import com.hypersmart.usercenter.service.HouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
    public  PageList<Map<String, Object>> list(QueryFilter queryFilter) {
        //todo
        //带查询条件的待完善
        PageBean pageBean = queryFilter.getPageBean();
        Map<String, Object> params = queryFilter.getParams();
        QueryFilter queryFilter2 = QueryFilter.build();

        if(params.get("massifId")!=null){
            queryFilter2.addFilter("gbi.staging_id", params.get("massifId"), QueryOP.EQUAL, FieldRelation.AND);

        }

        if(params.get("gridId")!=null){
            queryFilter2.addFilter("gr.id", params.get("gridId"), QueryOP.EQUAL, FieldRelation.AND);


        }
        //分页
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }

        List<Map<String, Object>> query=houseMapper.list(queryFilter2.getParams());
        return new PageList<>(query);
    }

    @Override
    public Map selectGridBuilding(String id) {

        return houseMapper.selectGridBuilding(id);
    }

    @Override
    public Map selectBuildingUnit(String id) {
        return houseMapper.selectBuildingUnit(id);
    }
}