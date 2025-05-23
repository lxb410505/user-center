package com.hypersmart.usercenter.controller;


import com.hypersmart.base.aop.norepeat.NoRepeatSubmit;
import com.hypersmart.base.controller.BaseController;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
import com.hypersmart.usercenter.dto.RangeDTO;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.UcOrgUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 网格基础信息表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */
@RestController
@RequestMapping(value = {"/grid-api/grid-basic-info"}, produces = {"application/json;charset=UTF-8"})
@Api(tags = {"gridBasicInfoController"})
public class GridBasicInfoController extends BaseController {
	@Resource
	GridBasicInfoService gridBasicInfoService;

	@Autowired
	private UcOrgUserService ucOrgUserService;

	/**
	 * 分页查询
	 *
	 * @param queryFilter
	 * @return
	 */
	@PostMapping({"/list"})
	@ApiOperation(value = "用户组织关系数据列表}", httpMethod = "POST", notes = "获取用户组织关系列表")
	public PageList<Map<String, Object>> queryList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
		return this.gridBasicInfoService.quertList(queryFilter, 0);
	}

	/**
	 * 分页查询
	 *
	 * @param queryFilter
	 * @return
	 */
	@PostMapping({"/associateList"})
	@ApiOperation(value = "用户组织关系数据列表(关联网格)", httpMethod = "POST", notes = "获取用户组织关系列表")
	public PageList<Map<String, Object>> queryAssociateList(@ApiParam(name = "queryFilter", value = "查询对象") @RequestBody QueryFilter queryFilter) {
		return this.gridBasicInfoService.quertList(queryFilter, 1);
	}


	/**
	 * 新增网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@PostMapping({"/save"})
	@NoRepeatSubmit
	@ApiOperation(value = "新增网格基础信息表信息", httpMethod = "POST", notes = "保存网格基础信息表")
	public CommonResult<String> create(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoDTO gridBasicInfoDTO) {
		return gridBasicInfoService.create(gridBasicInfoDTO);
	}

	/**
	 * 编辑网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@PostMapping({"/update"})
	@NoRepeatSubmit
	@ApiOperation(value = "更新指定id的 网格基础信息表 信息（更新全部信息）", httpMethod = "POST", notes = "更新指定id的 网格基础信息表 信息（更新全部信息）")
	public CommonResult<String> edit(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoDTO gridBasicInfoDTO) {
		CommonResult commonResult = new CommonResult();
		GridErrorCode gridErrorCode = gridBasicInfoService.edit(gridBasicInfoDTO);
		if (GridErrorCode.SUCCESS.getCode() == gridErrorCode.getCode()) {
			commonResult.setState(true);
			commonResult.setMessage("修改成功！");
		} else {
			commonResult.setState(false);
			commonResult.setMessage(gridErrorCode.getMessage());
		}
		return commonResult;
	}

	/**
	 * 获取指定id的网格
	 *
	 * @param id
	 * @return
	 */
	@GetMapping({"/getGridById/{id}/{stagingId}"})
	@ApiOperation(value = "网格基础信息表数据列表", httpMethod = "GET", notes = "获取单个网格基础信息表记录")
	public Map<String, Object> getGridById(@ApiParam(name = "id", value = "业务对象主键", required = true) @PathVariable String id, @PathVariable String stagingId) {
		return gridBasicInfoService.getGridById(id,stagingId);
	}

	/**
	 * 更换管家
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@PostMapping({"/changeHousekeeper"})
	@NoRepeatSubmit
	@ApiOperation(value = "变更网格管家", httpMethod = "POST", notes = "变更网格管家")
	public CommonResult<String> changeHousekeeper(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoDTO gridBasicInfoDTO) {
		CommonResult commonResult = new CommonResult();
		GridErrorCode gridErrorCode = gridBasicInfoService.changeHousekeeper(gridBasicInfoDTO);
		if (GridErrorCode.SUCCESS.getCode() == gridErrorCode.getCode()) {
			commonResult.setState(true);
			commonResult.setMessage("变更管家成功！");
		} else {
			commonResult.setState(false);
			commonResult.setMessage(gridErrorCode.getMessage());
		}
		return commonResult;
	}

	/**
	 * 更换映射楼栋
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@PostMapping({"/changeRange"})
	@NoRepeatSubmit
	@ApiOperation(value = "变更映射楼栋", httpMethod = "POST", notes = "变更映射楼栋")
	public CommonResult<String> changeRange(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoDTO gridBasicInfoDTO) {
		return gridBasicInfoService.changeRange(gridBasicInfoDTO);
	}


	/**
	 * 批量禁用网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	@PostMapping({"/disable"})
	@NoRepeatSubmit
	@ApiOperation(value = "禁用网格基础信息表记录", httpMethod = "POST", notes = "禁用网格基础信息表记录")
	public CommonResult<String> disable(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoDTO gridBasicInfoDTO) {
		return gridBasicInfoService.disableGridList(gridBasicInfoDTO);
	}

	/**
	 * 批量删除网格
	 *
	 * @param gridBasicInfoBO
	 * @return
	 */
	@PostMapping({"/delete"})
	@ApiOperation(value = "删除网格基础信息表记录", httpMethod = "POST", notes = "删除网格基础信息表记录")
	public CommonResult<String> delete(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoBO gridBasicInfoBO) {
		CommonResult commonResult = new CommonResult();
		GridErrorCode gridErrorCode = gridBasicInfoService.deleteGridList(gridBasicInfoBO);
		if (GridErrorCode.SUCCESS.getCode() == gridErrorCode.getCode()) {
			commonResult.setState(true);
			commonResult.setMessage("批量删除成功！");
		} else {
			commonResult.setState(false);
			commonResult.setMessage("删除失败");
		}
		return commonResult;
	}


	@PostMapping({"/getHouseKeeper"})
	@ApiOperation(value = "管家列表", httpMethod = "POST", notes = "管家列表")
	public PageList<Map<String, Object>> listHouseKeeper(@ApiParam(name = "queryFilter", value = "查询条件") @RequestBody QueryFilter queryFilter) {
		PageBean pageBean = queryFilter.getPageBean();
		PageList<Map<String, Object>> pageList = ucOrgUserService.quertListFive(queryFilter);
		if (pageList != null && pageList.getRows() != null && pageList.getRows().size() > 0) {
			List<String> ids=new ArrayList<>();
			List <Map<String,Object>>  temp= pageList.getRows().stream().filter(// 过滤去重
					v -> {
						boolean flag = !ids.contains(v.get("houseKeeperId").toString());
						ids.add(v.get("houseKeeperId").toString());
						return flag;
					}
			).collect(Collectors.toList());
			pageList.setRows(temp);
			List<HouseKeeperBO> houseKeeperBOList = new ArrayList<>();
			for (Map<String, Object> objectMap : pageList.getRows()) {
				HouseKeeperBO houseKeeperBO = new HouseKeeperBO();
				houseKeeperBO.setDivideId(objectMap.get("divideId").toString());
				houseKeeperBO.setHouseKeeperId(objectMap.get("houseKeeperId").toString());
				houseKeeperBOList.add(houseKeeperBO);
			}

			List<GridBasicInfoSimpleDTO> list = gridBasicInfoService.getGridBasicInfoByHouseKeeperIds(houseKeeperBOList);

			if (list != null && list.size() > 0) {
				for (Map<String, Object> m : pageList.getRows()) {
					Map<String, List<GridBasicInfoSimpleDTO>> map = list.stream()
							.filter(gridBasicInfoSimpleDTO -> (m.get("houseKeeperId").toString().equals(gridBasicInfoSimpleDTO.getHousekeeperId())))
							.collect(Collectors.toList()).stream().collect(Collectors.groupingBy(GridBasicInfoSimpleDTO::getStagingId));
					List<GridBasicInfoSimpleDTO> gridBasicInfoSimpleDTOList = map.get(m.get("divideId").toString());
					m.put("gridList", gridBasicInfoSimpleDTOList);
					//20200819 增加管家师傅 wmc start ---
					if (gridBasicInfoSimpleDTOList == null || gridBasicInfoSimpleDTOList.size()<=0)
					{
						m.put("housekeeperMasterName","");
						continue;
					}
					List<GridBasicInfoSimpleDTO> housekeeperMasterNameList = gridBasicInfoSimpleDTOList.stream().filter(x -> StringUtils.isNotEmpty(x.getHousekeeperMasterName())).collect(Collectors.toList());
					List<String> nameList = new ArrayList<>();
					if (housekeeperMasterNameList!=null&&housekeeperMasterNameList.size()>0)
					{
						for (GridBasicInfoSimpleDTO x : housekeeperMasterNameList)
						{
							nameList.add(x.getHousekeeperMasterName());
						}
					}
					m.put("housekeeperMasterName",(null!=nameList&&nameList.size()>0)? String.join("、",nameList):"");
					//20200819 增加管家师傅 wmc  end ---
				}
			}
		}
		if (pageList.getRows() == null) {
			pageList.setRows(new ArrayList<>());
		}
		if (pageList.getPageSize() == 0) {
			if(BeanUtils.isEmpty(pageBean)){
				pageList.setPageSize(20);
				pageList.setPage(1);
			}else{
				pageList.setPageSize(pageBean.getPageSize());
				pageList.setPage(pageBean.getPage());
			}
		}
		return pageList;
	}


	@PostMapping({"/associatedGrid"})
	@NoRepeatSubmit
	@ApiOperation(value = "关联网格", httpMethod = "POST", notes = "关联网格")
	public CommonResult<String> associatedGrid(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoDTO gridBasicInfoDTO) {
        return gridBasicInfoService.associatedGrid(gridBasicInfoDTO);
	}

	@PostMapping({"/disassociatedGrid"})
	@NoRepeatSubmit
	@ApiOperation(value = "取消关联网格", httpMethod = "POST", notes = "取消关联网格")
	public CommonResult<String> disassociatedGrid(@ApiParam(name = "gridBasicInfo", value = "网格基础信息表业务对象", required = true) @RequestBody GridBasicInfoDTO gridBasicInfoDTO) {
        return gridBasicInfoService.disassociatedGrid(gridBasicInfoDTO);
	}

	@GetMapping({"/getGridsBymassifId/{id}"})
	@ApiOperation(value = "根据地块id，获取地块下的楼栋网格信息", httpMethod = "GET", notes = "根据地块id，获取地块下的楼栋网格信息")
	public List<GridBasicInfo> getGridsBymassifId(@ApiParam(name = "id", value = "地块id", required = true) @PathVariable String id) {
		if (StringUtils.isRealEmpty(id)) {
			return new ArrayList<>();
		}
		List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.getGridsBymassifId(id);
		return gridBasicInfos;
	}

	@GetMapping({"/getGridsHouse/{id}"})
	@ApiOperation(value = "根据地块id，获取地块下的网格覆盖的房产信息", httpMethod = "GET", notes = "根据地块id，获取地块下的楼栋网格信息")
	public List<RangeDTO> getGridsHouse(@ApiParam(name = "id", value = "地块id", required = true) @PathVariable String id) {
		List<RangeDTO> gridHouses = gridBasicInfoService.getGridsHouseBymassifId(id);
		return gridHouses;
	}

	@GetMapping({"/getGridByHouseId"})
	@ApiOperation(value = "根据房产id，获取相应网格", httpMethod = "GET", notes = "根据房产id，获取相应网格")
	public List<GridBasicInfo> getGridByHouseId(@ApiParam(name = "id", value = "查询对象") @RequestParam String id) {
		List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.getByGridRange(id);
		return gridBasicInfos;
	}


	@GetMapping({"/getHouseByCondition"})
	@ApiOperation(value = "根据地块id，获取地块下的网格覆盖的房产信息", httpMethod = "GET", notes = "根据地块id，获取地块下的楼栋网格信息")
	public List<Map<String,Object>> getHouseByCondition(@ApiParam(name = "divide", value = "地块id", required = true) @RequestParam(value = "divide",required = false,defaultValue = "") String divide, @RequestParam(value = "id",required = false,defaultValue = "0") String id, @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "20") Integer pageSize) {
		List<Map<String, Object>> houseByCondition = gridBasicInfoService.getHouseByCondition(divide, id,pageNum,pageSize);
		/*Map<String,Object> checkMap = new HashMap<>(16);
		List<Map<String, Object>> maps = new ArrayList<>();
		for (Map<String, Object> map : houseByCondition) {
			String key = map.get("parentId")+(String)map.get("name");
		    if(!checkMap.containsKey(key)){
                checkMap.put(key,map);
                maps.add(map);
            }
		}*/
		return houseByCondition;
	}


	@GetMapping({"/getPublicGrid/{id}"})
	@ApiOperation(value = "", httpMethod = "GET", notes = "")
	public Integer getPublicGrid(@PathVariable String id) {
		return gridBasicInfoService.getPublicGridNum(id);
	}

	@GetMapping({"/getByHouseId/{houseId}"})
	public Map<String,Object> getByHouseId(@PathVariable String houseId) {
		return gridBasicInfoService.getByHouseId(houseId);
	}

	/**
	 * 根据地块id或者网格Id查询网格id，name,管家id，name
	 * @param stagingId
	 * @param gridId
	 * @return
	 */
	@PostMapping({"/getByStagingId"})
	public List<GridBasicInfoDTO> getByStagingId(@ApiParam(name = "stagingId", value = "查询对象") @RequestBody String stagingId ,
												 @ApiParam(name = "gridId", value = "查询对象") @RequestParam String gridId) {
		return gridBasicInfoService.getByStagingId(stagingId,gridId);
	}

	@PostMapping({"/getListByOrgs"})
	public List<Map<String,Object>> getListByOrgs(@ApiParam(name = "stagingId", value = "查询对象") @RequestBody(required = false) String stagingId) {
		return gridBasicInfoService.getListByOrgs(stagingId);
	}

	@PostMapping({"/getGridByStagingId"})
	public List<GridBasicInfo> getGridByStagingId(@ApiParam(name = "stagingId", value = "查询对象") @RequestBody String stagingId) {
		return gridBasicInfoService.getGridByStagingId(stagingId);
	}
}
