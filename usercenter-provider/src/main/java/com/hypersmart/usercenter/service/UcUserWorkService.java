package com.hypersmart.usercenter.service;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.UcUserWork;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户上下班记录
 * @Author: liyong
 * @CreateDate: 2019/4/25 19:35
 * @Version: 1.0
 */
@Service
public interface UcUserWorkService extends IGenericService<String, UcUserWork> {
    void delByUserId(String userId);
    String getStatus(String userId);
    //status:0上班，1下班
    List<UcUserWork> queryUserWorkStatusList(List<String> userIds,String status);
}
