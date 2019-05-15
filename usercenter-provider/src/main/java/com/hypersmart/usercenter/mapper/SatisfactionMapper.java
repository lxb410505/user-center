package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.Satisfaction;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author magellan
 * @email magellan
 * @date 2019-05-14 13:37:39
 */
public interface SatisfactionMapper extends GenericMapper<Satisfaction> {
    void deleteByDate(@Param("date") String date);
}
