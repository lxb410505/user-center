package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcUserConfigMapper;
import com.hypersmart.usercenter.model.UcUserConfig;
import com.hypersmart.usercenter.service.UcUserConfigService;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author Magellan
 * @email 121935403@qq.com
 * @date 2019-04-30 15:13:12
 */
@Service("ucUserConfigServiceImpl")
public class UcUserConfigServiceImpl extends GenericService<String, UcUserConfig> implements UcUserConfigService {

    public UcUserConfigServiceImpl(UcUserConfigMapper mapper) {
        super(mapper);
    }
}