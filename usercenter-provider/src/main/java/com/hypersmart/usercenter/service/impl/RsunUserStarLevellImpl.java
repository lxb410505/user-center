package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSON;
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

    //全年金币job
    public void doGoldJob4Gold(){
        boolean status= false;
        StringBuffer message = new StringBuffer("");
        String replaceId = UUID.randomUUID().toString().replace("-", "");
        String type = "金币全年";

        Map record = new HashMap();
        record.put("id",replaceId);
        record.put("row_time",new Date());
        record.put("state",status);
        record.put("message",message.toString());
        record.put("type",type);
        rsunUserStarLevellMapper .insertGoldRecord(record);

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);

        logger.info("执行金币job \\n");
        message.append("执行金币job +\\n");
        //删除掉统计表；
        rsunUserStarLevellMapper.deleteGoldYear();

        List<Map<String, Object>> maps = rsunUserStarLevellMapper.queryYear4job();
        List<Map<String, Object>> maps2 = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {

            Map map = new HashMap();
            map.put("id",UUID.randomUUID().toString().replace("-",""));
            map.put("sort",i+1);
            map.put("user_name", maps.get(i).get("fullname_"));
            map.put("project", maps.get(i).get("name_"));
            map.put("gold", maps.get(i).get("total_coin"));
            map.put("year", 2019);
            map.put("sort_name", "第"+(i+1)+"名");
            rsunUserStarLevellMapper.insertYear(map);

            maps2.add(map);
            message.append("执行金币job，插入一条数据，数据具体为"+ JSON.toJSONString(map)+"\\n");
        }
        logger.info("执行job: month,结束，共"+maps.size()+"条个人全年数据");
        message.append("执行job: month,结束，共"+maps.size()+"条个人全年数据\\n");
        logger.info("执行job: month");
        message.append("执行job: month \\n");
        //message.append("执行job 结束时间: month \\n"+new Date().);
        record.put("state",true);
        record.put("message",message.toString());
        record.put("end_time",new Date());
        rsunUserStarLevellMapper.updateSingleRecord(record);

        if(day!=6){
            return;
        }

        //  完善业务逻辑 ，目前没有根据工单发起时间/申诉等情形进行验证

        record.put("id",UUID.randomUUID().toString().replace("-",""));
        record.put("type","金币按月");
        record.put("status",false);
        record.put("message","");
        rsunUserStarLevellMapper.insertGoldRecord(record);

        rsunUserStarLevellMapper.deleteGoldMonth();

        String  month = cal.get(Calendar.MONTH) + 1 +"";
        int year = cal.get(Calendar.YEAR);
        String s = year +""+(month.length()==1?"0"+month:month);
        List<Map<String, Object>> maps1 = rsunUserStarLevellMapper.queryMonth4job(s);
        for (int i = 0; i < maps1.size(); i++) {

            Map map = new HashMap();
            map.put("id",UUID.randomUUID().toString().replace("-",""));
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

        record.put("status",true);
        record.put("end_date",new Date());
        record.put("message","执行job: month,结束，共"+maps1.size()+"条月数据");
        rsunUserStarLevellMapper.updateSingleRecord(record);

    }

    public RsunUserStarLevellImpl(RsunUserStarLevellMapper rsunUserStarLevellMapper) {
        super(rsunUserStarLevellMapper);
    }
    public PageList<Map<String, Object>> quertList(QueryFilter queryFilter) {
        if (checkIsHaveData(queryFilter)) return new PageList<>();

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
            if(StringUtils.isRealEmpty(params.get("month").toString())){
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

    }

    private boolean checkIsHaveData(QueryFilter queryFilter) {
        boolean flag = false;

        Calendar cal = Calendar.getInstance();
        String year = cal.get(Calendar.YEAR)+"";
        int day = cal.get(Calendar.DATE);
        String  month = cal.get(Calendar.MONTH) + 1 +"";
        flag =day<6 &&year.equals(queryFilter.getParams().get("year").toString())&&month.equals(queryFilter.getParams().get("month").toString());
        //如果查询当月不足6号的数据，直接不用查了。
        if(flag){
            return true;
        }
        return false;
    }


    //全年徽章job
    public void doGoldJob4Badge(){
        boolean status= false;
        StringBuffer message = new StringBuffer("");
        String replaceId = UUID.randomUUID().toString().replace("-", "");
        String type = "金币全年";

        Map record = new HashMap();
        record.put("id",replaceId);
        record.put("row_time",new Date());
        record.put("state",status);
        record.put("message",message.toString());
        record.put("type",type);
        rsunUserStarLevellMapper .insertGoldRecord(record);

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);

        logger.info("徽章执行job");
        //删除掉统计表；
        rsunUserStarLevellMapper.deleteGoldYear4Badge();

        List<Map<String, Object>> maps = rsunUserStarLevellMapper.queryYear4job4Badge();
        List<Map<String, Object>> maps2 = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {

            Map map = new HashMap();
            map.put("id",UUID.randomUUID().toString().replace("-",""));
            map.put("sort",i+1);
            map.put("user_name", maps.get(i).get("fullname_"));
            map.put("project", maps.get(i).get("name_"));
            map.put("badge", maps.get(i).get("total_coin"));
            map.put("year", 2019);
            map.put("sort_name", "第"+(i+1)+"名");
            rsunUserStarLevellMapper.insertYear4Badge(map);

            maps2.add(map);
        }
        logger.info("徽章执行job: month,结束，共"+maps.size()+"条个人全年徽章数据");

        logger.info("徽章执行job: month");

        record.put("state",true);
        record.put("message",message.toString());
        record.put("end_time",new Date());
        rsunUserStarLevellMapper.updateSingleRecord(record);

        if(day!=6){
            return;
        }
        record.put("id",UUID.randomUUID().toString().replace("-",""));
        record.put("type","徽章按月");
        record.put("status",false);
        record.put("message","");
        rsunUserStarLevellMapper.insertGoldRecord(record);

        rsunUserStarLevellMapper.deleteGoldMonth4Badge();

        String  month = cal.get(Calendar.MONTH) + 1 +"";
        int year = cal.get(Calendar.YEAR);
        String s = year +""+(month.length()==1?"0"+month:month);
        List<Map<String, Object>> maps1 = rsunUserStarLevellMapper.queryMonth4job4Badge(s);
        for (int i = 0; i < maps1.size(); i++) {

            Map map = new HashMap();
            map.put("id",UUID.randomUUID().toString().replace("-",""));
            map.put("sort",i+1);
            map.put("user_name", maps.get(i).get("fullname_"));
            map.put("project", maps.get(i).get("name_"));
            map.put("badge", maps.get(i).get("total_coin"));
            map.put("year", 2019);
            map.put("month", month);
            map.put("sort_name", "第"+(i+1)+"名");
            rsunUserStarLevellMapper.insertMonth4Badge(map);

        }
        logger.info("徽章执行job: month,结束，共"+maps1.size()+"条月徽章数据");

        record.put("status",true);
        record.put("end_date",new Date());
        record.put("message","执行job: month,结束，共"+maps1.size()+"条月数据");
        rsunUserStarLevellMapper.updateSingleRecord(record);
    }

    @Override
    public PageList<Map<String, Object>> quertList4Badge(QueryFilter queryFilter) {
        logger.info("查询徽章数据");
        if (checkIsHaveData(queryFilter)) return new PageList<>();

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

                query = this.rsunUserStarLevellMapper.queryYear4Badge(null);
                return new PageList<>(query);
            }else {
                logger.info("查询月份");

                query = this.rsunUserStarLevellMapper.queryMonth4Badge(queryFilter.getParams());
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

    }
    @Override
    public int insertBadge(Map map){
        map.put("jb_jl_time",new Date());
        map.put("jb_originnal_time",new Date());

        return rsunUserStarLevellMapper.insertBadgeHistory(map);
    }
}
