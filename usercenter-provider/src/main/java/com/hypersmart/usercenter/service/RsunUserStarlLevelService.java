package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface  RsunUserStarlLevelService extends IGenericService<String, RsunUserStarLevell> {

    /**
     * 获取员工信息中心列表
     * @param queryFilter
     * @return
     */
   PageList<RsunUserStarLevel> moneylist(QueryFilter queryFilter);

    /**
     * 修改员工等级
     * @param map
     * @return
     */
   CommonResult updateInfo(Map<String,Object> map);

    public PageList<Map<String, Object>> quertList(QueryFilter queryFilterqueryFilter);
    public PageList<Map<String, Object>> quertList4Badge(QueryFilter queryFilterqueryFilter);
    int insertBadge(Map map);
}
