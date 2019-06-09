package com.hypersmart.usercenter.service;

import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.StageServiceGirdRef;

import java.util.List;

public interface StageServiceGirdRefService extends IGenericService<String, StageServiceGirdRef> {
    /**
     * 获取存在的地块id
     * @return
     */
    List<String> getExistStagingIds();
}
