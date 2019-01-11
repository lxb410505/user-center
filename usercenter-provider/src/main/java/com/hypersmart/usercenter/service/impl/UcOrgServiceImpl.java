package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织架构
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */
@Service("ucOrgServiceImpl")
public class UcOrgServiceImpl extends GenericService<String, UcOrg> implements UcOrgService {

    private UcOrgMapper ucOrgMapper;

    public UcOrgServiceImpl(UcOrgMapper mapper) {
        super(mapper);
        this.ucOrgMapper = mapper;
    }

    @Override
    public List<UcOrg> getOrg(List<UcOrgUser> list) {
        return ucOrgMapper.getOrgList(list);
    }

    @Override
    public List<UcOrg> getChildrenOrg(UcOrg ucOrg) {
        return ucOrgMapper.getChildrenOrg(ucOrg);
    }
}