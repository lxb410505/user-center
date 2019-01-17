package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONArray;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public Integer deleteRangeByGridId(String gridId) {
        return gridRangeMapper.deleteRangeByGridId(gridId);
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
                gridRange.setId(UUID.randomUUID().toString());
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
        if(gridRangeBO == null || StringUtils.isEmpty(gridRangeBO.getGridId())) {
            List<GridRange> gridRangeList = this.getAll();
            if(!CollectionUtils.isEmpty(gridRangeList)) {
                for(GridRange gridRange : gridRangeList) {
                    if("2".equals(gridRange.getRangeType())) {
                        stringList.add(gridRange.getResourceId());
                    }
                }
            }
        }
        return stringList;
    }
}