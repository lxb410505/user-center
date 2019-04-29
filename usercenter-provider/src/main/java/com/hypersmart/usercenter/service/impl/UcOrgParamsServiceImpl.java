package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcOrgParams;
import com.hypersmart.usercenter.mapper.UcOrgParamsMapper;
import com.hypersmart.usercenter.service.UcOrgParamsService;
import org.springframework.stereotype.Service;

/**
 * 组织参数
 *
 * @author lily
 * @email lily@qq.com
 * @date 2019-04-29 11:42:50
 */
@Service("ucOrgParamsServiceImpl")
public class UcOrgParamsServiceImpl extends GenericService<String, UcOrgParams> implements UcOrgParamsService {

    public UcOrgParamsServiceImpl(UcOrgParamsMapper mapper) {
        super(mapper);
    }
}