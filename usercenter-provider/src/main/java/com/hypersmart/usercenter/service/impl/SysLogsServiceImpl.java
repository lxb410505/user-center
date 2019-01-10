package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.SysLogsMapper;
import com.hypersmart.usercenter.model.SysLogs;
import com.hypersmart.usercenter.service.SysLogsService;
import org.springframework.stereotype.Service;

@Service("sysLogsServiceImpl")
public class SysLogsServiceImpl extends GenericService<String, SysLogs> implements SysLogsService {

    public SysLogsServiceImpl(SysLogsMapper sysLogsDao) {
        super(sysLogsDao);
    }
}
