package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.usercenter.model.DecorateFiles;
import com.hypersmart.framework.service.IGenericService;

/**
 * 客户装修资料归档管理
 *
 * @author ljia
 * @email 123@456.com
 * @date 2019-08-27 14:21:33
 */
public interface DecorateFilesService extends IGenericService<String, DecorateFiles> {
    PageList<DecorateFiles> queryFilesList(String decorateId);

    CommonResult<String> add(DecorateFiles model);
}

