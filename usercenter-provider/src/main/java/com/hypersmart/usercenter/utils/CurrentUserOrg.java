package com.hypersmart.usercenter.utils;

import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcOrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CurrentUserOrg {

    @Autowired
    UcOrgUserService ucOrgUserService;

    @Autowired
    UcOrgService ucOrgService;

    public List<UcOrg> getUserOrg(){
        IUser currentUser =  ContextUtil.getCurrentUser();
        List<UcOrgUser> list = ucOrgUserService.getUserOrg("1012");
        List<UcOrg> returnList = ucOrgService.getOrg(list);
        List<UcOrg> set = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for(UcOrg ucOrg :returnList){
            List<UcOrg> orgs = ucOrgService.getChildrenOrg(ucOrg);
            for(UcOrg org :orgs){
                if(!idList.contains(org.getId())){
                    idList.add(org.getId());
                    set.add(org);
                }
            }
        }
        return set;
    }
}
