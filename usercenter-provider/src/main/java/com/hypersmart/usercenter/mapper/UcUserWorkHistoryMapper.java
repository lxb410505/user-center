package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.HousekeeperHistory;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户上下班记录
 */
@Mapper
public interface UcUserWorkHistoryMapper extends GenericMapper<UcUserWorkHistory> {
    List<Map<String,Object>> queryPage(Map<String,Object> map);

    int save(UcUserWorkHistory ucUserWorkHistory);
}
