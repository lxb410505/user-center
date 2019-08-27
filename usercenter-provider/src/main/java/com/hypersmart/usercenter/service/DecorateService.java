package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.usercenter.model.Decorate;
import com.hypersmart.framework.service.IGenericService;

/**
 * 客户装修资料归档管理
 *
 * @author zcf
 * @email 1490***@qq.com
 * @date 2019-08-26 23:08:37
 */
public interface DecorateService extends IGenericService<String, Decorate> {

    CommonResult<String> addDecoration(Decorate model);

    CommonResult<String> updateDecoration(Decorate model);
}

