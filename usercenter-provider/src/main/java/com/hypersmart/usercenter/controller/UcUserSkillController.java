package com.hypersmart.usercenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.JsonUtil;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UcUserSkill;
import com.hypersmart.usercenter.service.UcUserSkillService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户技能管理
 *
 * @author hwy
 * @email 123@12
 * @date 2019-07-25 16:44:59
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucUserSkill"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucUserSkillController"})
public class UcUserSkillController extends BaseController {
    @Resource
    UcUserSkillService ucUserSkillService;

    @PostMapping({"/list"})
    @ApiOperation(value = "用户技能管理数据列表}", httpMethod = "POST", notes = "获取用户技能管理列表")
    public PageList<UcUserSkill> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucUserSkillService.query(queryFilter);
    }

    @GetMapping({"/getSkillsByUserId"})
    @ApiOperation(value = "用户技能管理数据列表", httpMethod = "GET", notes = "获取单个用户技能管理记录")
    public List<String> getSkillsByUserId(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) throws Exception {
        List<String> ret = new LinkedList<>();
        List<UcUserSkill> skills = this.ucUserSkillService.getSkillsByUserId(userId);
        if (skills != null) {
            for (int i = 0; i < skills.size(); i++) {
                ret.add(skills.get(i).getEngineeringSkillCode());
            }
        }
        return ret;
    }

    @PostMapping({"save"})
    @ApiOperation(value = "保存用户技能管理", httpMethod = "POST", notes = "保存用户技能管理")
    public CommonResult save(@ApiParam(name = "model", value = "用户技能对象", required = true) @RequestBody JsonNode model) throws Exception {
        if (model == null || model.isNull()) {
            return new CommonResult<>(false, "参数错误", model, -1);
        }

        JsonNode user = model.get("user");
        JsonNode skill = model.get("skill");
        List<UcUserSkill> listNew = new LinkedList<>();
        List<UcUserSkill> listDel = new LinkedList<>();

        if (user != null && skill != null) {
            Iterator<JsonNode> userIterable = user.iterator();
            while (userIterable.hasNext()) {
                JsonNode userItem = userIterable.next();
                List<UcUserSkill> listUserSkillsExist = this.ucUserSkillService.getSkillsByUserId(userItem.asText());
                List<String> skillsExist = new LinkedList<>();
                if (listUserSkillsExist != null) {
                    for (int i = 0; i < listUserSkillsExist.size(); i++) {
                        skillsExist.add(listUserSkillsExist.get(i).getEngineeringSkillCode());
                    }
                }
                Iterator<JsonNode> skillIterable = skill.iterator();
                while (skillIterable.hasNext()) {
                    JsonNode skillItem = skillIterable.next();
                    if (!skillsExist.contains(skillItem.asText())) {
                        UcUserSkill newSkill = new UcUserSkill();
                        newSkill.setEngineeringSkillCode(skillItem.asText());
                        newSkill.setUserId(userItem.asText());
                        newSkill.setIsDele("0");
                        newSkill.setCreatedBy(ContextUtil.getCurrentUser().getUserId());
                        listNew.add(newSkill);
                    }
                }
                List<String> skills = JsonUtil.toBean(skill, List.class);
                List<UcUserSkill> needDels = listUserSkillsExist.stream().filter(e -> e.getUserId().equals(userItem.asText()) && !skills.contains(e.getEngineeringSkillCode())).collect(Collectors.toList());
                needDels.forEach(item -> {
                    item.setIsDele("1");
                    item.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
                });
                listDel.addAll(needDels);
            }
            int num = 0;
            if (listNew.size() > 0) {
                num += ucUserSkillService.insertBatch(listNew);
            }
            if (listDel.size() > 0) {
                num += ucUserSkillService.updateBatch(listDel);
            }
            if (num == listNew.size() + listDel.size()) {
                return new CommonResult(true, "用户技能保存成功");
            } else {
                return new CommonResult(false, "用户技能保存失败");
            }
        } else {
            return new CommonResult<>(false, "参数错误", model, -1);
        }
    }


}
