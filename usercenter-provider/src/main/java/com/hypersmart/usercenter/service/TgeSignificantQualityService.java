package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.TgeSignificantQuality;
import org.springframework.web.multipart.MultipartFile;

/**
 * 重大事件质量
 *
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:07:53
 */
public interface TgeSignificantQualityService extends IGenericService<String, TgeSignificantQuality> {
    PageList<TgeSignificantQuality> queryList(QueryFilter queryFilter);


    CommonResult<String> importData(MultipartFile file, String month);

    boolean checkExists(String month, Object orgId);

}

