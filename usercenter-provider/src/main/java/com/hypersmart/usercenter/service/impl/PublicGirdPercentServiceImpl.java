package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.PublicGirdPercent;
import com.hypersmart.usercenter.service.PublicGirdPercentService;
import org.springframework.stereotype.Service;

@Service("PublicGirdPercentServiceImpl")
public class PublicGirdPercentServiceImpl extends GenericService<String, PublicGirdPercent> implements PublicGirdPercentService {
    public PublicGirdPercentServiceImpl(GenericMapper<PublicGirdPercent> genericMapper) {
        super(genericMapper);
    }
}
