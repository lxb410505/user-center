package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.*;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcOrgUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.UcOrgService;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织架构
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-11 11:48:45
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucOrg"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucOrgController"})
public class UcOrgController extends BaseController {
    @Resource
        UcOrgService ucOrgService;

    @Resource
    UcOrgUserService ucOrgUserService;

    /*
    *   根据地块id与当前用户信息，获取它及它以上的组织信息
    *
    * */
    @GetMapping("/getUcOrgsByorgId")
    public List<UcOrg> getUcOrgsByorgId(@RequestParam("orgId") String orgId){
        String userId = ContextUtil.getCurrentUserId();
        UcOrg ucOrg = null;
        if(null!= orgId && !"".equals(orgId)){
            ucOrg = ucOrgService.get(orgId);
            if(null== ucOrg){
                return  new ArrayList<>();
            }
        }else{
            return new ArrayList<>();
        }
        List<UcOrg> list = ucOrgService.getUserOrgList(userId);
        //根据地块id查询字段
        List<String> idList = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        String ids = "";
        for(int i=0;i<list.size();i++){
            UcOrg uc = list.get(i);
            if(!idList.contains(uc.getId())){
                idList.add(uc.getId());
                map.put(uc.getId(),i);
            }
            if("".equals(ids)){
                ids = uc.getId();
            }else{
                ids = ids +","+uc.getId();
            }
        }
        //根据ids查询组织，同时根据传递的orgID判断
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("id",ids, QueryOP.IN,FieldRelation.AND);
        queryFilter.addFilter("path",ucOrg.getPath(), QueryOP.LEFT_LIKE,FieldRelation.AND);
        PageList<UcOrg> page = ucOrgService.query(queryFilter);
        if(null == page || null == page.getRows() || page.getRows().size()<=0){
            return new ArrayList<>();
        }
        List<UcOrg> returnList = new ArrayList<>();
        List<UcOrg> queryList = page.getRows();
        for(UcOrg vo : queryList){
            if(map.get(vo.getId()) != null){
                   returnList.add(list.get(map.get(vo.getId())));
            }
        }
        List<UcOrg> set = new ArrayList<>();
        List<String> returnIds = new ArrayList<>();
        for(UcOrg temp : returnList){
            String [] paths = temp.getPath().split("\\.");
            for(int i=0;i<paths.length;i++){
                QueryFilter query = QueryFilter.build();
                query.addFilter("id",paths[i],QueryOP.EQUAL,FieldRelation.AND);
                query.addFilter("isDele","1",QueryOP.NOT_EQUAL,FieldRelation.AND);
                List<UcOrg> voList = ucOrgService.query(query).getRows();
                for(UcOrg vo:voList){
                    if(!returnIds.contains(vo.getId())){
                        vo.setDisabled("2");
                        set.add(vo);
                        returnIds.add(vo.getId());
                    }
                }
            }
        }
        return set;
    }



    @GetMapping({"/queryParent"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "GET", notes = "获取组织架构列表")
    public UcOrg queryParent(@ApiParam(name = "queryFilter", value = "查询对象") @RequestParam String id) {
        UcOrg ucOrg = ucOrgService.get(id);
        if(null != ucOrg && StringUtil.isNotEmpty(ucOrg.getParentId())){
            UcOrg parentOrg = ucOrgService.get(ucOrg.getParentId());
            return parentOrg;
        }
        return null;
    }

    @PostMapping({"/queryList"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "POST", notes = "获取组织架构列表")
    public PageList<UcOrg> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgService.query(queryFilter);
    }


    @PostMapping({"/list"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "POST", notes = "获取组织架构列表")
    public List<UcOrg> list( @RequestBody  Map<String,String> map) {
        QueryFilter queryFilter =QueryFilter.build();
        //获取当前用户
        IUser user =  ContextUtil.getCurrentUser();
        //根据用户查询人与组织关系
        List<UcOrgUser> list = ucOrgUserService.getUserOrg(user.getUserId());
        if(null==list || list.size()<=0){
            return new ArrayList<>();
        }
        String orgIds = "";
        for(int i=0;i<list.size();i++){
            if(i==0){
                orgIds = list.get(i).getOrgId();
            }else{
                orgIds = orgIds+","+list.get(i).getOrgId();
            }
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id",orgIds,QueryOP.IN,FieldRelation.AND);
        List<UcOrg> returnList = ucOrgService.query(orgQuery).getRows();
        //根据组织获取子级
        List<UcOrg> set = new ArrayList<>();
        for(UcOrg ucOrg :returnList){
            QueryFilter childQuery = QueryFilter.build();
            childQuery.addFilter("path",ucOrg.getPath(), QueryOP.RIGHT_LIKE,FieldRelation.AND);
            List<UcOrg> orgs = ucOrgService.query(childQuery).getRows();
            for(UcOrg org :orgs){
                set.add(org);
            }
            set.add(ucOrg);
        }
        //根据组织查询父级组织
        for(UcOrg ucOrg : returnList){
            String [] paths = ucOrg.getPath().split("\\.");
            for(int i=0;i<paths.length;i++){
                QueryFilter query = QueryFilter.build();
                query.addFilter("id",paths[i],QueryOP.EQUAL,FieldRelation.AND);
                List<UcOrg> voList = ucOrgService.query(query).getRows();
                for(UcOrg vo:voList){
                    set.add(vo);
                }
            }
        }
        //拼接组织id
        String str = "";
        for(int i=0;i<set.size();i++){
            if(i==0){
                str = set.get(i).getId();
            }else{
                str = str + "," + set.get(i).getId();
            }
        }
        if("".equals(str)){
            return new ArrayList<>();
        }
        queryFilter.addFilter("id",str, QueryOP.IN,FieldRelation.AND);
        if(null != map && null != map.get("parentId")) {
            if("".equals(map.get("parentId"))){
                map.put("parentId","0");
            }
            queryFilter.addFilter("parentId",map.get("parentId"), QueryOP.EQUAL,FieldRelation.AND);
            List<FieldSort> sortList = new ArrayList<>();
            FieldSort fieldSort = new FieldSort();
            fieldSort.setDirection(Direction.ASC);
            fieldSort.setProperty("orderNo");
            sortList.add(fieldSort);
            queryFilter.setSorter(sortList);
            return this.ucOrgService.query(queryFilter).getRows();
        }
        return new ArrayList<>();
    }


    @GetMapping({"/userList/{id}"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "POST", notes = "获取组织架构列表")
    public List<UcOrg> userList(@PathVariable("id") String userId) {
       return ucOrgService.getUserOrgList(userId);
    }

    @PostMapping({"/getByList"})
    @ApiOperation(value = "根据组织id集合获取组织信息}", httpMethod = "POST", notes = "根据组织id集合获取组织信息")
    public List<UcOrg> getByList( @RequestBody  Map<String,List<String>> map) {
        QueryFilter queryFilter =QueryFilter.build();

        if(null != map && null != map.get("list")) {
            String ids = "";
            for(int i=0;i<map.get("list").size();i++){
                if(i==0){
                    ids = map.get("list").get(i);
                }else{
                    ids = ids + "," + map.get("list").get(i);
                }
            }
            queryFilter.addFilter("id",ids, QueryOP.IN,FieldRelation.AND);
            return this.ucOrgService.query(queryFilter).getRows();
        }
        return new ArrayList<>();
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "组织架构数据列表", httpMethod = "GET", notes = "获取单个组织架构记录")
    public UcOrg get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgService.get(id);
    }


//    @PostMapping({"add"})
//    @ApiOperation(value = "新增组织架构信息", httpMethod = "POST", notes = "保存组织架构")
//    public CommonResult<String> post(@ApiParam(name = "ucOrg", value = "组织架构业务对象", required = true) @RequestBody UcOrg model) {
//        String msg = "添加组织架构成功";
//        this.ucOrgService.insert(model);
//        return new CommonResult(msg);
//    }
//
//    @PutMapping({"update"})
//    @ApiOperation(value = "更新指定id的 组织架构 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 组织架构 信息（更新全部信息）")
//    public CommonResult<String> put(@ApiParam(name = "ucOrg", value = "组织架构业务对象", required = true) @RequestBody UcOrg model) {
//        String msg = "更新组织架构成功";
//        this.ucOrgService.update(model);
//        return new CommonResult(msg);
//    }
//
//    @PatchMapping({"update"})
//    @ApiOperation(value = "更新指定id的 组织架构 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 组织架构 信息（更新部分信息）")
//    public CommonResult<String> patch(@ApiParam(name = "ucOrg", value = "组织架构业务对象", required = true) @RequestBody UcOrg model) {
//        String msg = "更新组织架构成功";
//        this.ucOrgService.updateSelective(model);
//        return new CommonResult(msg);
//    }
//
//    @DeleteMapping({"remove/{id}"})
//    @ApiOperation(value = "删除组织架构记录", httpMethod = "DELETE", notes = "删除组织架构记录")
//    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
//        this.ucOrgService.delete(id);
//        return new CommonResult(true, "删除成功");
//    }
//
//    @DeleteMapping({"/remove"})
//    @ApiOperation(value = "批量删除组织架构记录", httpMethod = "DELETE", notes = "批量删除组织架构记录")
//    public CommonResult<String> removes(
//            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
//        String[] aryIds = ids.split(",");
//        this.ucOrgService.delete(aryIds);
//        return new CommonResult(true, "批量删除成功");
//    }
}
