package com.hypersmart.usercenter.service;

import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户上下班记录
 * @Author: liyong
 * @CreateDate: 2019/4/25 19:35
 * @Version: 1.0
 */
@Service
public interface UcUserWorkHistoryService extends IGenericService<String, UcUserWorkHistory> {
}
