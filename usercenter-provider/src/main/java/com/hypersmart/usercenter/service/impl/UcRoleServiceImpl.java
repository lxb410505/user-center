package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcRoleMapper;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.usercenter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ucRoleServiceImpl")
public class UcRoleServiceImpl extends GenericService<String, UcRole> implements UcRoleService {

    @Autowired
    UcRoleMapper ucRoleMapper;

    public UcRoleServiceImpl(UcRoleMapper mapper) {
        super(mapper);
        this.ucRoleMapper = mapper;
    }

    @Override
    public List<UcRole> getRolesByUserId(String userId) throws Exception {
        return this.ucRoleMapper.getRolesByUserId(userId);
    }
}
