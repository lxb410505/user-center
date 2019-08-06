package com.hypersmart.usercenter.service;

import com.hypersmart.usercenter.model.UcUserSkill;
import com.hypersmart.framework.service.IGenericService;

import java.util.List;

/**
 * 用户技能管理
 *
 * @author hwy
 * @email 123@12
 * @date 2019-07-25 16:44:59
 */
public interface UcUserSkillService extends IGenericService<String, UcUserSkill> {

    List<UcUserSkill> getSkillsByUserId(String userId) throws Exception;
}

