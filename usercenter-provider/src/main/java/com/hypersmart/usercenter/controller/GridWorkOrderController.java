package com.hypersmart.usercenter.controller;

import com.hypersmart.base.query.*;
import com.hypersmart.base.util.ArrayUtil;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.GridRange;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.GridRangeService;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/grid-work-order"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridWorkOrderController"})
public class GridWorkOrderController {

    @Resource
    UcUserService ucUserService;

    @Resource
    GridBasicInfoService gridBasicInfoService;

    @Resource
    GridRangeService gridRangeService;

    /*
    *   unitType : 参数类型 1：楼栋 2:单元
    *   unitId :参数id  对应grid_range 的resourceId字段
    *   【作用】根据单元，楼栋查询对应网格的管家
    * */
    @PostMapping("/getUserByUnitId")
    public UcUser getUserByUnitId(@RequestParam("unitId") String unitId,@RequestParam("unitType")String unitType){
        if(StringUtil.isNotEmpty(unitId) && StringUtil.isNotEmpty(unitType)){
            UcUser user = ucUserService.getUserByUnitId(unitId,unitType);
            if(null != user && StringUtil.isNotEmpty(user.getId())){
                return user;
            }
        }
        return new UcUser();
    }

    /**
     *  【新增】 根据房产id获取管家接口
     *  【新增目的】小程序首页管家列表接口
     *  【作用】根据地块查询对应网格的管家--》主要查询服务中心网格管家
     */
    @PostMapping("/getUserByDivideId")
    public UcUser getUserByDivideId(@RequestParam("divideId") String divideId,@RequestParam("gridType")String gridType){
        if(StringUtil.isNotEmpty(divideId) && StringUtil.isNotEmpty(divideId)){
            QueryFilter query = QueryFilter.build();
                query.addFilter("gridType",gridType, QueryOP.EQUAL,FieldRelation.AND);
            query.addFilter("stagingId",divideId,QueryOP.EQUAL,FieldRelation.AND);
            query.addFilter("housekeeperId","",QueryOP.NOTNULL,FieldRelation.AND);
            PageList<GridBasicInfo> page = gridBasicInfoService.query(query);
            if(null != page && null != page.getRows() && page.getRows().size()>0){
                GridBasicInfo info = page.getRows().get(0);
                UcUser ucUser = ucUserService.get(info.getHousekeeperId());
                if(null != ucUser && StringUtil.isNotEmpty(ucUser.getId())){
                    return ucUser;
                }
            }
        }
        return new UcUser();
    }


    /**
     *  【新增】 根据房产id获取管家接口
     *  【新增目的】小程序首页管家列表接口
     *  【新增时间】2019-4-1
     *  【详情】若管家存在，则返回管家信息，若管家不存在，返回对应小区的服务中心管家的信息，若服务中心不存在管家，则不返回
     */
    @RequestMapping("/getHouseKeeperByHouseIds")
    public List<Map<String,String>> getHouseKeeper(@RequestParam("houseIds") String houseIds,
                                                   @RequestParam("resourceId") String resourceId){
        List<Map<String,String>> mapList = new ArrayList<>();
        //查询房产对应的网格
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("resourceId",houseIds, QueryOP.IN,FieldRelation.AND);
        queryFilter.addFilter("enabledFlag",1, QueryOP.EQUAL,FieldRelation.AND);
        queryFilter.addFilter("isDeleted",0, QueryOP.EQUAL,FieldRelation.AND);
        queryFilter.addFilter("rangeType",3, QueryOP.EQUAL,FieldRelation.AND);
        PageList<GridRange> pageList = gridRangeService.query(queryFilter);
        if(null != pageList  && null != pageList.getRows() && pageList.getRows().size()>0){
            //设置房产与网格的关系
            Map<String,String> gridMap = new HashMap<>();
            for(GridRange range : pageList.getRows()){
                gridMap.put(range.getResourceId(),range.getGridId());
            }
            //查询对应的网格的管家
            String grids = String.join(",",pageList.getRows().stream().map(GridRange::getGridId).collect(Collectors.toList()));
            QueryFilter query = QueryFilter.build();
            query.addFilter("id",grids, QueryOP.IN,FieldRelation.AND);
            query.addFilter("housekeeperId","", QueryOP.NOTNULL,FieldRelation.AND);
            query.addFilter("enabledFlag",1, QueryOP.EQUAL,FieldRelation.AND);
            query.addFilter("isDeleted",0, QueryOP.EQUAL,FieldRelation.AND);
            PageList<GridBasicInfo> basicInfoList = gridBasicInfoService.query(query);
            Map<String,String> userMap = new HashMap<>();
            if(null != basicInfoList && null != basicInfoList.getRows() && basicInfoList.getRows().size()>0){
                //设置网格与管家的关系
                for(GridBasicInfo info : basicInfoList.getRows()){
                    userMap.put(info.getId(),info.getHousekeeperId());
                }
                //查询管家详情数据
                String userIds = String.join(",",basicInfoList.getRows().stream().map(GridBasicInfo::getHousekeeperId).collect(Collectors.toList()));
                QueryFilter userQuery = QueryFilter.build();
                userQuery.addFilter("id",userIds, QueryOP.IN,FieldRelation.AND);
                userQuery.addFilter("isDele",0, QueryOP.EQUAL,FieldRelation.AND);
                PageList<UcUser> userPage = ucUserService.query(userQuery);
                Map<String,UcUser> userDetilMap = new HashMap<>();
                if(null != userPage && null != userPage.getRows() && userPage.getRows().size()>0){
                    for(UcUser ucUser : userPage.getRows()){
                        userDetilMap.put(ucUser.getId(),ucUser);
                    }
                }
                //设置返回值格式
                List<String> houseList = Arrays.asList(houseIds.split(","));
                for(String houseId : houseList){
                    Map<String,String> map = new HashMap<>();
                    if(StringUtil.isNotEmpty(gridMap.get(houseId))){//关联对应的网格
                        String gridId = gridMap.get(houseId);
                        if(StringUtil.isNotEmpty(userMap.get(gridId))){//网格是否关联管家
                            String userId = userMap.get(gridId);
                            if(null != userDetilMap.get(userId)){
                                map.put("houseId",houseId);
                                map.put("userId",userId);
                                map.put("userName",userDetilMap.get(userId).getFullname());
                                map.put("mobile",userDetilMap.get(userId).getMobile());
                            }else{//没有对应的管家信息
                                //查询服务中心管家
                                UcUser ucUser = getUserByDivideId(resourceId,"service_center_grid");
                                if(null != ucUser && StringUtil.isNotEmpty(ucUser.getId())){
                                    map.put("houseId",houseId);
                                    map.put("userId",ucUser.getId());
                                    map.put("userName",ucUser.getFullname());
                                    map.put("mobile",ucUser.getMobile());
                                }
                            }
                        }else{//没有关联管家
                            //查询服务中心管家
                            UcUser ucUser = getUserByDivideId(resourceId,"service_center_grid");
                            if(null != ucUser && StringUtil.isNotEmpty(ucUser.getId())){
                                map.put("houseId",houseId);
                                map.put("userId",ucUser.getId());
                                map.put("userName",ucUser.getFullname());
                                map.put("mobile",ucUser.getMobile());
                            }
                        }
                    }else{//表示该房产没有关联表格
                        //查询服务中心管家
                        UcUser ucUser = getUserByDivideId(resourceId,"service_center_grid");
                        if(null != ucUser && StringUtil.isNotEmpty(ucUser.getId())){
                            map.put("houseId",houseId);
                            map.put("userId",ucUser.getId());
                            map.put("userName",ucUser.getFullname());
                            map.put("mobile",ucUser.getMobile());
                        }
                    }
                    mapList.add(map);
                }
                return mapList;
            }
        }else{
            UcUser ucUser = getUserByDivideId(resourceId,"service_center_grid");
            //如果存在服务中心管家。循环赋值
            if(null != ucUser && StringUtil.isNotEmpty(ucUser.getId())){
                List<String> houseList = Arrays.asList(houseIds.split(","));
                for(String houseId : houseList){
                    Map<String,String> map = new HashMap<>();
                    map.put("houseId",houseId);
                    map.put("userId",ucUser.getId());
                    map.put("userName",ucUser.getFullname());
                    map.put("mobile",ucUser.getMobile());
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }
}
