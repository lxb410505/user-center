package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hypersmart.base.util.UniqueIdUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.bo.GridRangeBO;
import com.hypersmart.usercenter.dto.GridRangeInfo;
import com.hypersmart.usercenter.mapper.GridRangeMapper;
import com.hypersmart.usercenter.model.GridRange;
import com.hypersmart.usercenter.service.GridRangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 网格覆盖范围表
 *
 * @author fangyuan
 * @email fyass***.163.com
 * @date 2019-01-17 12:48:37
 */
@Service("gridRangeServiceImpl")
public class GridRangeServiceImpl extends GenericService<String, GridRange> implements GridRangeService {

    @Resource
    private GridRangeMapper gridRangeMapper;

    public GridRangeServiceImpl(GridRangeMapper mapper) {
        super(mapper);
    }

    /**
     * 删除历史记录
     */
    @Override
    public Integer deleteRangeByGridIds(String[] ids) {
        return gridRangeMapper.deleteRangeByGridIds(ids);
    }

    /**
     * 记录数据
     * @param gridRangeRecord
     * @return
     */
    @Override
    public Integer recordRange(String gridRangeRecord,String gridId) {
        Integer num = 0;
        //新增记录
        JSONArray jsonArray = JSONArray.parseArray(gridRangeRecord);
        List<GridRangeInfo> gridRangeInfoList = jsonArray.toJavaList(GridRangeInfo.class);
        List<GridRange> gridRangeList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(gridRangeInfoList)) {
            for(GridRangeInfo gridRangeInfo : gridRangeInfoList) {
                GridRange gridRange = new GridRange();
                gridRange.setId(UniqueIdUtil.getSuid());
                gridRange.setGridId(gridId);
                gridRange.setRangeType(gridRangeInfo.getLevel().toString());
                gridRange.setResourceId(gridRangeInfo.getId());
                gridRange.setEnabledFlag(1);
                gridRange.setIsDeleted(0);
                gridRange.setCreationDate(new Date());
                gridRange.setUpdationDate(new Date());
                gridRangeList.add(gridRange);
            }
        }
        if(!CollectionUtils.isEmpty(gridRangeList)) {
            num = this.insertBatch(gridRangeList);
        }
        return num;
    }

    /**
     * 获取所有覆盖范围
     * @param gridRangeBO
     * @return
     */
    @Override
    public List<String> getAllRange(GridRangeBO gridRangeBO) {
        List<String> stringList = new ArrayList<>();
        List<GridRange> gridRangeList = gridRangeMapper.getRange(gridRangeBO);
        if(!CollectionUtils.isEmpty(gridRangeList)) {
            for(GridRange gridRange : gridRangeList) {
                if("3".equals(gridRange.getRangeType())) {
                    stringList.add(gridRange.getResourceId());
                }
            }
        }
        return stringList;
    }

    /**
     * 判断房产是否已经被覆盖
     * @param gridRangeRecord
     * @return
     */
    @Override
    public boolean judgeExistHouse(String gridRangeRecord, String gridId, String stagingId) {
        GridRangeBO gridRangeBO = new GridRangeBO();
        gridRangeBO.setGridId(gridId);
        gridRangeBO.setStagingId(stagingId);
        List<GridRange> gridRangeList = gridRangeMapper.getRange(gridRangeBO);
        Map<String,GridRange>  gridRangeMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(gridRangeList)) {
            for(GridRange gridRange : gridRangeList) {
                if("3".equals(gridRange.getRangeType())) {
                    gridRangeMap.put(gridRange.getResourceId(),gridRange);
                }
            }
        }
        JSONArray jsonArray = JSONArray.parseArray(gridRangeRecord);
        List<GridRangeInfo> gridRangeInfoList = jsonArray.toJavaList(GridRangeInfo.class);
        if(!CollectionUtils.isEmpty(gridRangeInfoList)) {
            for(GridRangeInfo gridRangeInfo : gridRangeInfoList) {
                if(gridRangeInfo.getLevel().equals(3) && gridRangeMap.get(gridRangeInfo.getId()) != null) {
                 return true;
                }
            }
        }
        return false;
    }

}