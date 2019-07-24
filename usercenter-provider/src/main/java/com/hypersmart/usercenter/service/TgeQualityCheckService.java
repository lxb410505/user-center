package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.TgeQualityCheck;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 *
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:05:16
 */
public interface TgeQualityCheckService extends IGenericService<String, TgeQualityCheck> {
    public CommonResult<String> importData(MultipartFile file, String date) ;
    CommonResult CheckHasExist(String date);
}

