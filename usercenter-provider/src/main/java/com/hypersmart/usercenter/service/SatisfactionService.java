package com.hypersmart.usercenter.service;

import com.alibaba.fastjson.JSONObject;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.Satisfaction;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
 *
 * @author magellan
 * @email magellan
 * @date 2019-05-14 13:37:39
 */
public interface SatisfactionService extends IGenericService<String, Satisfaction> {
    //导入excel数据
    CommonResult<String> importData(MultipartFile file, String date);
    //导入前调用检查是否有重复导入
    CommonResult CheckHasExist(String date);
    List<Satisfaction> getSatisfactionListByParam(JSONObject json);
    List<Satisfaction> getSatisfactionDetail(String orgCode,String time);
    Satisfaction getSingleSatisfaction(String orgCode, String time);
    List<Satisfaction> getAllSatisfaction(String time);
}

