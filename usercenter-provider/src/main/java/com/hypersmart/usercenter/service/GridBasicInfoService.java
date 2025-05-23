package com.hypersmart.usercenter.service;

import com.github.pagehelper.PageInfo;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
import com.hypersmart.usercenter.dto.RangeDTO;
import com.hypersmart.usercenter.model.GridBasicInfo;

import java.util.List;
import java.util.Map;

/**
 * 网格基础信息表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-10 11:23:58
 */
public interface GridBasicInfoService extends IGenericService<String, GridBasicInfo> {


	/**
	 * 网格分页查询
	 *
	 * @param queryFilter
	 * @param type  0 网格管理页面    1 管家关联网格页面  2 管家解除关联页面
	 * @return
	 */
	PageList<Map<String, Object>> quertList(QueryFilter queryFilter, int type);


	/**
	 * 获取指定id的网格信息
	 *
	 * @param id
	 * @return
	 */
	Map<String, Object> getGridById(String id,String stagingId);

	/**
	 * 新增网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	CommonResult<String> create(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 修改网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	GridErrorCode edit(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 变更管家
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	GridErrorCode changeHousekeeper(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 变更映射楼栋
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	CommonResult<String> changeRange(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 禁用网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	CommonResult<String> disableGridList(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 删除网格
	 *
	 * @param gridBasicInfoBO
	 * @return
	 */
	GridErrorCode deleteGridList(GridBasicInfoBO gridBasicInfoBO);

	/**
	 * 根据管家id和分期id获得 网格id和名称
	 *
	 * @param houseKeeperBO
	 * @return
	 */
	List<GridBasicInfoSimpleDTO> getGridBasicInfoByHouseKeeperIds(List<HouseKeeperBO> houseKeeperBO);

	/**
	 * 根据分期id获得该分期下未关联的分期
	 *
	 * @param divideId
	 * @return
	 */
	List<GridBasicInfo> getDisassociatedGridByDivideId(String divideId);

	/**
	 * 根据地块id，获取地块下的楼栋网格
	 *
	 * @param massifId
	 * @return
	 */
	List<GridBasicInfo> getGridsBymassifId(String massifId);

	/**
	 * 根据系统id,获取地块下的楼栋网格
	 * @param massifId
	 * @return
	 */
	List<GridBasicInfo> getGridsBySmcloudmassifId(String massifId);

	/**
	 * 管家关联网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	CommonResult<String> associatedGrid(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 管家解除关联网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	CommonResult<String> disassociatedGrid(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 根据地块id，获取地块下的楼栋网格覆盖的房产
	 *
	 * @param massifId
	 * @return
	 */
	List<RangeDTO> getGridsHouseBymassifId(String massifId);

	/**
	 * 根据房产id获取网格
	 * @param gridRange
	 * @return
	 */
	List<GridBasicInfo> getByGridRange(String gridRange);

	/**
	 *  更新网格覆盖房产信息
	 * @param gridId
	 * @param gridRange
	 * @param action
	 */
	void handChangeRange(String gridId,String gridRange,Integer action);

	List<Map<String, Object>> getHouseByCondition(String divide, String id, Integer pageNum, Integer pageSize);

	PageInfo<GridBasicInfo> getGridsBySmcloudmassifIdPage(String massifId, Integer page, Integer pageSize);
	List<Map<String,Object>> getGridsHouseBymassifIdPage(String massifId, Integer page, Integer pageSize);
	Integer getPublicGridNum(String id);
	Map<String,Object> getByHouseId(String houseId);

	/**
	 * 根据地块Id查询下面的网格及网格管家姓名
	 * @param stagingId
	 * @return
	 */
	List<GridBasicInfoDTO> getByStagingId(String stagingId,String gridId);

	/**
	 * 根据orgs查询网格
	 * @param stagingId
	 * @return
	 */
	List<Map<String,Object>> getListByOrgs(String stagingId);

	void realDeleteGridList(String id,String gridType);

	List<GridBasicInfo> getGridByStagingId(String stagingId);
}

