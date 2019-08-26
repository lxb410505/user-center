package com.hypersmart.usercenter.service;


import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.framework.service.IGenericService;
import com.hypersmart.usercenter.dto.ClientRelationDTO;
import com.hypersmart.usercenter.model.House;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 【基础信息】房产
 *
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */
public interface HouseService extends IGenericService<String, House> {
    PageList<Map<String, Object>> list (QueryFilter queryFilter);
    List<Map<String,Object>> selectGridBuilding (String  id);
    List<Map<String,Object>> selectBuildingUnit (String  id);

    void exportExcel(QueryFilter queryFilter, HttpServletResponse response) throws Exception;

    PageList<ClientRelationDTO> ucMemberRelationList(QueryFilter queryFilter);
}

