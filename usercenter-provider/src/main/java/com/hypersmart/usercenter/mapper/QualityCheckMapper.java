package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.QualityCheck;
import com.hypersmart.usercenter.model.Satisfaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Magellan
 * @email Magellan
 * @date 2019-05-21 16:18:32
 */
public interface QualityCheckMapper extends GenericMapper<QualityCheck> {

    void deleteByDate(@Param("date") String date);
    List<Satisfaction> initSelect(@Param("value")Map value);

}
