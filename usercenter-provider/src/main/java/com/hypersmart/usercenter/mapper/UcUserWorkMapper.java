package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.UcUserWork;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户上下班记录
 */
@Mapper
public interface UcUserWorkMapper extends GenericMapper<UcUserWork> {

    void delByUserId(@Param("userId") String userId);

    String getStatus(@Param("userId") String userId);

    List<UcUserWork> queryUserWorkStatusList (@Param("userIds") List<String> userIds,@Param("status") String status);
}
