package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
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


        return this.satisfactionService.query(queryFilter);
    }

    @GetMapping({"/listByOrg"})
    @ApiOperation(value = "数据列表", httpMethod = "GET", notes = "获取列表")
    public PageList<Satisfaction> listByOrg(@ApiParam(name = "orgCode", value = "组织编码") @RequestParam String orgCode,
                                            @ApiParam(name = "beginDate", value = "起始时间") @RequestParam String beginDate,
                                            @ApiParam(name = "endDate", value = "结束时间") @RequestParam String endDate) {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("org_code", orgCode, QueryOP.EQUAL, FieldRelation.AND);
        queryFilter.addFilter("create_time", beginDate, QueryOP.GREAT_EQUAL, FieldRelation.AND);
        queryFilter.addFilter("create_time", endDate, QueryOP.LESS_EQUAL, FieldRelation.AND);
        List<FieldSort> fieldSortList = new ArrayList<>();
        FieldSort fieldSort = new FieldSort();
        fieldSort.setProperty("create_time");
        fieldSort.setDirection(Direction.DESC);
        fieldSortList.add(fieldSort);
        queryFilter.setSorter(fieldSortList);
        return this.satisfactionService.query(queryFilter);
    }

    @GetMapping({"/topLevel"})
    @ApiOperation(value = "顶级组织级别", httpMethod = "GET", notes = "获取顶级组织级别")
    public int topLevel(@ApiParam(name = "userId", value = "用户id") @RequestParam String userId) {
        List<UcOrg> ucOrgList = ucOrgService.getUserOrgListMerge(userId);
        int level = 4;
        for (UcOrg org:ucOrgList){
            if (org.getLevel()<level){
                level = org.getLevel();
            }
        }
        return level;
    }

    @GetMapping({"/gridsByDivideId/{massifId}"})
    @ApiOperation(value = "根据地块id获取网格", httpMethod = "GET", notes = "根据地块id获取网格")
    public List<GridBasicInfo> gridsBySmcloudmassifId(@ApiParam(name = "userId", value = "地块id") @PathVariable String massifId) {
        return gridBasicInfoService.getGridsBySmcloudmassifId(massifId);
    }

    @GetMapping({"/satisfactionDetail"})
    @ApiOperation(value = "满意度明细", httpMethod = "GET", notes = "获取满意度明细")
    public List<Satisfaction> satisfactionDetail(@ApiParam(name = "orgId", value = "组织id") @RequestParam String orgId,
                                                 @ApiParam(name = "time", value = "时间") @RequestParam String time) {
        return this.satisfactionService.getSatisfactionDetail(orgId,time);
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
    @RequestMapping(value = "/importData/{date}" ,method = RequestMethod.POST)
    public CommonResult<String> importData(MultipartFile file,@PathVariable("date")String date) throws Exception {
        CommonResult<String> result = this.satisfactionService.importData(file,date);
        return result;
    }

    /**
     * 查询当月是否已有数据；
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/check/data/{date}" ,method = RequestMethod.GET)
    public CommonResult CheckHasExist(@PathVariable("date")String date) throws Exception {
        return satisfactionService.CheckHasExist(date);
    }
}
