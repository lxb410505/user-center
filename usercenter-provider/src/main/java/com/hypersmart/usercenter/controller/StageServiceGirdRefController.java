package com.hypersmart.usercenter.controller;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.usercenter.model.StageServiceGirdRef;
import com.hypersmart.usercenter.service.StageServiceGirdRefService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/grid-api/StageServiceGirdRef"}, produces = {"application/json;charset=UTF-8"})
public class StageServiceGirdRefController {
    @Resource
    private StageServiceGirdRefService stageServiceGirdRefService;
    @GetMapping({"/getExistStagingIds"})
    public List<String> getExistStagingIds() {
        return this.stageServiceGirdRefService.getExistStagingIds();
    }
}
