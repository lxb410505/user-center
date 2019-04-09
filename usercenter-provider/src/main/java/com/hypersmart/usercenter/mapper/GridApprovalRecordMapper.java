package com.hypersmart.usercenter.mapper;

import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.usercenter.model.GridApprovalRecord;
import com.hypersmart.usercenter.model.GridBasicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GridApprovalRecordMapper extends GenericMapper<GridApprovalRecord> {

	/**
	 * 根据网格id，获取网格信息
	 *
	 * @param gridId
	 * @return
	 */
	GridBasicInfo getBeforeGridInfo(@Param("gridId") String gridId);

	/**
	 * 根据id，获取用户名称
	 *
	 * @param userId
	 * @return
	 */
	String getUserNameById(@Param("userId") String userId);

	/**
	 * 根据id集合，查询网格信息
	 *
	 * @param ids
	 * @return
	 */
	List<GridBasicInfo> getGridInfoByIds(@Param("ids") String[] ids);

	/**
	 * 根据管家id和组织id，获取管家基础信息
	 *
	 * @param dbName
	 * @param housekeeperId
	 * @param orgId
	 * @return
	 */
	Map<String, String> getHousekeeperInfoById(@Param("dbName") String dbName, @Param("housekeeperId") String housekeeperId, @Param("orgId") String orgId);

	/**
	 * 根据typeKey,获取数据字典信息
	 *
	 * @param dbName
	 * @param typeKey
	 * @return
	 */
	List<Map<String, String>> getSysDicsByTypeKey(@Param("dbName") String dbName, @Param("typeKey") String typeKey);

	/**
	 * 根据流程id，获取最后一次提交K2记录
	 *
	 * @param procInstId
	 * @return
	 */
	GridApprovalRecord getGridApprovalRecordByProcInstId(@Param("procInstId") String procInstId);

}
