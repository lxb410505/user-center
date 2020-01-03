package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.dto.CoinStatisticsListDTO;
import com.hypersmart.usercenter.model.RsunJbHiReward;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface  RsunJbHiRewardMapper extends GenericMapper<RsunJbHiReward> {

    List<CoinStatisticsListDTO>  getCoinStatisticsList(Map<String, Object> params);

    List<CoinStatisticsListDTO>  queryExportExcel(List list);

    List<HashMap<String,String>> getEngineeringGrabOrdersDataInsight(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("list") List<String> list);
}
