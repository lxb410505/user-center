package com.hypersmart.usercenter.service.impl;

import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.model.UcUserSkill;
import com.hypersmart.usercenter.mapper.UcUserSkillMapper;
import com.hypersmart.usercenter.service.UcUserSkillService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户技能管理
 *
 * @author hwy
 * @email 123@12
 * @date 2019-07-25 16:44:59
 */
@Service("ucUserSkillServiceImpl")
public class UcUserSkillServiceImpl extends GenericService<String, UcUserSkill> implements UcUserSkillService {

    public UcUserSkillServiceImpl(UcUserSkillMapper mapper) {
        super(mapper);
    }

    @Override
    public List<UcUserSkill> getSkillsByUserId(String userId) throws Exception {
        UcUserSkill skill = new UcUserSkill();
        skill.setUserId(userId);
        skill.setIsDele("0");
        return this.selectAll(skill);
    }
}