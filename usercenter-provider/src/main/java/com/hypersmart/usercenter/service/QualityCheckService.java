package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.QualityCheck;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 *
 * @author Magellan
 * @email Magellan
 * @date 2019-05-21 16:18:32
 */
public interface QualityCheckService extends IGenericService<String, QualityCheck> {

    public CommonResult<String> importData(MultipartFile file, String date) ;
}

