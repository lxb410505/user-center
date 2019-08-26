package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.model.Parking;
import com.hypersmart.usercenter.mapper.ParkingMapper;
import com.hypersmart.usercenter.service.ParkingService;
import groovy.ui.view.MacOSXDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 【基础信息】停车位
 *
 * @author zcf
 * @email 1490318946@qq.com
 * @date 2019-08-21 16:19:42
 */
@Service("parkingServiceImpl")
public class ParkingServiceImpl extends GenericService<String, Parking> implements ParkingService {

    public ParkingServiceImpl(ParkingMapper mapper) {
        super(mapper);
    }

    @Resource
    private ParkingMapper parkingMapper;

    /**
     * 新增车位信息
     *
     * @param model
     */
    @Override
    public CommonResult<String> addParking(Parking model) {
        int insert = 0;
        if (model != null) {
            model.setParkingNum(model.getParkingNum());
            model.setRentalPropertyNum(model.getRentalPropertyNum());
            model.setPlateNumber(model.getPlateNumber());
            model.setCarBrands(model.getCarBrands());
            model.setCarColor(model.getCarColor());
            model.setCardNumber(model.getCardNumber());
            model.setLeaseTermBegin(model.getLeaseTermBegin());
            model.setLeaseTermEnd(model.getLeaseTermEnd());
            model.setFee(model.getFee());

            model.setIsDeleted(0);
            model.setEnabledFlag(1);
            model.setCreationDate(new Date());
            model.setUpdationDate(new Date());
            model.setCreatedBy(ContextUtil.getCurrentUser().getUserId());
            model.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
            model.setRowVersion(0);
            insert = parkingMapper.insert(model);
        }
        if (insert < 1) {
            return new CommonResult<>(Boolean.FALSE, "新增失敗");
        }
        return new CommonResult<>(Boolean.TRUE, "新增成功");


    }

    /**
     * 修改车位信息
     *
     * @param model
     */
    @Override
    public CommonResult<String> updateParking(Parking model) {
        int i = 0;
        if (model != null && !StringUtils.isEmpty(model.getId())) {
            model.setRowVersion(model.getRowVersion() == null ? 0 : model.getRowVersion() + 1);

            model.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
            model.setUpdationDate(new Date());
            i = this.updateSelective(model);
        }

        if (i < 1) {
            return new CommonResult<>(Boolean.FALSE, "修改失败");
        }
        return new CommonResult<>(Boolean.TRUE, "修改成功");
    }
}