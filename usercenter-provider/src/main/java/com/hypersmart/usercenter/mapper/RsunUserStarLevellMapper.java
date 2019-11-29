package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.UcUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface  RsunUserStarLevellMapper extends GenericMapper<RsunUserStarLevell> {


    List<RsunUserStarLevel> getRsunUserStarLevelList(Map<String, Object> params);
    /**
     * 根据员工账号查询userId
     * @param account
     * @return
     */
    String getUserIdByAccount(String account);

    /**
     * 金币相关
     * @param map
     * @return
     */
    List<Map<String, Object>> queryYear(Map<String, Object> map);
    List<Map<String, Object>> queryMonth(Map<String, Object> map);

    List<Map<String, Object>> queryYear4job();
    List<Map<String, Object>> queryMonth4job(@Param("s") String s,@Param("s2") String s2);
    int insertYear(Map map);
    int insertMonth(Map map);
    int deleteGoldYear();
    int deleteGoldMonth(Map map);

    /**
     * 徽章相关
     * @param map
     * @return
     */
    List<Map<String, Object>> queryYear4Badge(Map<String, Object> map);
    List<Map<String, Object>> queryMonth4Badge(Map<String, Object> map);

    List<Map<String, Object>> queryYear4job4Badge();
    List<Map<String, Object>> queryMonth4job4Badge(@Param("s") String s);
    int insertYear4Badge(Map map);
    int insertMonth4Badge(Map map);
    int deleteGoldYear4Badge();
    int deleteGoldMonth4Badge();
    int insertGoldRecord(Map<String, Object> map);
    int updateSingleRecord(Map<String, Object> map);
    int insertBadgeHistory(Map<String, Object> map);



}
