package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.TgeQualityCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:05:16
 */
public interface TgeQualityCheckMapper extends GenericMapper<TgeQualityCheck> {
    void deleteByDate(@Param("date") String date);
    List<TgeQualityCheck> initSelect(@Param("value") Map value);
}
