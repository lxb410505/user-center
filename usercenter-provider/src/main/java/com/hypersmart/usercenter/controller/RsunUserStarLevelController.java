package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.TgeSignificantQuality;
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

@RestController
@RequestMapping(value = {"/api/usercenter/v1/rsunUserStarLevel"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"rsunUserStarLevelController"})
public class RsunUserStarLevelController extends BaseController {

    @Resource
    UcUserService ucUserService;

    @Resource
    RsunUserStarlLevelService rsunUserStarlLevelService;
    /**
     * 查询员工金币数和勋章数
     * @param queryFilter
     * @return
     */
    @PostMapping({"/moneylist"})
    @ApiOperation(value = "用户金币等级列表}", httpMethod = "POST", notes = "获取用户管理列表")
    public PageList<RsunUserStarLevel> moneylistt(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        PageList<RsunUserStarLevell> query = rsunUserStarlLevelService.query(queryFilter);
        List<RsunUserStarLevell> rows = query.getRows();

        List<RsunUserStarLevel> list = new ArrayList<RsunUserStarLevel>();
        for(RsunUserStarLevell rsunUserStarLevell : rows){
            String getname = ucUserService.getname(rsunUserStarLevell.getUcUserId());

            RsunUserStarLevel rsunUserStarLevel = new RsunUserStarLevel();
            rsunUserStarLevel.setName(getname);//姓名
            rsunUserStarLevel.setUcUserId(rsunUserStarLevell.getUcUserId());//账号
            rsunUserStarLevel.setPjStarId(rsunUserStarLevell.getPjStarId());//等级
            rsunUserStarLevel.setLevelSyTime(rsunUserStarLevell.getLevelSyTime());//时间
            rsunUserStarLevel.setTotalCoin(rsunUserStarLevell.getTotalCoin());//金币数
            rsunUserStarLevel.setXzNum(rsunUserStarLevell.getXzNum());//勋章数

            list.add(rsunUserStarLevel);
        }

        PageList<RsunUserStarLevel> rsunUserStarLevellPageList = new PageList<>();
        rsunUserStarLevellPageList.setRows(list);
        rsunUserStarLevellPageList.setTotal(query.getTotal());
        rsunUserStarLevellPageList.setPageSize(query.getPageSize());
        rsunUserStarLevellPageList.setPage(query.getPage());
        return rsunUserStarLevellPageList;
//        return query;
    }


    @PutMapping({"/update"})
    @ApiOperation(value = "修改员工等级", httpMethod = "PUT", notes = "修改员工等级")
    public CommonResult<String> put(@ApiParam(name = "tgeSignificantQuality", value = "重大事件质量业务对象", required = true) @RequestBody RsunUserStarLevell model) {
        String msg = "更新重大事件质量成功";
        model.setLevelSyTime(new Date());
        this.rsunUserStarlLevelService.update(model);
        return new CommonResult(msg);
    }

}
