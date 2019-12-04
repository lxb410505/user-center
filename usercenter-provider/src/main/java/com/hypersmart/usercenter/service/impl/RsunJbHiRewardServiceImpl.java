package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryField;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.dto.CoinStatisticsListDTO;
import com.hypersmart.usercenter.dto.CoinStatisticsListForExportDTO;
import com.hypersmart.usercenter.mapper.RsunJbHiRewardMapper;
import com.hypersmart.usercenter.model.RsunJbHiReward;
import com.hypersmart.usercenter.service.RsunJbHiRewardService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

@Service("rsunJbHiRewardServiceImpl")
public class RsunJbHiRewardServiceImpl extends GenericService<String, RsunJbHiReward> implements RsunJbHiRewardService {


    @Autowired
    private RsunJbHiRewardMapper rsunJbHiRewardMapper;


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


}
