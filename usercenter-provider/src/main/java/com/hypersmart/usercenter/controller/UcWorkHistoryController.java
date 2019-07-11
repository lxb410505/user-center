package com.hypersmart.usercenter.controller;


import com.hypersmart.base.aop.norepeat.NoRepeatSubmit;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.Direction;
import com.hypersmart.base.query.FieldSort;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.UniqueIdUtil;
import com.hypersmart.usercenter.model.UcUserWork;
import com.hypersmart.usercenter.model.UcUserWorkHistory;
import com.hypersmart.usercenter.service.UcUserWorkHistoryService;
import com.hypersmart.usercenter.service.UcUserWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


/**
 * 用户上下班记录
 * @author liyong
 */
@RestController
@RequestMapping(value = {"/api/usercenter/v1/ucWorkHistory"},produces = {"application/json;charset=UTF-8"})
@Api(tags = {"ucWorkHistoryController"})
public class UcWorkHistoryController extends BaseController {


	@Autowired
	private UcUserWorkHistoryService ucUserWorkHistoryService;
	@Autowired
	UcUserWorkService ucUserWorkService;
	/**
	 * 分页查询
	 *
	 * @param queryFilter
	 * @return
	 */
	@PostMapping("list")
	@ApiOperation(value = "用户上下班历史列表}", httpMethod = "POST", notes = "用户上下班历史列表")
	public PageList<Map<String,Object>> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
		queryFilter.setSorter(new ArrayList<FieldSort>(){{
			add(new FieldSort("create_time", Direction.DESC));
		}});
		return this.ucUserWorkHistoryService.queryPage(queryFilter);

	}


	/**
     * 新增上下班记录
     *
     * @param
     * @return
     */
    @GetMapping("save")
	@NoRepeatSubmit
    @ApiOperation(value = "新增上下班记录", httpMethod = "POST", notes = "新增上下班记录")
    public CommonResult<String> create(@ApiParam(name = "ucUserWorkHistory", value = "新增上下班记录", required = true)  @RequestParam(value = "status",required = false) String status, @RequestParam(value = "account",required = false) String account,@RequestParam(value = "userId",required = false) String userId) {
		CommonResult commonResult = new CommonResult();
		String msg = null;
		// 0上班  1下班

		UcUserWorkHistory ucUserWorkHistory = new UcUserWorkHistory();
		ucUserWorkHistory.setCreateBy(current());
		ucUserWorkHistory.setCreateTime(new Date());
		ucUserWorkHistory.setStatus(status);
		ucUserWorkHistory.setAccount(account);
		ucUserWorkHistory.setUserId(userId);
		ucUserWorkHistory.setId(UniqueIdUtil.getSuid());
		int i = ucUserWorkHistoryService.save(ucUserWorkHistory);

		ucUserWorkService.delByUserId(userId);
		UcUserWork ucUserWork=new UcUserWork();
		ucUserWork.setCreateBy(current());
		ucUserWork.setCreateTime(new Date());
		ucUserWork.setStatus(status);
		ucUserWork.setAccount(account);
		ucUserWork.setUserId(userId);
		ucUserWork.setId(UniqueIdUtil.getSuid());
		int j = ucUserWorkService.insert(ucUserWork);

		if (i>0&&j>0) {
			commonResult.setState(true);
			commonResult.setMessage(msg);
			commonResult.setValue(status);
		} else {
			commonResult.setState(false);
			commonResult.setMessage("新增失败");
//			commonResult.setValue(status);
		}
		return commonResult;
	}

	@GetMapping("getUserHisStatus")
	public CommonResult<String> getUserHisStatus(@RequestParam("userId") String userId){
		String s = ucUserWorkService.getStatus(userId);
		if(null==s){
			return new CommonResult<>(false,"该用户无上下班记录",null);
		}
		return new CommonResult<>(true,"",s,null);

	}
}






