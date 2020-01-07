package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.feign.PortalFeignService;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.bo.EngineeringGrabOrdersDataInsightBO;
import com.hypersmart.usercenter.dto.CoinStatisticsListDTO;
import com.hypersmart.usercenter.dto.CoinStatisticsListForExportDTO;
import com.hypersmart.usercenter.mapper.RsunJbHiRewardMapper;
import com.hypersmart.usercenter.model.RsunJbHiReward;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.model.UcOrgUser;
import com.hypersmart.usercenter.service.RsunJbHiRewardService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcOrgUserService;
import com.hypersmart.usercenter.util.DateUtil;
import com.hypersmart.usercenter.util.ExportExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.code.ORDER;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("rsunJbHiRewardServiceImpl")
public class RsunJbHiRewardServiceImpl extends GenericService<String, RsunJbHiReward> implements RsunJbHiRewardService {


    @Autowired
    private RsunJbHiRewardMapper rsunJbHiRewardMapper;

    @Autowired
    private PortalFeignService portalFeignService;

    @Autowired
    private UcOrgService ucOrgService;

    @Autowired
    UcOrgUserService ucOrgUserService;

    private static final Logger logger = LoggerFactory.getLogger(RsunUserStarLevellImpl.class);

    public RsunJbHiRewardServiceImpl(RsunJbHiRewardMapper genericMapper) {
        super(genericMapper);
    }


    /**
     * 查询员工各月份金币数列表
     *
     * @param queryFilter
     * @return
     */
    @Override
    public PageList<CoinStatisticsListDTO> coinStatisticsList(QueryFilter queryFilter) {
        if (null != queryFilter) {
            PageBean pageBean = queryFilter.getPageBean();
            if (!com.hypersmart.base.util.BeanUtils.isEmpty(pageBean)) {
                PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
            } else {
                PageHelper.startPage(1, Integer.MAX_VALUE, false);
            }
            Map<String, Object> paramMap = queryFilter.getParams();
            List<QueryField> queryFields=queryFilter.getQuerys();
            for(QueryField queryField:queryFields){
                if(StringUtils.pathEquals(queryField.getProperty(),"projectForSearch")){
                    paramMap.put("projectForSearch",queryField.getValue());
                }
                if(StringUtils.pathEquals(queryField.getProperty(),"jobForSearch")){
                    paramMap.put("jobForSearch",queryField.getValue());
                }
            }
            List<CoinStatisticsListDTO> list = rsunJbHiRewardMapper.getCoinStatisticsList(paramMap);
            if (!CollectionUtils.isEmpty(list)) {
                return new PageList(list);
            } else {
                PageList<CoinStatisticsListDTO> pageList = new PageList<>();
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0);
                pageList.setPageSize(20);
                pageList.setPage(1);
                return pageList;
            }
        }
        PageList<CoinStatisticsListDTO> pageList = new PageList<>();
        pageList.setRows(new ArrayList<>());
        pageList.setTotal(0);
        pageList.setPageSize(20);
        pageList.setPage(1);
        return pageList;
    }

    /**
     * 员工各月份金币数--导出
     *
     * @param filter
     * @param response
     */
    @Override
    public void exportExcel(QueryFilter filter, HttpServletResponse response) throws Exception {
        List<CoinStatisticsListDTO> coinStatisticsListDTOS;
        Map<String, Object> params = filter.getParams();
        Object ids = params.get("ids");
        if (ids != null && ids != "") {//有选中
            List list = Arrays.asList(ids.toString().split(","));
            coinStatisticsListDTOS = this.rsunJbHiRewardMapper.queryExportExcel(list);
        } else {//未选中
            List<QueryField> queryFields=filter.getQuerys();
            for(QueryField queryField:queryFields){
                if(StringUtils.pathEquals(queryField.getProperty(),"projectForSearch")){
                    params.put("projectForSearch",queryField.getValue());
                }
                if(StringUtils.pathEquals(queryField.getProperty(),"jobForSearch")){
                    params.put("jobForSearch",queryField.getValue());
                }
            }
            coinStatisticsListDTOS = this.rsunJbHiRewardMapper.getCoinStatisticsList(params);
        }
        List<CoinStatisticsListForExportDTO> list1 = new ArrayList<>();
        for (CoinStatisticsListDTO coinStatisticsListDTO : coinStatisticsListDTOS) {
            CoinStatisticsListForExportDTO excel = new CoinStatisticsListForExportDTO();
            excel.setFullName(coinStatisticsListDTO.getFullName());
            excel.setUserNumber(coinStatisticsListDTO.getUserNumber());
            if (coinStatisticsListDTO.getJanuary() == null) {
                excel.setJanuary(0.00);
            } else {
                excel.setJanuary(coinStatisticsListDTO.getJanuary());
            }
            if (coinStatisticsListDTO.getFebruary() == null) {
                excel.setFebruary(0.00);
            } else {
                excel.setFebruary(coinStatisticsListDTO.getFebruary());
            }
            if (coinStatisticsListDTO.getMarch() == null) {
                excel.setMarch(0.00);
            } else {
                excel.setMarch(coinStatisticsListDTO.getMarch());
            }
            if (coinStatisticsListDTO.getApril() == null) {
                excel.setApril(0.00);
            } else {
                excel.setApril(coinStatisticsListDTO.getApril());
            }
            if (coinStatisticsListDTO.getMay() == null) {
                excel.setMay(0.00);
            } else {
                excel.setMay(coinStatisticsListDTO.getMay());
            }
            if (coinStatisticsListDTO.getJune() == null) {
                excel.setJune(0.00);
            } else {
                excel.setJune(coinStatisticsListDTO.getJune());
            }
            if (coinStatisticsListDTO.getJuly() == null) {
                excel.setJuly(0.00);
            } else {
                excel.setJuly(coinStatisticsListDTO.getJuly());
            }
            if (coinStatisticsListDTO.getAngust() == null) {
                excel.setAngust(0.00);
            } else {
                excel.setAngust(coinStatisticsListDTO.getAngust());
            }
            if (coinStatisticsListDTO.getSeptember() == null) {
                excel.setSeptember(0.00);
            } else {
                excel.setSeptember(coinStatisticsListDTO.getSeptember());
            }
            if (coinStatisticsListDTO.getOctober() == null) {
                excel.setOctober(0.00);
            } else {
                excel.setOctober(coinStatisticsListDTO.getOctober());
            }
            if (coinStatisticsListDTO.getNovember() == null) {
                excel.setNovember(0.00);
            } else {
                excel.setNovember(coinStatisticsListDTO.getNovember());
            }
            if (coinStatisticsListDTO.getDecember() == null) {
                excel.setDecember(0.00);
            } else {
                excel.setDecember(coinStatisticsListDTO.getDecember());
            }
            if (coinStatisticsListDTO.getTotalNum() == null) {
                excel.setTotalNum(0.00);
            } else {
                excel.setTotalNum(coinStatisticsListDTO.getTotalNum());
            }
            list1.add(excel);
        }
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        String[] headers = {"姓名", "工号", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月", "合计"};
        List<String> stringList = Arrays.asList(headers);
        Sheet sheet = ExportExcel.addSheet(workbook, null, stringList, null, "金币统计", 20);
        for (int i = 0; i < list1.size(); i++) {
            Row row = sheet.createRow(i + 1);
            List<String> rowValue = new ArrayList<>();
            rowValue.add(list1.get(i).getFullName());
            rowValue.add(list1.get(i).getUserNumber());
            rowValue.add(String.valueOf(list1.get(i).getJanuary()));
            rowValue.add(String.valueOf(list1.get(i).getFebruary()));
            rowValue.add(String.valueOf(list1.get(i).getMarch()));
            rowValue.add(String.valueOf(list1.get(i).getApril()));
            rowValue.add(String.valueOf(list1.get(i).getMay()));
            rowValue.add(String.valueOf(list1.get(i).getJune()));
            rowValue.add(String.valueOf(list1.get(i).getJuly()));
            rowValue.add(String.valueOf(list1.get(i).getAngust()));
            rowValue.add(String.valueOf(list1.get(i).getSeptember()));
            rowValue.add(String.valueOf(list1.get(i).getOctober()));
            rowValue.add(String.valueOf(list1.get(i).getNovember()));
            rowValue.add(String.valueOf(list1.get(i).getDecember()));
            rowValue.add(String.valueOf(list1.get(i).getTotalNum()));
            ExportExcel.setRowValue(row, rowValue);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("金币统计报表.xlsx").getBytes(), "iso-8859-1"));
        OutputStream os = response.getOutputStream();
        ExportExcel.exportExcel(workbook, os);
        response.flushBuffer();
    }


    /**
     * 工程抢单数据洞察
     * @param jsonobject
     * @return
     */
    public HashMap<String,Object> getEngineeringGrabOrdersDataInsight(EngineeringGrabOrdersDataInsightBO bo) {
        HashMap<String, Object> map = new HashMap<>();
        LinkedList<String> legendList = new LinkedList<String>();//项目名称
        List<String> xAxisList = new ArrayList<>();
        LinkedList<HashMap<String,Object>> seriesList = new LinkedList<HashMap<String,Object>>();//{name:String type:String  data:[]}
        //查询参数检查  项目ids  开始年月  截至年月
        if(null!=bo){
            //如果没有传入项目id 默认查询一个项目
//            if(CollectionUtils.isEmpty(bo.getProjectIdList())){
//                //获取可以抢单地块
//                String idString = portalFeignService.getPropertyByAlias("grabOrderList");
//                if(StringUtil.isNotEmpty(idString)) {
//                    String[] ids  = idString.split(",");
//                    for(String pid:ids){
//                        UcOrg org = ucOrgService.get(pid);
//                        if(null!=org){
//                            bo.setProjectIdList(Arrays.asList(org.getParentId()));
//                            break;//默认查第一个
//                        }
//                    }
//                }
//            }
            //如果没有传入项目id 查询所有
            if(CollectionUtils.isEmpty(bo.getProjectIdList())){
                //获取可以抢单地块
                String idString = portalFeignService.getPropertyByAlias("grabOrderList");
                if(StringUtil.isNotEmpty(idString)) {
                    String[] ids  = idString.split(",");
                    List<String> pIdArray = new ArrayList<String>();
                    for(String pid:ids){
                        UcOrg org = ucOrgService.get(pid);
                        if(null!=org){
                            pIdArray.add(org.getParentId());
                            if(null==bo.getQueryAll()||!"true".equals(bo.getQueryAll())){
                                break;//只查询一个
                            }
                        }
                    }
                    bo.setProjectIdList(pIdArray);
                }
            }

            String startDate = "";
            String endDate = "";
            if(StringUtil.isEmpty(bo.getStartYears()) || StringUtil.isEmpty(bo.getEndYears())){
                startDate = DateUtil.getPastNumMonth(6) + " 00:00:00";
                endDate = DateUtil.getMonthEnd() + " 23:59:59";
            }else{
                startDate = bo.getStartYears() + "-01 00:00:00";
                endDate = bo.getEndYears() + "-31 23:59:59";
            }
            xAxisList = DateUtil.getMonthBetweenDate(startDate.substring(0,7),endDate.substring(0,7));//x轴 所选日期范围  YYYY-MM
            //查询数据 -- 必须传入项目id
            if(!CollectionUtils.isEmpty(bo.getProjectIdList())) {
                List<HashMap<String, String>> pList = rsunJbHiRewardMapper.getEngineeringGrabOrdersDataInsight(startDate, endDate, bo.getProjectIdList());
                if (!CollectionUtils.isEmpty(pList)) {
                    List<HashMap<String, String>> dataList = pList.stream().filter(o -> StringUtil.isNotEmpty(o.get("u_project"))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dataList)) {
                        //按项目分组 --  遍历数据  -- 按年月取值 没有则赋值0 -- 封装数据
                        Map<String, List<HashMap<String, String>>> pMap = pList.stream().collect(Collectors.groupingBy(o -> o.get("u_project")));
                        for (Map.Entry<String, List<HashMap<String, String>>> entry : pMap.entrySet()) {
                            legendList.add(entry.getKey());
                            HashMap<String, Object> seriesMap = new HashMap<String, Object>();
                            seriesMap.put("name", entry.getKey());
                            seriesMap.put("type", "line");
                            List<HashMap<String, String>> projectList = entry.getValue();//数据
                            List<String> data = new ArrayList<String>();
                            for (String yyyyMM : xAxisList) {
                                //年月
                                Optional<HashMap<String, String>> optional = projectList.stream().filter(o -> o.get("years").equals(yyyyMM)).findAny();
                                if (optional.isPresent()) {
                                    data.add(optional.get().get("gcoin_val"));
                                } else {
                                    data.add("0");
                                }
                            }
                            seriesMap.put("data", data);
                            seriesList.add(seriesMap);
                        }
                    }
                }
            }
        }
        map.put("legendList",legendList);
        map.put("xAxisList",xAxisList);
        map.put("seriesList",seriesList);
        return map;
    }


    //获取用户抢单项目组织
    public List<UcOrg> getGrabOrderList(String userId) {
        //获取可以抢单地块
        String idString = portalFeignService.getPropertyByAlias("grabOrderList");
        if(StringUtil.isEmpty(idString)) {
            return new ArrayList<>();
        }
        //根据组织id获取组织信息
        QueryFilter orgQuery = QueryFilter.build();
        orgQuery.addFilter("id", idString, QueryOP.IN, FieldRelation.AND);
        orgQuery.addFilter("isDele", "0", QueryOP.EQUAL, FieldRelation.AND);
        List<UcOrg> returnList = ucOrgService.query(orgQuery).getRows();
        //根据组织查询父级组织
        List<UcOrg> set = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (UcOrg ucOrg : returnList) {
            String[] paths = ucOrg.getPath().split("\\.");
            for (int i = 0; i < paths.length; i++) {
                QueryFilter query = QueryFilter.build();
                query.addFilter("id", paths[i], QueryOP.EQUAL, FieldRelation.AND);
                query.addFilter("isDele", "1", QueryOP.NOT_EQUAL, FieldRelation.AND);
                List<UcOrg> voList = ucOrgService.query(query).getRows();
                for (UcOrg vo : voList) {
                    if (!ids.contains(vo.getId())) {
                        vo.setDisabled("2");
                        set.add(vo);
                        ids.add(vo.getId());
                    }
                }
            }
        }
        Set<String> xingZhengList=new HashSet<>();
        for(UcOrg item:set){
            if ("ORG_XingZheng".equals(item.getGrade())) {
                xingZhengList.add(item.getId());
            }
        }
        List<UcOrg> resultSet = new ArrayList<>();
        for(UcOrg item:set){
            Boolean isUnder=false;
            for(String xzId:xingZhengList){
                if(item.getPath().contains(xzId)){
                    isUnder=true;
                    break;
                }
            }
            if(!isUnder){
                resultSet.add(item);
            }
        }
        resultSet=resultSet.stream().sorted(Comparator.comparing(a -> null ==a.getOrderNo()?1000:a.getOrderNo())).collect(Collectors.toList());
        return resultSet;
    }

}
