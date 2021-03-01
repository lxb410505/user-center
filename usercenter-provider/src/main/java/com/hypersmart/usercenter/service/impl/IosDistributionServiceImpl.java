package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.IosDistribution;
import com.hypersmart.usercenter.mapper.IosDistributionMapper;
import com.hypersmart.usercenter.service.IosDistributionService;
import org.springframework.stereotype.Service;

/**
 * ios编码分配表
 *
 * @author lily
 * @email lily
 * @date 2020-12-14 17:36:15
 */
@Service("iosDistributionServiceImpl")
public class IosDistributionServiceImpl extends GenericService<String, IosDistribution> implements IosDistributionService {

    public IosDistributionServiceImpl(IosDistributionMapper mapper) {
        super(mapper);
    }
}