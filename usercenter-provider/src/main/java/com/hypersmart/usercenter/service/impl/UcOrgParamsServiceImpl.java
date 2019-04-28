package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcOrgParamsMapper;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.usercenter.service.UcOrgParamsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织参数
 *
 * @author zhoukai
 * @email 70*******@qq.com
 * @date 2019-04-28 10:15:44
 */
@Service("ucOrgParamsServiceImpl")
public class UcOrgParamsServiceImpl extends GenericService<String, UcOrgParams> implements UcOrgParamsService {

    public UcOrgParamsServiceImpl(UcOrgParamsMapper mapper) {
        super(mapper);
    }

    @Resource
    UcOrgParamsMapper ucOrgParamsMapper;

    @Override
    public List<UcOrgParams> selectByOrgId(String orgId) {
        return ucOrgParamsMapper.selectByOrgId(orgId);
    }
}