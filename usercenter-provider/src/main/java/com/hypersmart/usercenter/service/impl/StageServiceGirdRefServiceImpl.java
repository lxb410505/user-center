package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.constant.GridTypeConstants;
import com.hypersmart.usercenter.mapper.GridBasicInfoMapper;
import com.hypersmart.usercenter.mapper.StageServiceGirdRefMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.StageServiceGirdRef;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.StageServiceGirdRefService;
import com.hypersmart.usercenter.service.UcOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("StageServiceGirdRefServiceImpl")
public class StageServiceGirdRefServiceImpl extends GenericService<String, StageServiceGirdRef> implements StageServiceGirdRefService {
    @Resource
    private GridBasicInfoMapper gridBasicInfoMapper;
    @Resource
    private StageServiceGirdRefMapper stageServiceGirdRefMapper;
    @Resource
    private UcOrgService ucOrgService;

    public StageServiceGirdRefServiceImpl(GenericMapper<StageServiceGirdRef> genericMapper) {
        super(genericMapper);
    }

    @Override
    public List<String> getExistStagingIds() {
        List<UcOrg> userOrgList = ucOrgService.getUserOrgList(ContextUtil.getCurrentUser().getUserId());
        List<String> stagingIds = userOrgList.stream().filter(u -> "ORG_DiKuai".equals(u.getGrade())).map(UcOrg::getId).collect(Collectors.toList());
        QueryFilter queryFilter=QueryFilter.build(StageServiceGirdRef.class);
        queryFilter.addFilter("enabledFlag",1, QueryOP.EQUAL, FieldRelation.AND);
        queryFilter.addFilter("isDeleted",0, QueryOP.EQUAL, FieldRelation.AND);
        queryFilter.addFilter("stagingId",stagingIds, QueryOP.IN, FieldRelation.AND);
        List<String> ids1 = stageServiceGirdRefMapper.getServiceGridByStagingIds(queryFilter.getParams());
//        queryFilter.setClazz(GridBasicInfo.class);
//        queryFilter.addFilter("gridType", GridTypeConstants.SERVICE_CENTER_GRID,QueryOP.EQUAL,FieldRelation.AND);
//        List<String> ids2 = gridBasicInfoMapper.getServiceGridByStagingIds(queryFilter.getParams());
//        ids1.removeAll(ids2);
//        ids1.addAll(ids2);
        return ids1;
    }
}
