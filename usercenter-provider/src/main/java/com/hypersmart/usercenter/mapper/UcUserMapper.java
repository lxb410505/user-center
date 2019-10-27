package com.hypersmart.usercenter.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.usercenter.dto.rsunJbDTO;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@Mapper
public interface  UcUserMapper extends GenericMapper<UcUser> {

    List<Map<String, Object>> quertListFive(Map<String, Object> map);

    List<Map<String, Object>> quertListFour(Map<String, Object> map);

    List<Map<String, Object>> quertListThree(Map<String, Object> map);

    List<Map<String, Object>> quertListTwo(Map<String, Object> map);

    List<UcUser> queryUserByOrgIdList(@Param("ucOrgList")List<UcOrg> ucOrgList, @Param("fullname")String fullname, @Param("mobile")String mobile);


    UcUser getUserByUnitId(@Param("unitId") String unitId,@Param("unitType")String unitType);

    /**
     * 根据组织和职务查询对应组织中的用户
     * @param params
     * @return
     */
    List<UcUser> searchUserByCondition(Map<String, Object> params);

    /**
     * 查询user的岗位集合
     * @param uId
     * @param oId
     * @return
     */
    List<String> serchUserJobsByUserId(@Param("uId") String uId,@Param("oId") String oId);

    /**
     * 根据职务编码查询对应的用户
     * @param params
     * @return
     */
    List<UcUser> getByJobCodes(Map<String, Object> params);

    UcUser getByAccount(@Param("account")String account);

    UcUser getUserByDivideId(@Param("divideId") String unitId,@Param("gridType")String unitType);

    List<String> getSkillCodebyCategory(@Param("category")String category);

    String getdikuai(@Param("id") String id);

    RsunUserStarLevel getkuozhan(@Param("account") String account);

    void insertt(@Param("ucUser") UcUser ucUser);

    ArrayList<RsunUserStarLevel> getlist(@Param("pagebean") PageBean pagebean);

    RsunUserStarLevel getxzjb( @Param("UserCode") String UserCode);

    List<rsunJbHiReward> getmoney( HashMap<String, String> map);

    List<JinBiJiLv> getUserCoinHisRecordByUserCode(@Param("userCode") String userCode);

    List<UcUser> getaa();

    Boolean insertadd(rsunJbDTO reward);

    void updatemoney(@Param("reward") rsunJbHiReward reward);

    String getname(@Param("reward") String ucUserId);

    List<UcUser> getzh(@Param("getname") String getname);

    String getid(@Param("zhanghao") String value);
}
