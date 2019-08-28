package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.TransferInfo;
import com.hypersmart.usercenter.mapper.TransferInfoMapper;
import com.hypersmart.usercenter.service.TransferInfoService;
import org.springframework.stereotype.Service;

/**
 * 过户信息
 *
 * @author liyong
 * @email xx
 * @date 2019-08-27 20:21:29
 */
@Service("transferInfoServiceImpl")
public class TransferInfoServiceImpl extends GenericService<String, TransferInfo> implements TransferInfoService {

    public TransferInfoServiceImpl(TransferInfoMapper mapper) {
        super(mapper);
    }
}