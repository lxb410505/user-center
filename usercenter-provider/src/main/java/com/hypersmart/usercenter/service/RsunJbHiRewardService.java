package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.dto.CoinStatisticsListDTO;
import com.hypersmart.usercenter.model.RsunJbHiReward;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface  RsunJbHiRewardService extends IGenericService<String, RsunJbHiReward>  {

    //查询员工各月份金币数列表
    PageList<CoinStatisticsListDTO> coinStatisticsList(QueryFilter queryFilter);

    //导出
    void exportExcel(QueryFilter filter, HttpServletResponse response) throws Exception;


}
