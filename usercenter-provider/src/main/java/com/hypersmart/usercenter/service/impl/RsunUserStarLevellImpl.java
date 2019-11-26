package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.controller.GridApprovalRecordController;
import com.hypersmart.usercenter.mapper.RsunUserStarLevellMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.service.RsunUserStarlLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("rsunUserStarLevellImpl")
public class RsunUserStarLevellImpl extends GenericService<String, RsunUserStarLevell> implements RsunUserStarlLevelService {
    private static final Logger logger = LoggerFactory.getLogger(GridApprovalRecordController.class);

    @Autowired
    private RsunUserStarLevellMapper rsunUserStarLevellMapper;

    public RsunUserStarLevellImpl(RsunUserStarLevellMapper rsunUserStarLevellMapper) {
        super(rsunUserStarLevellMapper);
    }

    @Override
    public PageList<RsunUserStarLevel> moneylist(QueryFilter queryFilter) {
        if (null != queryFilter) {
            PageBean pageBean = queryFilter.getPageBean();
            if (!com.hypersmart.base.util.BeanUtils.isEmpty(pageBean)) {
                PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
            } else {
                PageHelper.startPage(1, Integer.MAX_VALUE, false);
            }
            Map<String, Object> paramMap = queryFilter.getParams();
            List<RsunUserStarLevel> list = rsunUserStarLevellMapper.getRsunUserStarLevelList(paramMap);
            if (!CollectionUtils.isEmpty(list)) {
                return new PageList(list);
            } else {
                PageList<RsunUserStarLevel> pageList = new PageList<>();
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
                pageList.setPageSize(20);
                pageList.setPage(1);
                return pageList;
            }
        }
        PageList<RsunUserStarLevel> pageList = new PageList<>();
        pageList.setRows(new ArrayList<>());
        pageList.setTotal(0);
        pageList.setPageSize(20);
        pageList.setPage(1);
        return new PageList<>();
    }
}
