package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.dto.GoldInfo;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.TgeSignificantQuality;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.RsunUserStarlLevelService;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/usercenter/v1/rsunUserStarLevel"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"rsunUserStarLevelController"})
public class RsunUserStarLevelController extends BaseController {


    @Resource
    RsunUserStarlLevelService rsunUserStarlLevelService;

    /**
     * 查询员工金币数和勋章数
     *
     * @param queryFilter
     * @return
     */
    @PostMapping({"/moneylist"})
    @ApiOperation(value = "用户金币等级列表}", httpMethod = "POST", notes = "获取用户管理列表")
    public PageList<RsunUserStarLevel> moneylistt(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return rsunUserStarlLevelService.moneylist(queryFilter);
    }


    @PutMapping({"/update"})
    @ApiOperation(value = "修改员工等级", httpMethod = "PUT", notes = "修改员工等级")
    public CommonResult<String> put(@ApiParam(name = "tgeSignificantQuality", value = "重大事件质量业务对象", required = true) @RequestBody Map<String,Object> modelMap) {
        if(modelMap == null){
            return new CommonResult<>(false,"修改员工等级入参信息错误");
        }
        if(modelMap.get("ucUserId") == null || (String)modelMap.get("ucUserId") == ""){
            if(modelMap.get("account") == null || (String)modelMap.get("account") == ""){
                return new CommonResult<>(false,"查询用户账号信息有误，请联系管理员");
            }
        }
        return rsunUserStarlLevelService.updateInfo(modelMap);
    }

    @PostMapping({"/query/gold"})
    @ApiOperation(value = "管家列表", httpMethod = "POST", notes = "管家列表")
    public PageList<Map<String, Object>> getGoldNoun(@ApiParam(name = "queryFilter", value = "查询条件") @RequestBody QueryFilter queryFilter) {
        return rsunUserStarlLevelService.quertList(queryFilter);
    }

    @PostMapping({"/query/badge"})
    @ApiOperation(value = "管家列表", httpMethod = "POST", notes = "管家列表")
    public PageList<Map<String, Object>> getbadge(@ApiParam(name = "queryFilter", value = "查询条件") @RequestBody QueryFilter queryFilter) {
        return rsunUserStarlLevelService.quertList4Badge(queryFilter);
    }


    @PostMapping({"/add/badge"})
    @ApiOperation(value = "管家列表", httpMethod = "POST", notes = "管家列表")

    public int getbadge(@ApiParam(name = "queryFilter", value = "查询条件") @RequestBody Map map) {
        if(map == null){
            return -1;
        }
        if(map.get("uc_user_id") == null || (String)map.get("uc_user_id") == ""){
            if(map.get("account") == null || (String)map.get("account") == ""){
                return -1;
            }
        }
        return rsunUserStarlLevelService.insertBadge(map);
    }
    @GetMapping({"/job/gold"})
    public void doGoldJob (){
        rsunUserStarlLevelService.doGoldJob4Gold();
    }

    @GetMapping({"/job/badge"})
    public void doBadgeJob (){
        rsunUserStarlLevelService.doGoldJob4Badge();
    }

    /**
     * 给第三方  调用的接口
     * 获取人员
     *
     * @return
     */
    @PostMapping({"/query/usergold"})
    public PageList<GoldInfo> getUserGold4Part(@ApiParam(name = "queryFilter", value = "查询条件") @RequestBody QueryFilter queryFilter){
        return rsunUserStarlLevelService.getUserGold4Part(queryFilter);
    }
}
