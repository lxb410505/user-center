package com.hypersmart.usercenter.controller;

import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.query.*;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.bo.UcOrgBO;
import com.hypersmart.usercenter.dto.UcOrgDTO;
import com.hypersmart.usercenter.dto.UcOrgExtend;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.usercenter.service.UcOrgParamsService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcOrgUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    UcOrgParamsService ucOrgParamsService;

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

    /**
     * 根据组织id获取父级组织
     * @param id
     * @return
     */
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


    /**
     * 通用查询方法
     * @param queryFilter
     * @return
     */
    @PostMapping({"/queryList"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "POST", notes = "获取组织架构列表")
    public PageList<UcOrg> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgService.query(queryFilter);
    }

    @GetMapping({"/getDefaultOrgList"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "GET", notes = "获取组织架构列表")
    public List<UcOrg> getDefaultOrgList() {
        return this.ucOrgService.getDefaultOrgList();
    }

    /**
     * 查询用户的关联的组织列表--》弃用，主要维护userList方法
     * @param map
     * @return
     */
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

    /**
     * 查询用户关联组织的列表
     * @param userId
     * @return
     */
    @GetMapping({"/userList/{id}"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "GET", notes = "获取组织架构列表")
    public List<UcOrg> userList(@PathVariable("id") String userId) {
       return ucOrgService.getUserOrgList(userId);
    }

    @GetMapping({"/userListMerge/{id}"})
    @ApiOperation(value = "组织架构数据列表包含条线的引用}", httpMethod = "GET", notes = "组织架构数据列表包含条线的引用")
    public List<UcOrg> userListMerge(@PathVariable("id") String userId) {
        return ucOrgService.getUserOrgListMerge(userId);
    }


    /**
     *  根据用户Id和组织父级id查询组织信息
     */
    @PostMapping({"/queryChildrenByUserId"})
    public List<UcOrgDTO> queryChildrenByUserId(@RequestBody UserIdParentId userIdParentId) {
        String userId=userIdParentId.getUserId();
        String parentOrgId=userIdParentId.getParentOrgId();
        return ucOrgService.queryChildrenByUserId(userId,parentOrgId);
    }

    /**
     *  根据用户id和组织id获取下级组织信息
     */
    @GetMapping({"/queryChildrenByCondition"})
    public List<UcOrg> queryChildrenByCondition(@RequestParam String userId,
                                                   @RequestParam String orgId,
                                                   @RequestParam(required = false) String grade
    ) {
        if(com.hypersmart.base.util.StringUtil.isEmpty(grade)){
            grade =null;
        }
        return ucOrgService.queryChildrenByCondition(userId,orgId,grade);
    }

    //根据组织级别查询组织信息
    @PostMapping({"/queryByGrade"})
    public List<UcOrg> queryByGrade(@RequestBody UserIdGrade userIdGrade) {
        String userId=userIdGrade.getUserId();
        String grade=userIdGrade.getGrade();
        return ucOrgService.queryByGrade(userId,grade);
    }

    //根据组织id查询所有子节点的组织信息
    @PostMapping({"/queryChildrenByOrgId"})
    public List<UcOrg> queryChildrenByOrgId(@RequestBody OrgIdGrade orgIdGrade) {
        String orgId=orgIdGrade.getOrgId();
        String grade=orgIdGrade.getGrade();
        return ucOrgService.queryChildrenByOrgId(orgId,grade);
    }

    //查询用户有权查看条线的列表接口
    @PostMapping({"/queryByDemensionCode"})
    public List<UcOrg> queryByDemensionCode(@RequestBody UserIdDemensionCode userIdDemensionCode) {
        String userId=userIdDemensionCode.getUserId();
        String demensionCode=userIdDemensionCode.getDemensionCode();
        return ucOrgService.queryByDemensionCode(userId,demensionCode);
    }

    /**
     * 根据组织id集合获取组织信息
     * @param map
     * @return
     */
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

    /**
     * 根据组织id查询详情
     * @return
     */
    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "组织架构数据列表", httpMethod = "GET", notes = "获取单个组织架构记录")
    public UcOrg get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.ucOrgService.get(id);
    }

    /**
     * 根据组织id获取所有维度的关联组织及当前组织
     * @param query
     * @return
     */
    @RequestMapping(value = {"getAllDimOrgListByOrg"}, method = {
            RequestMethod.POST}, produces = {
            "application/json; charset=utf-8"})
    @ApiOperation(value = "根据组织id获取所有维度的关联组织及当前组织", httpMethod = "GET", notes = "根据组织id获取所有维度的关联组织及当前组织")
    public List<UcOrgExtend> getAllDimOrgListByOrg(@ApiParam(name = "query", value = "查询对象", required = true)
                                                       @RequestBody UcOrgBO query) {
        return this.ucOrgService.getAllDimOrgListByOrg(query);
    }

    @GetMapping({"getAllParentByOrgId"})
    @ApiOperation(value = "获取当前组织及其所有上级组织列表", httpMethod = "GET", notes = "获取当前组织及其所有上级组织列表")
    public List<UcOrg> getAllParentByOrgId(@ApiParam(name = "id", value = "业务对象主键", required = true)
                                           @RequestParam("id") String id) {
        UcOrg currentOrg = this.ucOrgService.get(id);
        if (null == currentOrg) {
            return null;
        }
        String fullPath = currentOrg.getPath();
        String[] orgIds = fullPath.split("\\.");
        List<UcOrg> orgList = this.ucOrgService.getByIds(orgIds);
        return orgList;
    }

    @GetMapping({"getAllOrgByOrgId"})
    @ApiOperation(value = "获取当前组织及其所有上级组织列表", httpMethod = "GET", notes = "获取当前组织及其所有上级组织列表")
    public List<UcOrg> getAllOrgByOrgId(@ApiParam(name = "id", value = "业务对象主键", required = true)
                                           @RequestParam("id") String id) {
        String[] orgIds = id.split("\\.");
        List<UcOrg> orgList = this.ucOrgService.getByIds(ArrayUtils.remove(orgIds, 0));
        return orgList;

    }

    /**
     * 根据组织id查询组织参数
     * @param orgId
     * @return
     */
    @GetMapping({"/organizationParams/{orgId}"})
    @ApiOperation(value = "组织架构数据列表}", httpMethod = "GET", notes = "获取组织架构列表")
    public List<UcOrgParams> organizationParams(@PathVariable("orgId") String orgId) {
        List<UcOrgParams> ucOrgParams = ucOrgParamsService.selectByOrgId(orgId);
        return ucOrgParams;
    }



    @GetMapping({"getOrgParams"})
    @ApiOperation(value = "获取组织参数信息", httpMethod = "GET", notes = "获取组织参数信息")
    public List<UcOrgParams> getOrgParams(@ApiParam(name = "code", value = "参数Code", required = true)
                                           @RequestParam("code") String code,
    @ApiParam(name = "value", value = "参数值", required = true)
    @RequestParam("value") String value
    ) {
       return  this.ucOrgService.getOrgParams(code,value);
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


    /**
     * 查询地块列表
     * @param queryFilter
     * @return
     */
    @PostMapping({"/getOrgList"})
    @ApiOperation(value = "职务定义数据列表}", httpMethod = "POST", notes = "获取职务定义列表")
    public PageList<UcOrg> getOrgList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.ucOrgService.query(queryFilter);
    }

    /**
     * 根据组织id获取上级组织grade
     */
    @GetMapping({"/getParentGrade/{orgId}"})
    @ApiOperation(value = "获取上级组织grade}", httpMethod = "GET", notes = "获取上级组织grade")
    public String getParentGrade(@ApiParam(name = "orgId", value = "组织id") @PathVariable String orgId) {
        UcOrg ucOrg = ucOrgService.get(orgId);
        if (ucOrg!=null){
            String parentId = ucOrg.getParentId();
            UcOrg parent = ucOrgService.get(parentId);
            if (parent!=null){
                return parent.getGrade();
            }
        }
        return null;
    }

    @GetMapping({"getDefaultListByGrade"})
    @ApiOperation(value = "获取组织参数信息", httpMethod = "GET", notes = "获取组织参数信息")
    public List<UcOrg> getDefaultListByGrade(@ApiParam(name = "grade", value = "参数值", required = true) @RequestParam("grade") String grade
    ) {
        return  this.ucOrgService.getDefaultOrgListByGrade(grade);
    }

    @GetMapping({"/getLandMassList"})
    @ApiOperation(value = "闸机对接-获取地块组织信息", httpMethod = "GET", notes = "闸机对接-获取地块组织信息")
    public List<Map<String, String>> getDiKuaiList() {
        List<Map<String, String>> retList = new LinkedList<>();
        List<UcOrg> list = this.ucOrgService.getDefaultOrgListByGrade("ORG_DiKuai");
        list.forEach(p -> {
            Map<String, String> item = new HashMap<>();
            item.put("id", p.getId());
            item.put("name", p.getName());
            retList.add(item);
        });
        return retList;
    }
}
