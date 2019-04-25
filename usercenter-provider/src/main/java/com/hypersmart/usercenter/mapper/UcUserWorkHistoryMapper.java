package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.HousekeeperHistory;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户上下班记录
 */
@Mapper
public interface UcUserWorkHistoryMapper extends GenericMapper<UcUserWorkHistory> {

}
