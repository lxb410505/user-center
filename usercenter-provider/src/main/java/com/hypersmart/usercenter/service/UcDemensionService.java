package com.hypersmart.usercenter.service;

import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.UcDemension;

import java.util.List;

/**
 * 维度管理
 *
 * @author yang
 * @email yang17766@sina.com
 * @date 2019-03-13 13:49:47
 */
public interface UcDemensionService extends IGenericService<String, UcDemension> {
    List<UcDemension> queryByCode(String code);

    List<UcDemension> queryByNotCode(String code);
}

