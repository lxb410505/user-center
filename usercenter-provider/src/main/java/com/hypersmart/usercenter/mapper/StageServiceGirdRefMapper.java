package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.StageServiceGirdRef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StageServiceGirdRefMapper extends GenericMapper<StageServiceGirdRef> {
    /**
     * 获取所有有所有服网格的地块
     * @param params
     * @return
     */
    List<String> getServiceGridByStagingIds(Map<String, Object> params);


    List<Map<String, Object>> getServiceGridByGridId(String id);

    Map<String,Object> getServiceGridIdByStagingId(String stagingId);

    String getServiceIdByStagingId(String stagingId);

    Map<String,Object>  getServiceGridIdByStagingIdNotEnableFlag(String id);

}
