package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.mapper.GenericMapper;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.controller.GridApprovalRecordController;
import com.hypersmart.usercenter.mapper.RsunUserStarLevellMapper;
import com.hypersmart.usercenter.mapper.UcUserMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.model.UcUser;
import com.hypersmart.usercenter.service.RsunUserStarlLevelService;
import com.hypersmart.usercenter.service.UcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("rsunUserStarLevellImpl")
public class  RsunUserStarLevellImpl extends GenericService<String, RsunUserStarLevell> implements RsunUserStarlLevelService {

    private static final Logger logger = LoggerFactory.getLogger(RsunUserStarLevellImpl.class);

    @Resource
    RsunUserStarLevellMapper rsunUserStarLevellMapper;
    //全年job
    public void doGoldJob(){
        logger.info("执行job");
        //删除掉统计表；
        rsunUserStarLevellMapper.deleteGoldYear();

        List<Map<String, Object>> maps = rsunUserStarLevellMapper.queryYear4job();
        List<Map<String, Object>> maps2 = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {

            Map map = new HashMap();
            map.put("sort",i+1);
            map.put("user_name", maps.get(i).get("fullname_"));
            map.put("project", maps.get(i).get("name_"));
            map.put("gold", maps.get(i).get("total_coin"));
            map.put("year", 2019);
            map.put("sort_name", "第"+(i+1)+"名");
            rsunUserStarLevellMapper.insertYear(map);

            maps2.add(map);
        }
        logger.info("执行job: month,结束，共"+maps.size()+"条个人全年数据");

        logger.info("执行job: month");

        //todo  完善业务逻辑 ，目前没有根据工单发起时间/申诉等情形进行验证
        rsunUserStarLevellMapper.deleteGoldMonth();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        String  month = cal.get(Calendar.MONTH) + 1 +"";
        int year = cal.get(Calendar.YEAR);
        String s = year +""+(month.length()==1?"0"+month:month);
        List<Map<String, Object>> maps1 = rsunUserStarLevellMapper.queryMonth4job(s);
        for (int i = 0; i < maps1.size(); i++) {

            Map map = new HashMap();
            map.put("sort",i+1);
            map.put("user_name", maps.get(i).get("fullname_"));
            map.put("project", maps.get(i).get("name_"));
            map.put("gold", maps.get(i).get("total_coin"));
            map.put("year", 2019);
            map.put("month", month);
            map.put("sort_name", "第"+(i+1)+"名");
            rsunUserStarLevellMapper.insertMonth(map);

        }
        logger.info("执行job: month,结束，共"+maps1.size()+"条月数据");

    }

    public RsunUserStarLevellImpl(RsunUserStarLevellMapper rsunUserStarLevellMapper) {
        super(rsunUserStarLevellMapper);
    }
    public PageList<Map<String, Object>> quertList(QueryFilter queryFilter) {
        if (queryFilter != null) {
            PageBean pageBean = queryFilter.getPageBean();
            if (BeanUtils.isEmpty(pageBean)) {
                PageHelper.startPage(1, Integer.MAX_VALUE, false);
            } else {
                PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                                pageBean.showTotal());
            }
            List<Map<String, Object>> query = new ArrayList<>();
            Map<String, Object> params = queryFilter.getParams();
            if(StringUtils.isRealEmpty(params.get("year").toString())){
                logger.info("查询全年");

                query = this.rsunUserStarLevellMapper.queryYear(null);
                return new PageList<>(query);
            }else {
                logger.info("查询月份");

                query = this.rsunUserStarLevellMapper.queryMonth(queryFilter.getParams());
                return new PageList<>(query);
            }


        } else {
            PageList<Map<String, Object>> pageList = new PageList();
            pageList.setTotal(0);
            pageList.setPage(1);
            pageList.setPageSize(10);
            pageList.setRows(new ArrayList<>());
            return pageList;
        }
        return null;
    }
}
