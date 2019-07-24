package com.hypersmart.usercenter.fegin;

import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.conf.FeignConfig;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.mdm.dto.UcOrg;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: liyong
 * @CreateDate: 2019/5/23 16:33
 * @Version: 1.0
 */
@FeignClient(name = "user-center-eureka", fallback = UcOrgFegin.UcOrgFeginFallback.class, configuration = {FeignConfig.class})
public interface UcOrgFegin {
    @RequestMapping(value = {"/grid-api/grid-basic-info/getGridsHouse/{id}"},method = {RequestMethod.GET })
    @ApiOperation(value = "根据地块id，获取地块下的网格覆盖的房产信息", httpMethod = "GET", notes = "根据地块id，获取地块下的楼栋网格信息")
    List<JsonNode> getGridsHouse(@ApiParam(name = "id", value = "地块id", required = true) @PathVariable("id") String id);

    @GetMapping({"/api/skeleton/v1/ucOrg/get/{id}"})
    @ApiOperation(value = "组织架构数据列表", httpMethod = "GET", notes = "获取单个组织架构记录")
    public UcOrg get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable("id") String id);

    /**
     * 查询地块列表
     * @param queryFilter
     * @return
     */
    @PostMapping({"/api/usercenter/v1/ucOrg/getOrgList"})
    @ApiOperation(value = "职务定义数据列表}", httpMethod = "POST", notes = "获取职务定义列表")
    public PageList<UcOrg> getOrgList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter);

    @GetMapping({"/api/usercenter/v1/ucOrg/getAllOrgByOrgId"})
    @ApiOperation(value = "获取当前组织及其所有上级组织列表", httpMethod = "GET", notes = "获取当前组织及其所有上级组织列表")
    public List<UcOrg> getAllOrgByOrgId(@ApiParam(name = "id", value = "业务对象主键", required = true)
                                        @RequestParam("id") String id);


    @GetMapping({"/api/usercenter/v1/ucOrg/getAllParentByOrgId"})
    @ApiOperation(value = "获取当前组织及其所有上级组织列表", httpMethod = "GET", notes = "获取当前组织及其所有上级组织列表")
    public List<UcOrg> getAllParentByOrgId(@ApiParam(name = "id", value = "业务对象主键", required = true)
                                           @RequestParam("id") String id);
    @Component
    public class UcOrgFeginFallback implements UcOrgFegin {

        @Override
        public List<JsonNode> getGridsHouse(String id) {
            return null;
        }

        @Override
        public UcOrg get(String id) {
            return null;
        }

        /**
         * 查询地块列表
         *
         * @param queryFilter
         * @return
         */
        @Override
        public PageList<UcOrg> getOrgList(QueryFilter queryFilter) {
            return null;
        }

        @Override
        public List<UcOrg> getAllOrgByOrgId(String id) {
            return null;
        }

        @Override
        public List<UcOrg> getAllParentByOrgId(String id) {
            return null;
        }
    }

}
