package com.hypersmart.usercenter.service;


import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.UcOrgParams;

import java.util.List;

/**
 * 组织参数
 *
 * @author zhoukai
 * @email 70*******@qq.com
 * @date 2019-04-28 10:15:44
 */
public interface UcOrgParamsService extends IGenericService<String, UcOrgParams> {
    List<UcOrgParams> selectByOrgId(String orgId);
}

