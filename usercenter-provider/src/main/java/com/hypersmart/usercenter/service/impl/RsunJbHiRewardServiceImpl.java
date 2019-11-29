package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.dto.CoinStatisticsListDTO;
import com.hypersmart.usercenter.mapper.RsunJbHiRewardMapper;
import com.hypersmart.usercenter.model.RsunJbHiReward;
import com.hypersmart.usercenter.service.RsunJbHiRewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("rsunJbHiRewardServiceImpl")
public class  RsunJbHiRewardServiceImpl extends GenericService<String, RsunJbHiReward> implements RsunJbHiRewardService {


    @Autowired
    private RsunJbHiRewardMapper rsunJbHiRewardMapper;


    private static final Logger logger = LoggerFactory.getLogger(RsunUserStarLevellImpl.class);

    public RsunJbHiRewardServiceImpl(RsunJbHiRewardMapper genericMapper) {
        super(genericMapper);
    }


    /**
     * 查询员工各月份金币数列表
     * @param queryFilter
     * @return
     */
    @Override
    public PageList<CoinStatisticsListDTO> coinStatisticsList(QueryFilter queryFilter) {
        if (null != queryFilter) {
            PageBean pageBean = queryFilter.getPageBean();
            if (!com.hypersmart.base.util.BeanUtils.isEmpty(pageBean)) {
                PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
            } else {
                PageHelper.startPage(1, Integer.MAX_VALUE, false);
            }
            Map<String, Object> paramMap = queryFilter.getParams();
            List<CoinStatisticsListDTO> list = rsunJbHiRewardMapper.getCoinStatisticsList(paramMap);
            if (!CollectionUtils.isEmpty(list)) {
                return new PageList(list);
            } else {
                PageList<CoinStatisticsListDTO> pageList = new PageList<>();
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
                pageList.setPageSize(20);
                pageList.setPage(1);
                return pageList;
            }
        }
        PageList<CoinStatisticsListDTO> pageList = new PageList<>();
        pageList.setRows(new ArrayList<>());
        pageList.setTotal(0);
        pageList.setPageSize(20);
        pageList.setPage(1);
        return pageList;
    }


}
