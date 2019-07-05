package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.UcUserWorkHistoryMapper;
import com.hypersmart.usercenter.model.UcUserWork;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import com.hypersmart.usercenter.service.UcUserWorkService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Description:
 * @Author: liyong
 * @CreateDate: 2019/4/25 19:47
 * @Version: 1.0
 */
@Service("ucUserWorkServiceImpl")
public class UcUserWorkServiceImpl extends GenericService<String, UcUserWork> implements UcUserWorkService {


    public UcUserWorkServiceImpl(GenericMapper<UcUserWork> genericMapper) {
        super(genericMapper);
    }


}
