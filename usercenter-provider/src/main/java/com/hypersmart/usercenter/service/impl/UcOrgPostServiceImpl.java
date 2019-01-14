package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcOrgPost;
import com.hypersmart.usercenter.mapper.UcOrgPostMapper;
import com.hypersmart.usercenter.service.UcOrgPostService;
import org.springframework.stereotype.Service;

/**
 * 部门岗位
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-14 19:43:26
 */
@Service("ucOrgPostServiceImpl")
public class UcOrgPostServiceImpl extends GenericService<String, UcOrgPost> implements UcOrgPostService {

    public UcOrgPostServiceImpl(UcOrgPostMapper mapper) {
        super(mapper);
    }
}