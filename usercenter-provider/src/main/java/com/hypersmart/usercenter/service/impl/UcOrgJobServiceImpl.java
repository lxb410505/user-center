package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcOrgJob;
import com.hypersmart.usercenter.mapper.UcOrgJobMapper;
import com.hypersmart.usercenter.service.UcOrgJobService;
import org.springframework.stereotype.Service;

/**
 * 职务定义
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:44:42
 */
@Service("ucOrgJobServiceImpl")
public class UcOrgJobServiceImpl extends GenericService<String, UcOrgJob> implements UcOrgJobService {

    public UcOrgJobServiceImpl(UcOrgJobMapper mapper) {
        super(mapper);
    }
}