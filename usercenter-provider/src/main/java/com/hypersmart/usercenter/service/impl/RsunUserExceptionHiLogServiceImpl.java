package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.RsunUserExceptionHiLogMapper;
import com.hypersmart.usercenter.model.RsunUserExceptionHiLog;
import com.hypersmart.usercenter.model.rsunJbHiReward;
import com.hypersmart.usercenter.service.RsunUserExceptionHiLogService;
import org.springframework.stereotype.Service;

@Service("rsunUserExceptionHiLogServiceImpl")
public class RsunUserExceptionHiLogServiceImpl extends GenericService<String, RsunUserExceptionHiLog> implements RsunUserExceptionHiLogService {
    public RsunUserExceptionHiLogServiceImpl(RsunUserExceptionHiLogMapper genericMapper) {
        super(genericMapper);
    }
}
