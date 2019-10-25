package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.RsunJbHiRewardMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.rsunJbHiReward;
import com.hypersmart.usercenter.service.RsunJbHiRewardService;
import com.hypersmart.usercenter.service.RsunUserStarlLevelService;
import org.springframework.stereotype.Service;

@Service("rsunJbHiRewardServiceImpl")
public class RsunJbHiRewardServiceImpl extends GenericService<String, rsunJbHiReward> implements RsunJbHiRewardService {
    public RsunJbHiRewardServiceImpl(RsunJbHiRewardMapper genericMapper) {
        super(genericMapper);
    }
}
