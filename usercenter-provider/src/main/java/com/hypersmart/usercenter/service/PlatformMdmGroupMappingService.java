package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.dto.UcOrgDTO;
import com.hypersmart.usercenter.model.PlatformMdmGroupMapping;
import com.hypersmart.framework.service.IGenericService;

import java.util.List;

/**
 * 平台组团映射表
 *
 * @author lily
 * @email lily
 * @date 2019-12-18 12:51:08
 */
public interface PlatformMdmGroupMappingService extends IGenericService<String, PlatformMdmGroupMapping> {

    List<UcOrgDTO> queryByGrade(String userId, String grade);
}

