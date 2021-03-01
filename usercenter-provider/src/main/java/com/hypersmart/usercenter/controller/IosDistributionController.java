package com.hypersmart.usercenter.controller;

import com.hypersmart.base.constants.BaseConst;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.UcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import com.hypersmart.usercenter.model.IosDistribution;
import com.hypersmart.usercenter.service.IosDistributionService;
import java.util.Date;
import java.util.Map;

/**
 * ios编码分配表
 *
 * @author lily
 * @email lily
 * @date 2020-12-14 17:36:15
 */
@RestController
@RequestMapping(value = {"/api/v1/iosDistribution"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"iosDistributionController"})
public class IosDistributionController extends BaseController {
    @Resource
    IosDistributionService iosDistributionService;

    @Resource
    UcUserService ucUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(IosDistributionController.class);



    @RequestMapping(value = {"/getCode"}, method = {RequestMethod.POST})
    @ApiOperation(value = "获取code", httpMethod = "POST", notes = "获取code")
    public CommonResult<String> getCode(@RequestBody Map<String,String> params){

        try{
            logger.warn("iosDistribution========>getCode");
//            TenantsConfig.setTenantKey("-1");
            ContextUtils.addVariable(BaseConst.HEADER_RAW_ACCOUNT, "admin");
            String account=params.get("account");
            String pwd=params.get("pwd");
            if(StringUtil.isEmpty(account) ||StringUtil.isEmpty(pwd) ){
                return new CommonResult<>(false,"用户名或密码不能为空");
            }

            UcUser user = ucUserService.getByAccount(account);
            if(user==null){
                return new CommonResult<>(false,"用户不存在");
            }

            String password = user.getPassword();
            if(!password.equals(this.passwordEncoder.encode(pwd))){
                return new CommonResult<>(false,"密码错误");
            }

            QueryFilter query = QueryFilter.build();
            query.setPageBean(new PageBean(1,1));
            query.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
            query.addFilter("bindAccount", account, QueryOP.EQUAL, FieldRelation.AND);
            PageList<IosDistribution> page = iosDistributionService.query(query);
            String code="";
            if (page!=null && page.getRows()!=null && page.getRows().size()>0) {
                code=page.getRows().get(0).getCode();
            }
            else {
                //获取一条记录，然后更新进去
                QueryFilter query2 = QueryFilter.build();
                query2.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
                query2.addFilter("isBind", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
                query2.setPageBean(new PageBean(1,1));
                PageList<IosDistribution> page2 = iosDistributionService.query(query2);
                if (page2!=null && page2.getRows()!=null && page2.getRows().size()>0) {
                    IosDistribution avItem=page2.getRows().get(0);
                    avItem.setBindAccount(account);
                    avItem.setBindName(user.getFullname());
                    avItem.setIsBind("1");
                    avItem.setBindTime(new Date());
                    iosDistributionService.updateSelective(avItem);

                    code = avItem.getCode();
                }else {
                    return new CommonResult<>(false,"当前没有可用的下载码");
                }
            }

            return new CommonResult<>(true,"",code);
        }
        catch (Exception ex){
            logger.error("getCode:"+ex.getMessage());
            return new CommonResult<>(false,"获取下载码失败，请联系管理员");
        }

    }


    @PostMapping({"/list"})
    @ApiOperation(value = "ios编码分配表数据列表}", httpMethod = "POST", notes = "获取ios编码分配表列表")
    public PageList<IosDistribution> list(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
        return this.iosDistributionService.query(queryFilter);
    }

    @GetMapping({"/get/{id}"})
    @ApiOperation(value = "ios编码分配表数据列表", httpMethod = "GET", notes = "获取单个ios编码分配表记录")
    public IosDistribution get(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id) {
        return this.iosDistributionService.get(id);
    }


    @PostMapping({"add"})
    @ApiOperation(value = "新增ios编码分配表信息", httpMethod = "POST", notes = "保存ios编码分配表")
    public CommonResult<String> post(@ApiParam(name = "iosDistribution", value = "ios编码分配表业务对象", required = true) @RequestBody IosDistribution model) {
        String msg = "添加ios编码分配表成功";
        this.iosDistributionService.insert(model);
        return new CommonResult(msg);
    }

    @PutMapping({"update"})
    @ApiOperation(value = "更新指定id的 ios编码分配表 信息（更新全部信息）", httpMethod = "PUT", notes = "更新指定id的 ios编码分配表 信息（更新全部信息）")
    public CommonResult<String> put(@ApiParam(name = "iosDistribution", value = "ios编码分配表业务对象", required = true) @RequestBody IosDistribution model) {
        String msg = "更新ios编码分配表成功";
        this.iosDistributionService.update(model);
        return new CommonResult(msg);
    }

    @PatchMapping({"update"})
    @ApiOperation(value = "更新指定id的 ios编码分配表 信息（更新部分信息）", httpMethod = "PATCH", notes = "更新指定id的 ios编码分配表 信息（更新部分信息）")
    public CommonResult<String> patch(@ApiParam(name = "iosDistribution", value = "ios编码分配表业务对象", required = true) @RequestBody IosDistribution model) {
        String msg = "更新ios编码分配表成功";
        this.iosDistributionService.updateSelective(model);
        return new CommonResult(msg);
    }

    @DeleteMapping({"remove/{id}"})
    @ApiOperation(value = "删除ios编码分配表记录", httpMethod = "DELETE", notes = "删除ios编码分配表记录")
    public CommonResult<String> remove(@ApiParam(name = "id", value = "业务主键", required = true) @PathVariable String id) {
        this.iosDistributionService.delete(id);
        return new CommonResult(true, "删除成功");
    }

    @DeleteMapping({"/remove"})
    @ApiOperation(value = "批量删除ios编码分配表记录", httpMethod = "DELETE", notes = "批量删除ios编码分配表记录")
    public CommonResult<String> removes(
            @ApiParam(name = "ids", value = "业务主键数组,多个业务主键之间用逗号分隔", required = true) @RequestParam String ids) {
        String[] aryIds = ids.split(",");
        this.iosDistributionService.delete(aryIds);
        return new CommonResult(true, "批量删除成功");
    }
}
