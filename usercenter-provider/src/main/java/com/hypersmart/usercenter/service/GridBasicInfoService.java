package com.hypersmart.usercenter.service;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.bo.GridBasicInfoBO;
import com.hypersmart.usercenter.bo.HouseKeeperBO;
import com.hypersmart.usercenter.constant.GridErrorCode;
import com.hypersmart.usercenter.dto.GridBasicInfoDTO;
import com.hypersmart.usercenter.dto.GridBasicInfoSimpleDTO;
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
	Map<String, Object> getGridById(String id);

	/**
	 * 新增网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	GridErrorCode create(GridBasicInfoDTO gridBasicInfoDTO);

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
	GridErrorCode changeRange(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 禁用网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	GridErrorCode disableGridList(GridBasicInfoDTO gridBasicInfoDTO);

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
	 * 管家关联网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	GridErrorCode associatedGrid(GridBasicInfoDTO gridBasicInfoDTO);

	/**
	 * 管家解除关联网格
	 *
	 * @param gridBasicInfoDTO
	 * @return
	 */
	GridErrorCode disassociatedGrid(GridBasicInfoDTO gridBasicInfoDTO);


	/**
	 * 按条件查询网格
	 * @param queryFilter
	 * @return
	 */
	List<GridBasicInfo> getBasicInfo(QueryFilter queryFilter);
}

