package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
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

@RestController
@RequestMapping(value = {"/api/usercenter/v1/rsunUserStarLevel"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"rsunUserStarLevelController"})
public class  RsunUserStarLevelController extends BaseController {

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

        if(queryFilter.getQuerys().size()>0){
            List<RsunUserStarLevel> list = new ArrayList<>();
            List<QueryField> querys = queryFilter.getQuerys();
            for(int i = 0 ; i<querys.size() ; i++){
                String value = (String) querys.get(i).getValue();
                //通过账号去查询姓名
//                String getzh = ucUserService.getzh(value);
                String getname = ucUserService.getname(value);
                if(getname!=null && !"".equals(getname)){
                    RsunUserStarLevel rsunUserStarLevel = new RsunUserStarLevel();
                    rsunUserStarLevel.setName(getname);//姓名
                    rsunUserStarLevel.setZhanghao(value);//账号
                    //通过账号去查找id
                    String getid = ucUserService.getid(value);
                    rsunUserStarLevel.setUcUserId(getid);
                    RsunUserStarLevell rsunUserStarLevell = rsunUserStarlLevelService.get(getid);
                    if(rsunUserStarLevell==null){
                        rsunUserStarLevel.setPjStarId(0);
                        rsunUserStarLevel.setTotalCoin(0.0);
                        rsunUserStarLevel.setXzNum(0.0);
                    }else {
                        rsunUserStarLevel.setPjStarId(rsunUserStarLevell.getPjStarId());
                        rsunUserStarLevel.setTotalCoin(rsunUserStarLevell.getTotalCoin());
                        rsunUserStarLevel.setXzNum(rsunUserStarLevell.getXzNum());
                    }
                    list.add(rsunUserStarLevel);
                }else {

                }
                /*//通过姓名查找账号
                List<UcUser> ucUser = ucUserService.getzh(value);
                if(ucUser!=null && ucUser.size()>0){
                    for(UcUser ucUser1 : ucUser){
                    RsunUserStarLevel rsunUserStarLevel = new RsunUserStarLevel();
                    rsunUserStarLevel.setName(value);//姓名
                    rsunUserStarLevel.setUcUserId(ucUser1.getAccount());//账号
                    RsunUserStarLevell rsunUserStarLevell = rsunUserStarlLevelService.get(ucUser1.getAccount());
                    if(rsunUserStarLevell==null){
                        rsunUserStarLevel.setPjStarId(0);
                        rsunUserStarLevel.setTotalCoin(0.0);
                        rsunUserStarLevel.setXzNum(0.0);
                    }else {
                        rsunUserStarLevel.setPjStarId(rsunUserStarLevell.getPjStarId());
                        rsunUserStarLevel.setTotalCoin(rsunUserStarLevell.getTotalCoin());
                        rsunUserStarLevel.setXzNum(rsunUserStarLevell.getXzNum());
                    }
                    list.add(rsunUserStarLevel);
                    }
                }else {

                }*/

            }
            PageList<RsunUserStarLevel> pageList = new PageList<>();
            pageList.setTotal(list.size());
            pageList.setPage(1);
            pageList.setPageSize(20);
            pageList.setRows(list);
            return pageList;
        }

        //遍历用户表
        PageList<UcUser> query = ucUserService.query(queryFilter);
        List<UcUser> rows1 = query.getRows();
        //创建List集合
        List<RsunUserStarLevel> list = new ArrayList<>();
        //遍历用户表
        for(UcUser ucUser: rows1){
            if(ucUser.getAccount()!=null){
                //创建RsunUserStarLevel对象
                RsunUserStarLevel rsunUserStarLevel = new RsunUserStarLevel();
                //给rsunUserStarLevel存值
                rsunUserStarLevel.setName(ucUser.getFullname());//姓名
                //根据用户姓名去查询金币表中是否有数据,如果有数据就获取等级金币账号
                RsunUserStarLevell rsunUserStarLevell = rsunUserStarlLevelService.get(ucUser.getId());

                rsunUserStarLevel.setUcUserId(ucUser.getId());//id
                rsunUserStarLevel.setZhanghao(ucUser.getAccount());//账号
                if(rsunUserStarLevell==null){
                    rsunUserStarLevel.setPjStarId(0);//默认等级为0
                    rsunUserStarLevel.setTotalCoin(0.0);//默认为0
                    rsunUserStarLevel.setXzNum(0.0);//默认为0
                }else {
                    rsunUserStarLevel.setPjStarId(rsunUserStarLevell.getPjStarId());
                    rsunUserStarLevel.setTotalCoin(rsunUserStarLevell.getTotalCoin());
                    rsunUserStarLevel.setXzNum(rsunUserStarLevell.getXzNum());
                }

                list.add(rsunUserStarLevel);
            }

        }
        PageList<RsunUserStarLevel> objectPageList = new PageList<>();
        objectPageList.setTotal(query.getTotal());
        objectPageList.setPage(query.getPage());
        objectPageList.setPageSize(query.getPageSize());
        objectPageList.setRows(list);
        return objectPageList;


    }


    @PutMapping({"/update"})
    @ApiOperation(value = "修改员工等级", httpMethod = "PUT", notes = "修改员工等级")
    public CommonResult<String> put(@ApiParam(name = "tgeSignificantQuality", value = "重大事件质量业务对象", required = true) @RequestBody RsunUserStarLevell model) {

        String msg = "";
        //判断用户金币表里有没有数据如果有数据就执行修改如果没有数据就添加
        RsunUserStarLevell rsunUserStarLevell = rsunUserStarlLevelService.get(model.getUcUserId());
        if(rsunUserStarLevell==null){
            model.setLevelSyTime(new Date());
            rsunUserStarlLevelService.insert(model);
            return new CommonResult(msg);
        }else {
            model.setLevelSyTime(new Date());
            rsunUserStarlLevelService.update(model);
            return new CommonResult(msg);
        }


    }

}
