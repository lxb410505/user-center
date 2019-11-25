package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.Satisfaction;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface  RsunUserStarlLevelService extends IGenericService<String, RsunUserStarLevell> {
    public PageList<Map<String, Object>> quertList(QueryFilter queryFilterqueryFilter);
    public PageList<Map<String, Object>> quertList4Badge(QueryFilter queryFilterqueryFilter);
    int insertBadge(Map map);
}
