package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.Satisfaction;
import org.apache.ibatis.annotations.Param;
import com.hypersmart.usercenter.model.UcOrg;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author smh
 * @email smh
 * @date 2019-05-14 17:23:33
 */
public interface SatisfactionMapper extends GenericMapper<Satisfaction> {
    List<Satisfaction> getSatisfactionDetail(@Param("orgList")List<UcOrg> orgList,@Param("time")String time);
    void deleteByDate(@Param("date") String date);
    List<Satisfaction> getSatisfactionListByParam(@Param("value")Map value);
    List<Satisfaction> getGridSatisfaction(@Param("orgList")List<GridBasicInfo> list,@Param("time")String time);
    List<Satisfaction> getSingleSatisfaction(@Param("orgCode")String orgCode,@Param("time")String time);
    List<Satisfaction> getSatisfactionAvg(@Param("orgCodes")List<String> orgCodes,@Param("time")String time);
}
