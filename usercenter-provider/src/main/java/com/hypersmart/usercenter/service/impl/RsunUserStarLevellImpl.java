package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.RsunUserStarLevellMapper;
import com.hypersmart.usercenter.mapper.UcUserMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.RsunUserStarlLevelService;
import com.hypersmart.usercenter.service.UcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rsunUserStarLevellImpl")
public class  RsunUserStarLevellImpl extends GenericService<String, RsunUserStarLevell> implements RsunUserStarlLevelService {

    public RsunUserStarLevellImpl(RsunUserStarLevellMapper rsunUserStarLevellMapper) {
        super(rsunUserStarLevellMapper);
    }
}
