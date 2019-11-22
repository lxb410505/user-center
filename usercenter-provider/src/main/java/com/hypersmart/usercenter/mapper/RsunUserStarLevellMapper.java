package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.UcUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface  RsunUserStarLevellMapper extends GenericMapper<RsunUserStarLevell> {

    List<Map<String, Object>> queryYear(Map<String, Object> map);
    List<Map<String, Object>> queryMonth(Map<String, Object> map);

    List<Map<String, Object>> queryYear4job();
    List<Map<String, Object>> queryMonth4job(@Param("S") String s);
    int insertYear(Map map);
    int insertMonth(Map map);
    int deleteGoldYear();
    int deleteGoldMonth();

}
