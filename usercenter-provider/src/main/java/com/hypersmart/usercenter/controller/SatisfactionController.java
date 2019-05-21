package com.hypersmart.usercenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.bo.SatisfactionBo;
import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.usercenter.service.SatisfactionService;
import com.hypersmart.base.query.*;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.UcOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author magellan
 * @email magellan
 * @date 2019-05-14 13:37:39
 */
@RestController
@RequestMapping(value = {"/api/Satisfaction/v1/satisfaction"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"satisfactionController"})
public class SatisfactionController extends BaseController {
    @Resource
    SatisfactionService satisfactionService;

    @Resource
    UcOrgService ucOrgService;

    @Resource
    GridBasicInfoService gridBasicInfoService;

    @PostMapping({"/list"})
    @ApiOperation(value = "数据列表", httpMethod = "POST", notes = "获取列表")
    public PageList<Satisfaction> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        List<FieldSort> sortList = new ArrayList<>();
        FieldSort fieldSort = new FieldSort();
        fieldSort.setDirection(Direction.ASC);
        fieldSort.setProperty("order_");
        sortList.add(fieldSort);
        queryFilter.setSorter(sortList);
        return this.satisfactionService.query(queryFilter);
    }

    @PostMapping("/querylist")
    public List<Satisfaction> querylist(@RequestBody JSONObject json) {

        return this.satisfactionService.getSatisfactionListByParam(json);
    }

    @GetMapping({"/listByOrg"})
    @ApiOperation(value = "数据列表", httpMethod = "GET", notes = "获取列表")
    public List<Satisfaction> listByOrg(@ApiParam(name = "orgCode", value = "组织编码") @RequestParam String orgCode,
                                               @ApiParam(name = "beginDate", value = "起始时间") @RequestParam String beginDate,
                                               @ApiParam(name = "endDate", value = "结束时间") @RequestParam String endDate) throws ParseException {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("org_code", orgCode, QueryOP.EQUAL, FieldRelation.AND);
        queryFilter.addFilter("effective_time", beginDate + "-01", QueryOP.GREAT_EQUAL, FieldRelation.AND);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date end = sdf.parse(endDate);
        Date begin = sdf.parse(beginDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.MONTH, 1);
        Date dt1 = calendar.getTime();
        String reStr = sdf.format(dt1);
        queryFilter.addFilter("effective_time", reStr + "-01", QueryOP.LESS, FieldRelation.AND);
        List<FieldSort> fieldSortList = new ArrayList<>();
        FieldSort fieldSort = new FieldSort();
        fieldSort.setProperty("effective_time");
        fieldSort.setDirection(Direction.ASC);
        fieldSortList.add(fieldSort);
        queryFilter.setSorter(fieldSortList);
        List<Satisfaction> list = this.satisfactionService.query(queryFilter).getRows();
        calendar.setTime(begin);
        List<Satisfaction> tempList=new ArrayList<>();
        while (begin.getTime() <= end.getTime()) {
            Satisfaction temp =new Satisfaction();
            temp.setEffectiveTime(begin);
            tempList.add(temp);
            calendar.setTime(begin);
            calendar.add(Calendar.MONTH, 1);
            begin = calendar.getTime();
        }
        for (Satisfaction s:tempList){
            for (Satisfaction satisfaction : list) {
                if (sdf.format(satisfaction.getEffectiveTime()).equals(sdf.format(s.getEffectiveTime()))){
//                    Collections.replaceAll(tempList,s,satisfaction);
                    BeanUtils.mergeObject(satisfaction,s);
                }
            }
        }
//        Map<String, Satisfaction> resMap = new HashMap<>();
//        while (begin.getTime() <= end.getTime()) {
//            resMap.put(sdf.format(begin), new Satisfaction());
//            calendar.setTime(begin);
//            calendar.add(Calendar.MONTH, 1);
//            begin = calendar.getTime();
//        }
//        for (Satisfaction satisfaction : list) {
//            resMap.put(sdf.format(satisfaction.getEffectiveTime()), satisfaction);
//        }
//        return resMap;
        return tempList;
    }

    @GetMapping({"/topLevel"})
    @ApiOperation(value = "顶级组织级别", httpMethod = "GET", notes = "获取顶级组织级别")
    public Map<String,Object> topLevel() {
        int level = 0;
        String area = "";
        String areaName = "";
        String project = "";
        String projectName = "";
        String dikuai = "";
        String dikuaiName = "";
        String name = "";
        String code = "";

        String userId = ContextUtil.getCurrentUserId();
        List<UcOrg> ucOrgList = ucOrgService.getUserOrgListMerge(userId);
        for (UcOrg org : ucOrgList) {
            if ("1".equals(org.getDisabled())) {
                if ("ORG_QuYu".equals(org.getGrade())) {
                    if(StringUtil.isEmpty(area)){
                        area = org.getCode();
                        areaName=org.getName();
                    }

                } else if ("ORG_XiangMu".equals(org.getGrade())) {
                    if(StringUtil.isEmpty(project)){
                        project = org.getCode();
                        projectName=org.getName();
                    }
                } else if ("ORG_DiKuai".equals(org.getGrade())) {
                    if(StringUtil.isEmpty(dikuai)) {
                        dikuai = org.getCode();
                        dikuaiName = org.getName();
                    }
                }
            }
        }
        if (StringUtil.isNotEmpty(area)) {
            level = 1;
            name=areaName;
            code=area;
        } else if (StringUtil.isNotEmpty(project)) {
            level = 2;
            name=projectName;
            code=project;
        } else if (StringUtil.isNotEmpty(dikuai)) {
            level = 3;
            name=dikuaiName;
            code=dikuai;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("level",level);
        map.put("name",name);
        map.put("code",code);
        return map;
    }

    @GetMapping({"/gridsByDivideId/{massifId}"})
    @ApiOperation(value = "根据地块id获取网格", httpMethod = "GET", notes = "根据地块id获取网格")
    public List<GridBasicInfo> gridsBySmcloudmassifId(@ApiParam(name = "massifId", value = "地块id") @PathVariable String massifId) {
        return gridBasicInfoService.getGridsBySmcloudmassifId(massifId);
    }

    @GetMapping({"/satisfactionDetail"})
    @ApiOperation(value = "满意度明细", httpMethod = "GET", notes = "获取满意度明细")
    public List<Satisfaction> satisfactionDetail(@ApiParam(name = "orgCode", value = "组织编码") @RequestParam String orgCode,
                                                 @ApiParam(name = "time", value = "时间") @RequestParam String time) {
        return this.satisfactionService.getSatisfactionDetail(orgCode, time);
    }

    @GetMapping({"/singleSatisfaction"})
    @ApiOperation(value = "单组织单月满意度", httpMethod = "GET", notes = "单组织单月满意度")
    public List<Satisfaction> singleSatisfaction(@ApiParam(name = "orgCode", value = "组织编码") @RequestParam String orgCode,
                                                 @ApiParam(name = "time", value = "时间") @RequestParam String time) {
        return this.satisfactionService.getSatisfactionDetail(orgCode, time);
    }

    @PostMapping({"/appSatisfaction"})
    @ApiOperation(value = "单组织单月满意度", httpMethod = "POST", notes = "单组织单月满意度")
    public CommonResult<Satisfaction> appSatisfaction(@ApiParam(name = "orgIds", value = "组织id") @RequestBody SatisfactionBo bo) {
        CommonResult<Satisfaction> result=new CommonResult<>();
        result.setState(true);
        if(CollectionUtils.isEmpty(bo.getOrgIds()) || StringUtil.isEmpty(bo.getTime())){
            result.setState(false);
            result.setMessage("参数有误");
            return result;
        }
        Satisfaction singleSatisfaction = this.satisfactionService.getSingleSatisfaction(bo.getOrgIds(), bo.getTime());
        if(singleSatisfaction==null){
            result.setState(false);
            result.setMessage("暂无数据");
        }else{
            result.setMessage("成功");
            result.setValue(singleSatisfaction);
        }
        return result;
    }

    @GetMapping({"/allSatisfaction"})
    @ApiOperation(value = "总部满意度", httpMethod = "GET", notes = "总部满意度")
    public List<Satisfaction> allSatisfaction(@ApiParam(name = "time", value = "时间") @RequestParam String time) {
        return this.satisfactionService.getAllSatisfaction(time);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "数据列表", httpMethod = "GET", notes = "获取单个记录")
    public Satisfaction get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.satisfactionService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增信息", httpMethod = "POST", notes = "保存")
    public CommonResult<String> post(@ApiParam(name = "satisfaction", value = "业务对象", required = true) @RequestBody Satisfaction model) {
        String msg = "添加成功";
        this.satisfactionService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的  信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "satisfaction", value = "业务对象", required = true) @RequestBody Satisfaction model) {
        String msg = "更新成功";
        this.satisfactionService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的  信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的  信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "satisfaction", value = "业务对象", required = true) @RequestBody Satisfaction model) {
        String msg = "更新成功";
        this.satisfactionService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除记录", httpMethod = "DELETE", notes = "删除记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.satisfactionService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除记录", httpMethod = "DELETE", notes = "批量删除记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.satisfactionService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }

    /**
     * 通过Excle导入科目余额设置
     *
     * @param file Excel文件
     * @return
     */
    @RequestMapping(value = "/importData/{date}", method = RequestMethod.POST)
    public CommonResult<String> importData(MultipartFile file, @PathVariable("date") String date) throws Exception {

        CommonResult<String> result = this.satisfactionService.importData(file, date);
        return result;
    }

    /**
     * 查询当月是否已有数据；
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check/data/{date}", method = RequestMethod.GET)
    public CommonResult CheckHasExist(@PathVariable("date") String date) throws Exception {
        return satisfactionService.CheckHasExist(date);
    }
}
