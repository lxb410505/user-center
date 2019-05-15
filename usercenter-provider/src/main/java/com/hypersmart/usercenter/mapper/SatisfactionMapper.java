package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.Satisfaction;
import org.apache.ibatis.annotations.Param;
import com.hypersmart.usercenter.model.UcOrg;

import java.util.List;

/**
 * 
 * 
 * @author smh
 * @email smh
 * @date 2019-05-14 17:23:33
 */
public interface SatisfactionMapper extends GenericMapper<Satisfaction> {
    List<Satisfaction> getSatisfactionDetail(List<UcOrg> orgList,String time);
    void deleteByDate(@Param("date") String date);
}
