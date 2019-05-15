package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.usercenter.mapper.SatisfactionMapper;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.SatisfactionService;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.service.UcOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author smh
 * @email smh
 * @date 2019-05-14 17:23:33
 */
@Service("satisfactionServiceImpl")
public class SatisfactionServiceImpl extends GenericService<String, Satisfaction> implements SatisfactionService {

    @Resource
    UcOrgService ucOrgService;

    @Resource
    SatisfactionMapper satisfactionMapper;

    public SatisfactionServiceImpl(SatisfactionMapper mapper) {
        super(mapper);
    }

    @Override
    public List<Satisfaction> getSatisfactionDetail(String orgId, String time) {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("PARENT_ID_", orgId, QueryOP.EQUAL, FieldRelation.AND);
        queryFilter.addFilter("IS_DELE_", "1", QueryOP.EQUAL, FieldRelation.AND);
        List<UcOrg> ucOrgList = ucOrgService.query(queryFilter).getRows();
        List<Satisfaction> satisfactions = new ArrayList<>();
        if (ucOrgList!=null&&ucOrgList.size()>0){
            satisfactions = satisfactionMapper.getSatisfactionDetail(ucOrgList, time);
        }
        return satisfactions;
    }
}