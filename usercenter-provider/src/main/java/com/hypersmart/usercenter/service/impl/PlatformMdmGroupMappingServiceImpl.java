package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.dto.UcOrgDTO;
import com.hypersmart.usercenter.model.PlatformMdmGroupMapping;
import com.hypersmart.usercenter.mapper.PlatformMdmGroupMappingMapper;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.PlatformMdmGroupMappingService;
import com.hypersmart.usercenter.service.UcOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 平台组团映射表
 *
 * @author lily
 * @email lily
 * @date 2019-12-18 12:51:08
 */
@Service("platformMdmGroupMappingServiceImpl")
public class PlatformMdmGroupMappingServiceImpl extends GenericService<String, PlatformMdmGroupMapping> implements PlatformMdmGroupMappingService {
    @Resource
    UcOrgService ucOrgService;

    public PlatformMdmGroupMappingServiceImpl(PlatformMdmGroupMappingMapper mapper) {
        super(mapper);
    }


   public List<UcOrgDTO> queryByGrade(String userId, String grade){
       List<UcOrg> orgList= ucOrgService.queryByGrade(userId,grade);
       if(BeanUtils.isNotEmpty(orgList)){
           List<UcOrgDTO> result=new ArrayList<>();
           List<String> idList=new ArrayList<>();
           Map<String,UcOrg> mapList=new HashMap<>();
           for(UcOrg org:orgList){
               idList.add(org.getId());
               mapList.put(org.getId(),org);
           }
           String ids = String.join(",",idList);
           QueryFilter queryFilter=QueryFilter.build();
           queryFilter.addFilter("orgid", ids, QueryOP.IN);
           queryFilter.addFilter("isDele","1", QueryOP.NOT_EQUAL);
           PageList<PlatformMdmGroupMapping> pageList = this.query(queryFilter);
           if(pageList!=null && pageList.getRows()!=null && pageList.getRows().size()>0){
               for(PlatformMdmGroupMapping item :pageList.getRows()){
                   UcOrg org= mapList.get(item.getOrgid());
                   UcOrgDTO resultItem=new UcOrgDTO(org);
                   resultItem.setGrpid(item.getGrpid());
                   resultItem.setGrpname(item.getGrpname());
                   result.add(resultItem);
               }
               return result;
           }
           else {
               return new ArrayList<>();
           }
       }
       else {
           return new ArrayList<>();
       }
   }
}