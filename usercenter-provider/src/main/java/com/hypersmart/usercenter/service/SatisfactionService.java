package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.framework.service.IGenericService;

import java.util.List;

/**
 * 
 *
 * @author smh
 * @email smh
 * @date 2019-05-14 17:23:33
 */
public interface SatisfactionService extends IGenericService<String, Satisfaction> {
    List<Satisfaction> getSatisfactionDetail(String orgId,String time);
}

