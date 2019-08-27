package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.bo.UserHouseRefBO;
import com.hypersmart.usercenter.dto.ClientRelationDTO;
import com.hypersmart.usercenter.model.House;
import com.hypersmart.usercenter.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.hypersmart.framework.model.ResponseData;
import io.swagger.annotations.ApiParam;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 【基础信息】房产
 *
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */
@RestController
@RequestMapping(value = {"/api/House/v1/house"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"houseController"})
public class HouseController extends BaseController {
    @Resource
    HouseService houseService;

    @PostMapping({"/list"})
    @ApiOperation(value = "【基础信息】房产数据列表}", httpMethod = "POST", notes = "获取【基础信息】房产列表")
    public PageList<Map<String,Object>> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.houseService.list(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "【基础信息】房产数据列表", httpMethod = "GET", notes = "获取单个【基础信息】房产记录")
    public House get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.houseService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增【基础信息】房产信息", httpMethod = "POST", notes = "保存【基础信息】房产")
    public CommonResult<String> post(@ApiParam(name = "house", value = "【基础信息】房产业务对象", required = true) @RequestBody House model) {
        String msg = "添加【基础信息】房产成功";
        this.houseService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 【基础信息】房产 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 【基础信息】房产 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "house", value = "【基础信息】房产业务对象", required = true) @RequestBody House model) {
        String msg = "更新【基础信息】房产成功";
        this.houseService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 【基础信息】房产 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 【基础信息】房产 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "house", value = "【基础信息】房产业务对象", required = true) @RequestBody House model) {
        String msg = "更新【基础信息】房产成功";
        this.houseService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除【基础信息】房产记录", httpMethod = "DELETE", notes = "删除【基础信息】房产记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.houseService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除【基础信息】房产记录", httpMethod = "DELETE", notes = "批量删除【基础信息】房产记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.houseService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }


    @GetMapping({"/selectGridBuilding/{id}"})
    @ApiOperation(value = "根据网格查询楼栋", httpMethod = "GET", notes = "根据网格查询楼栋")
    public List<Map<String,Object>> selectGridBuilding(@ApiParam(name = "id", value = "网格id", required = true) @PathVariable String id) {
       return this.houseService.selectGridBuilding(id);
    }

    @GetMapping({"/getUnitByBuildingId/{id}"})
    @ApiOperation(value = "根据楼栋查询单元", httpMethod = "GET", notes = "根据楼栋查询单元")
    public List<Map<String,Object>> getUnitByBuildingId(@ApiParam(name = "id", value = "楼栋id", required = true) @PathVariable String id) {
        return this.houseService.selectBuildingUnit(id);
    }
    @PostMapping({"/exportExcel"})
    @ApiOperation(value = "根据楼栋查询单元", httpMethod = "PATCH", notes = "根据地块查询楼栋")
    public void exportExcel(@RequestBody QueryFilter queryFilter, HttpServletResponse response) throws Exception {
        this.houseService.exportExcel(queryFilter,response);
    }
    @PostMapping({"/ucMemberRelationList"})
    public PageList<ClientRelationDTO> ucMemberRelationList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.houseService.ucMemberRelationList(queryFilter);
    }

    @PostMapping({"/addUserHouseRef"})
    public CommonResult<String> addUserHouseRef( @RequestBody UserHouseRefBO model) {
        if(StringUtils.isEmpty(model.getHouseId()) || StringUtils.isEmpty(model.getRelation()) ||
                CollectionUtils.isEmpty(model.getMemberIds())){

            return new CommonResult<>(false,"入参有误，请检查!");
        }
        return this.houseService.addUserHouseRef(model);
    }
    @PostMapping({"/removeUserHouseRef"})
    public CommonResult<String> removeUserHouseRef(@RequestBody String id) {
        return this.houseService.updateUserHouseRef(id);
    }
}
