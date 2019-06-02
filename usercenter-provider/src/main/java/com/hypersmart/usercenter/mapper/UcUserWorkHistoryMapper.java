package com.hypersmart.usercenter.mapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.GroupIdentity;
import com.hypersmart.usercenter.model.HousekeeperHistory;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户上下班记录
 */
@Mapper
public interface UcUserWorkHistoryMapper extends GenericMapper<UcUserWorkHistory> {
    List<Map<String,Object>> queryPage(Map<String,Object> map);

    int save(UcUserWorkHistory ucUserWorkHistory);

    String queryLatest(@Param("userId") String username);

    List<UcUserWorkHistory> queryUserWorkStatusList (@Param("userIds") List<String> userIds);
}
