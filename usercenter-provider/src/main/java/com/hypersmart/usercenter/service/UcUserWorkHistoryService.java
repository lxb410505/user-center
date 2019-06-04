package com.hypersmart.usercenter.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.GroupIdentity;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 用户上下班记录
 * @Author: liyong
 * @CreateDate: 2019/4/25 19:35
 * @Version: 1.0
 */
@Service
public interface UcUserWorkHistoryService extends IGenericService<String, UcUserWorkHistory> {

    PageList<Map<String, Object>> queryPage(QueryFilter queryFilter);

    int save(UcUserWorkHistory ucUserWorkHistory);

    String queryLatest(String username);

    List<UcUserWorkHistory> queryUserWorkStatusList(List<String> userIds);
}
