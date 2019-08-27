package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.model.Decorate;
import com.hypersmart.usercenter.mapper.DecorateMapper;
import com.hypersmart.usercenter.service.DecorateService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 客户装修资料归档管理
 *
 * @author zcf
 * @email 1490***@qq.com
 * @date 2019-08-26 23:08:37
 */
@Service("decorateServiceImpl")
public class DecorateServiceImpl extends GenericService<String, Decorate> implements DecorateService {

    @Resource
    DecorateMapper decorateMapper;

    public DecorateServiceImpl(DecorateMapper mapper) {
        super(mapper);
    }

    /**
     * 新增装修信息
     * @param model
     * @return
     */
    @Override
    public CommonResult<String> addDecoration(Decorate model) {
        int insert = 0;
        if (model != null) {
            model.setHouseId(model.getHouseId());
            model.setIsDeleted("0");
            model.setEnabledFlag(1);
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            model.setCreatedBy(ContextUtil.getCurrentUser().getUserId());
            model.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
            model.setRowVersion(0);
            insert = decorateMapper.insert(model);
        }
        if (insert<1){
            return new CommonResult<>(Boolean.FALSE, "新增失敗");
        }
        return new CommonResult<>(Boolean.TRUE, "新增成功");
    }

    /**
     * 修改装修信息
     * @param model
     * @return
     */
    @Override
    public CommonResult<String> updateDecoration(Decorate model) {
        int result = 0;
        if(model != null && !StringUtils.isEmpty(model.getId())){
            model.setRowVersion(model.getRowVersion() == null ? 0 : model.getRowVersion() + 1);

            model.setUpdatedBy(ContextUtil.getCurrentUser().getUserId());
            model.setUpdateTime(new Date());
            result = this.updateSelective(model);
        }
        if (result < 1) {
            return new CommonResult<>(Boolean.FALSE, "修改失败");
        }
        return new CommonResult<>(Boolean.TRUE, "修改成功");
    }
}