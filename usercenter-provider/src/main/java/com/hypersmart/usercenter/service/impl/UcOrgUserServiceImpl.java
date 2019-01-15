package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.mapper.UcOrgUserMapper;
import com.hypersmart.usercenter.service.UcOrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户组织关系
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:59:39
 */
@Service("ucOrgUserServiceImpl")
public class UcOrgUserServiceImpl extends GenericService<String, UcOrgUser> implements UcOrgUserService {

    @Autowired
    UcOrgUserMapper ucOrgUserMapper;

    public UcOrgUserServiceImpl(UcOrgUserMapper mapper) {
        super(mapper);
    }

    @Override
    public List<UcOrgUser> getUserOrg(String userId) {
        return ucOrgUserMapper.getUserOrg(userId);
    }

    @Override
    public List<String> getPostIdByjobCode(String joCode) {
        return ucOrgUserMapper.getPostIdByjobCode(joCode);
    }

    @Override
    public List<Map<String, String>> quertList(Map<String, String> map) {
        return ucOrgUserMapper.quertList(map);
    }
}