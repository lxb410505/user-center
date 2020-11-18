package com.hypersmart.usercenter.service;

import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.model.UcRole;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public interface UcRoleService extends IGenericService<String, UcRole> {
    List<UcRole> getRolesByUserId(String userId) throws Exception;
}

