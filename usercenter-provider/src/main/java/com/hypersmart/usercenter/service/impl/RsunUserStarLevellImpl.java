package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.dto.GoldInfo;
import com.hypersmart.usercenter.mapper.RsunUserStarLevellMapper;
import com.hypersmart.usercenter.model.RsunUserStarLevel;
import com.hypersmart.usercenter.model.RsunUserStarLevell;
import com.hypersmart.usercenter.service.RsunUserStarlLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service("rsunUserStarLevellImpl")
public class RsunUserStarLevellImpl extends GenericService<String, RsunUserStarLevell> implements RsunUserStarlLevelService {


    private static final Logger logger = LoggerFactory.getLogger(RsunUserStarLevellImpl.class);

    @Resource
    RsunUserStarLevellMapper rsunUserStarLevellMapper;

    @Override
    public PageList<RsunUserStarLevel> moneylist(QueryFilter queryFilter) {
        if (null != queryFilter) {
            PageBean pageBean = queryFilter.getPageBean();
            if (!com.hypersmart.base.util.BeanUtils.isEmpty(pageBean)) {
                PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
            } else {
                PageHelper.startPage(1, Integer.MAX_VALUE, false);
            }
            Map<String, Object> paramMap = queryFilter.getParams();
            List<RsunUserStarLevel> list = rsunUserStarLevellMapper.getRsunUserStarLevelList(paramMap);
            if (!CollectionUtils.isEmpty(list)) {
                return new PageList(list);
            } else {
                PageList<RsunUserStarLevel> pageList = new PageList<>();
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
                pageList.setPageSize(20);
                pageList.setPage(1);
                return pageList;
            }
        }
        PageList<RsunUserStarLevel> pageList = new PageList<>();
        pageList.setRows(new ArrayList<>());
        pageList.setTotal(0);
        pageList.setPageSize(20);
        pageList.setPage(1);
        return pageList;
    }


    /**
     * 修改员工等级
     *
     * @param modelMap
     * @return
     */
    @Override
    public CommonResult updateInfo(Map<String, Object> modelMap) {
        //判断入参是否存在userId  不存在 根据账号查出userId 直接新增
        if (modelMap.get("ucUserId") == null || (String) modelMap.get("ucUserId") == "") {
            //新增
            String userId = rsunUserStarLevellMapper.getUserIdByAccount((String) modelMap.get("account"));
            if (StringUtil.isEmpty(userId)) {
                return new CommonResult(false, "该账号用户信息有异常，请联系管理员");
            }
            RsunUserStarLevell rsunUserStarLevell = new RsunUserStarLevell();
            rsunUserStarLevell.setLevelSyTime(new Date());
            rsunUserStarLevell.setTotalCoin(0.0);
            rsunUserStarLevell.setUcUserId(userId);
            if (modelMap.get("pjStarIdNew") != null) {
                rsunUserStarLevell.setPjStarId((Integer) modelMap.get("pjStarIdNew"));
            } else {
                rsunUserStarLevell.setPjStarId(0);
            }
            if (modelMap.get("xzNumNew") != null) {
                rsunUserStarLevell.setXzNum((Integer) modelMap.get("xzNumNew"));
            } else {
                rsunUserStarLevell.setXzNum(0);
            }
            if(modelMap.get("xzReason") != null){
                rsunUserStarLevell.setXzReason(String.valueOf(modelMap.get("xzReason")));
            }
            if(modelMap.get("xzAttachment") != null){
                rsunUserStarLevell.setXzAttachment(String.valueOf(modelMap.get("xzAttachment")));
            }
            this.insert(rsunUserStarLevell);
        } else {
            //修改
            RsunUserStarLevell rsunUserStarLevell = new RsunUserStarLevell();
            if (modelMap.get("xzNumNew") != null) {
                rsunUserStarLevell.setLevelSyTime(new Date());
            } else {
                rsunUserStarLevell.setLevelSyTime(new Date((Long) modelMap.get("levelSyTime")));
            }
            rsunUserStarLevell.setTotalCoin(Double.valueOf((String) modelMap.get("totalCoin")));
            rsunUserStarLevell.setUcUserId((String) modelMap.get("ucUserId"));
            if (modelMap.get("pjStarIdNew") != null) {
                rsunUserStarLevell.setPjStarId((Integer) modelMap.get("pjStarIdNew"));
            } else {
                rsunUserStarLevell.setPjStarId((Integer) modelMap.get("pjStarId"));
            }
            if (modelMap.get("xzNumNew") != null) {
                rsunUserStarLevell.setXzNum((Integer) modelMap.get("xzNumNew"));
            } else {
                rsunUserStarLevell.setXzNum((Integer) modelMap.get("xzNum"));
            }
            if(modelMap.get("xzReason") != null){
                rsunUserStarLevell.setXzReason(String.valueOf(modelMap.get("xzReason")));
            }
            if(modelMap.get("xzAttachment") != null){
                rsunUserStarLevell.setXzAttachment(String.valueOf(modelMap.get("xzAttachment")));
            }

            this.update(rsunUserStarLevell);
        }
        return new CommonResult(true, "修改成功");
    }

    //全年金币job
    public void doGoldJob4Gold() {
        boolean status = false;
        StringBuffer message = new StringBuffer("");
        String replaceId = UUID.randomUUID().toString().replace("-", "");
        String type = "金币全年";

        Map record = new HashMap();
        record.put("id", replaceId);
        record.put("row_time", new Date());
        record.put("state", status);
        record.put("message", message.toString());
        record.put("type", type);
        rsunUserStarLevellMapper.insertGoldRecord(record);

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
            map.put("id", UUID.randomUUID().toString().replace("-", ""));
            map.put("sort", i + 1);
            map.put("user_name", maps.get(i).get("fullname_"));
            map.put("project", maps.get(i).get("name_"));
            map.put("gold", maps.get(i).get("total_coin"));
            map.put("year", LocalDateTime.now().getYear());
            map.put("sort_name", "第" + (i + 1) + "名");
            rsunUserStarLevellMapper.insertYear(map);

            maps2.add(map);
            //message.append("执行金币job，插入一条数据，数据具体为" + JSON.toJSONString(map) + "\\n");
        }
        logger.info("执行job: month,结束，共" + maps.size() + "条个人全年数据");
        message.append("执行job: month,结束，共" + maps.size() + "条个人全年数据\\n");
        logger.info("执行job: month");
        message.append("执行job: month \\n");
        //message.append("执行job 结束时间: month \\n"+new Date().);
        record.put("state", true);
        record.put("message", message.toString());
        record.put("end_time", new Date());
        rsunUserStarLevellMapper.updateSingleRecord(record);

        if (day <= 6) {
            //生成上个月的数据
            LocalDateTime now = LocalDateTime.now();
            now = now.plusMonths(-1);
            String month = String.valueOf(now.getMonth().getValue());
            int year = now.getYear();

            Map mapTime = new HashMap();
            mapTime.put("year",year);

            mapTime.put("month",month);
            rsunUserStarLevellMapper.deleteGoldMonth(mapTime);
            message = new StringBuffer("");
            message.append("生成上个月的数据,month:"+mapTime.get("month"));

            record.put("id", UUID.randomUUID().toString().replace("-", ""));
            record.put("type", "金币按月-last month data ");
            record.put("status", false);
            record.put("message", "");
            rsunUserStarLevellMapper.insertGoldRecord(record);
            //todo  查询上个月到这个月6号的数据，包括申诉完成日期为这个月的。然后查询当月数据时要排除当月6号申诉前的数据；
            //month为上个月的值
            String s = year + "-" + (mapTime.get("month").toString().length() == 1 ? "0" + mapTime.get("month").toString() : mapTime.get("month").toString());
            String s2 = year + "-" + (month.length() == 1 ? "0" + month.toString() :month.toString()) ;
            List<Map<String, Object>> maps1 = rsunUserStarLevellMapper.queryMonth4job(s,s2);
            for (int i = 0; i < maps1.size(); i++) {

                Map map = new HashMap();
                map.put("id", UUID.randomUUID().toString().replace("-", ""));
                map.put("sort", i + 1);
                map.put("user_name", maps1.get(i).get("fullname_"));
                map.put("project", maps1.get(i).get("name_"));
                map.put("gold", maps1.get(i).get("s"));
                map.put("year", now.getYear());
                map.put("month", mapTime.get("month"));
                map.put("sort_name", "第" + (i + 1) + "名");
                rsunUserStarLevellMapper.insertMonth(map);

            }
            logger.info("执行job: month,结束，共" + maps1.size() + "条月数据");

            record.put("status", true);
            record.put("end_date", new Date());
            record.put("message", "执行job: month,结束，共" + maps1.size() + "条月数据");
            rsunUserStarLevellMapper.updateSingleRecord(record);
        }

        //  完善业务逻辑 ，目前没有根据工单发起时间/申诉等情形进行验证

        record.put("id", UUID.randomUUID().toString().replace("-", ""));
        record.put("type", "金币按月");
        record.put("status", false);
        record.put("message", "");
        rsunUserStarLevellMapper.insertGoldRecord(record);
        String month = cal.get(Calendar.MONTH) + 1 + "";
        int year = cal.get(Calendar.YEAR);
        String s = year + "-" + (month.length() == 1 ? "0" + month : month);
        Map mapTime = new HashMap();
        mapTime.put("year",year);
        mapTime.put("month",Integer.valueOf(month));
        rsunUserStarLevellMapper.deleteGoldMonth(mapTime);


        List<Map<String, Object>> maps1 = rsunUserStarLevellMapper.queryMonth4job(s,s);
        for (int i = 0; i < maps1.size(); i++) {

            Map map = new HashMap();
            map.put("id", UUID.randomUUID().toString().replace("-", ""));
            map.put("sort", i + 1);
            map.put("user_name", maps1.get(i).get("fullname_"));
            map.put("project", maps1.get(i).get("name_"));
            map.put("gold", maps1.get(i).get("s"));
            map.put("year", LocalDateTime.now().getYear());
            map.put("month", month);
            map.put("sort_name", "第" + (i + 1) + "名");
            rsunUserStarLevellMapper.insertMonth(map);

        }
        logger.info("执行job: month,结束，共" + maps1.size() + "条月数据");

        record.put("status", true);
        record.put("end_date", new Date());
        record.put("message", "执行job: month,结束，共" + maps1.size() + "条月数据");
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
            if (params.get("month") == null || StringUtils.isRealEmpty(params.get("month").toString())) {
                logger.info("查询全年");

                query = this.rsunUserStarLevellMapper.queryYear(null);
                return new PageList<>(query);
            } else {
                logger.info("查询月份");
                int month_ = Integer.valueOf(params.get("month").toString());//单月份去掉0
                params.put("month",month_);
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
        String year = cal.get(Calendar.YEAR) + "";
        int day = cal.get(Calendar.DATE);
        String month = cal.get(Calendar.MONTH) + 1 + "";
//        flag = day < 6 && year.equals(queryFilter.getParams().get("year").toString()) && month.equals(queryFilter.getParams().get("month").toString());
//        //如果查询当月不足6号的数据，直接不用查了。
//        if (flag) {
//            return true;
//        }
//        return false;
        return false;
    }


    //全年徽章job
    public void doGoldJob4Badge() {
        boolean status = false;
        StringBuffer message = new StringBuffer("");
        String replaceId = UUID.randomUUID().toString().replace("-", "");
        String type = "徽章全年";

        Map record = new HashMap();
        record.put("id", replaceId);
        record.put("row_time", new Date());
        record.put("state", status);
        record.put("message", message.toString());
        record.put("type", type);
        rsunUserStarLevellMapper.insertGoldRecord(record);

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);

        logger.info("徽章执行job");
        //删除掉统计表；
        rsunUserStarLevellMapper.deleteGoldYear4Badge();

        List<Map<String, Object>> maps = rsunUserStarLevellMapper.queryYear4job4Badge();
        List<Map<String, Object>> maps2 = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {

            Map map = new HashMap();
            map.put("id", UUID.randomUUID().toString().replace("-", ""));
            map.put("sort", i + 1);
            map.put("user_name", maps.get(i).get("fullname_"));
            map.put("project", maps.get(i).get("name_"));
            map.put("gold", maps.get(i).get("xz_num"));
            map.put("year", LocalDateTime.now().getYear());
            map.put("sort_name", "第" + (i + 1) + "名");
            rsunUserStarLevellMapper.insertYear4Badge(map);

            maps2.add(map);
        }
        logger.info("徽章执行job: month,结束，共" + maps.size() + "条个人全年徽章数据");

        logger.info("徽章执行job: month");

        record.put("state", true);
        record.put("message", message.toString());
        record.put("end_time", new Date());
        rsunUserStarLevellMapper.updateSingleRecord(record);


        record.put("id", UUID.randomUUID().toString().replace("-", ""));
        record.put("type", "徽章按月");
        record.put("status", false);
        record.put("message", "");
        rsunUserStarLevellMapper.insertGoldRecord(record);

        rsunUserStarLevellMapper.deleteGoldMonth4Badge();

        String month = cal.get(Calendar.MONTH) + 1 + "";
        int year = cal.get(Calendar.YEAR);
        String s = year + "-" + (month.length() == 1 ? "0" + month : month);
        List<Map<String, Object>> maps1 = rsunUserStarLevellMapper.queryMonth4job4Badge(s);
        for (int i = 0; i < maps1.size(); i++) {

            Map map = new HashMap();
            map.put("id", UUID.randomUUID().toString().replace("-", ""));
            map.put("sort", i + 1);
            map.put("user_name", maps1.get(i).get("fullname_"));
            map.put("project", maps1.get(i).get("name_"));
            map.put("gold", maps1.get(i).get("s"));
            map.put("year", LocalDateTime.now().getYear());
            map.put("month", month);
            map.put("sort_name", "第" + (i + 1) + "名");
            rsunUserStarLevellMapper.insertMonth4Badge(map);

        }
        logger.info("徽章执行job: month,结束，共" + maps1.size() + "条月徽章数据");

        record.put("status", true);
        record.put("end_date", new Date());
        record.put("message", "执行job: month,结束，共" + maps1.size() + "条月数据");
        rsunUserStarLevellMapper.updateSingleRecord(record);
    }

    @Override
    public PageList<GoldInfo> getUserGold4Part(QueryFilter queryFilter) {
        logger.info("查询第三方金币数据");
        if (queryFilter != null) {
            PageBean pageBean = queryFilter.getPageBean();
            if (BeanUtils.isEmpty(pageBean)) {
                PageHelper.startPage(1, Integer.MAX_VALUE, false);
            } else {
                PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                                pageBean.showTotal());
            }
            List<GoldInfo> query = new ArrayList<>();
            Map<String, Object> params = queryFilter.getParams();
            String month;
            String year;
            String time ="";
            //设置查询时间
            if(params.get("year") != null &&!StringUtils.isRealEmpty(params.get("year").toString())){
                year = params.get("year").toString();
                time +=year;
            }
            if (params.get("month") != null && !StringUtils.isRealEmpty(params.get("month").toString())) {
                month = params.get("month").toString();
                time +="-"+(month.length() == 1 ? "0" + month : month);
            }
            if(time.length()>0){
                params.put("time",time);
            }else{
                params.remove("time");
            }

            //todo 校验项目字段

            query = this.rsunUserStarLevellMapper.get4Part(params);
            return new PageList<>(query);

        } else {
            PageList<GoldInfo> pageList = new PageList();
            pageList.setTotal(0);
            pageList.setPage(1);
            pageList.setPageSize(10);
            pageList.setRows(new ArrayList<>());
            return pageList;
        }


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
            if (params.get("month") == null || StringUtils.isRealEmpty(params.get("month").toString())) {
                logger.info("查询全年");

                query = this.rsunUserStarLevellMapper.queryYear4Badge(null);
                return new PageList<>(query);
            } else {
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
    public int insertBadge(Map map) {
        //判断用户ID是否存在  不存在则查询userID
        if (map.get("uc_user_id") == null || (String) map.get("uc_user_id") == "") {
            String userId = rsunUserStarLevellMapper.getUserIdByAccount((String) map.get("account"));
            if (StringUtil.isEmpty(userId)) {
                return -1;
            }
            map.put("uc_user_id", userId);
        }
        map.put("jb_jl_time", new Date());
        map.put("jb_originnal_time", new Date());
        //历史记录增加字段
        if(null==map.get("xz_reason")){
            map.put("xz_reason", null);
        }
        if(null==map.get("xz_attachment")){
            map.put("xz_attachment", null);
        }

        return rsunUserStarLevellMapper.insertBadgeHistory(map);
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        now = now.plusMonths(-1);
        Month month = now.getMonth();
        System.out.println(month.getValue());
        System.out.println(now.getYear());
    }

    /**
     * 获取勋章记录列表
     * @param queryFilter
     * @return
     */
    @Override
    public PageList<Map<String, Object>> getUserMedalRecordList(QueryFilter queryFilter) {
        if (queryFilter != null) {
            PageBean pageBean = queryFilter.getPageBean();
            if (!com.hypersmart.base.util.BeanUtils.isEmpty(pageBean)) {
                PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
            } else {
                PageHelper.startPage(1, Integer.MAX_VALUE, false);
            }
            Map<String, Object> paramMap = queryFilter.getParams();
            List<Map<String, Object>> list = rsunUserStarLevellMapper.getUserMedalRecordList(paramMap);
            if (!CollectionUtils.isEmpty(list)) {
                return new PageList(list);
            } else {
                PageList<Map<String, Object>> pageList = new PageList<>();
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
                pageList.setPageSize(20);
                pageList.setPage(1);
                return pageList;
            }
        }
        PageList<Map<String, Object>> pageList = new PageList<>();
        pageList.setRows(new ArrayList<>());
        pageList.setTotal(0);
        pageList.setPageSize(20);
        pageList.setPage(1);
        return pageList;
    }
    

}
