package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.UcUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface  RsunUserStarLevellMapper extends GenericMapper<RsunUserStarLevell> {

    List<RsunUserStarLevel> getRsunUserStarLevelList(Map<String, Object> params);

}
