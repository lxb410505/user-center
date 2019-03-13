package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcDemensionMapper;
import com.hypersmart.usercenter.model.UcDemension;
import com.hypersmart.usercenter.service.UcDemensionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维度管理
 *
 * @author yang
 * @email yang17766@sina.com
 * @date 2019-03-13 13:49:47
 */
@Service("ucDemensionServiceImpl")
public class UcDemensionServiceImpl extends GenericService<String, UcDemension> implements UcDemensionService {

    public UcDemensionServiceImpl(UcDemensionMapper mapper) {
        super(mapper);
    }

    @Override
    public List<UcDemension> queryByCode(String code) {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("CODE_",code, QueryOP.EQUAL,FieldRelation.AND);
        queryFilter.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
        List<UcDemension> demensions = this.query(queryFilter).getRows();
        return demensions;
    }

    @Override
    public List<UcDemension> queryByNotCode(String code) {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("CODE_",code, QueryOP.NOT_EQUAL,FieldRelation.AND);
        queryFilter.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
        List<UcDemension> demensions = this.query(queryFilter).getRows();
        return demensions;
    }
}
