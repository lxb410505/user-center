package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.TgeSignificantQuality;

import java.util.List;
import java.util.Map;

/**
 * 重大事件质量
 * 
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:07:53
 */
public interface TgeSignificantQualityMapper extends GenericMapper<TgeSignificantQuality> {
    List<TgeSignificantQuality> queryList(Map<String, Object> map);

    Long checkExists(List<String> strings);
}
