package com.hypersmart.usercenter.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出）
 * @author lanxinghua
 * @version 2018-10-03
 */
public class ExportExcel {

    private static Logger log = LoggerFactory.getLogger(ExportExcel.class);

    /**
     * 工作薄对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 构造函数
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    public ExportExcel(String title, List<String> headerList) {
        initialize(title, headerList);
    }


    /**
     * 初始化函数
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    private void initialize(String title, List<String> headerList) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet("Export");
        this.styles = createStyles(wb);
        // Create title
        if (StringUtils.isNotBlank(title)){
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
        }
        // Create header
        if (headerList == null){
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("header"));
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length==2){
                cell.setCellValue(ss[0]);
                Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            }else{
                cell.setCellValue(headerList.get(i));
            }
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i)*2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        //sheet.setColumnWidth(3,10000);
        log.debug("Initialize success.");
    }

    /**
     * 构造函数
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    //合并单元格
    public ExportExcel(String title, List<String> headerList, String resourceType) {
        initializeMerge(title, headerList,resourceType);
    }

    /**
     * 初始化函数
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    //合并单元格
    private void initializeMerge(String title, List<String> headerList,String resourceType) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet("Export");
        this.styles = createStyles(wb);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,3,4));
        sheet.addMergedRegion(new CellRangeAddress(0,0,7,8));
        sheet.addMergedRegion(new CellRangeAddress(0,0,11,12));

        // Create title
        if (StringUtils.isNotBlank(title)){
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
        }
        // Create header
        if (headerList == null){
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("header"));
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length==2){
                cell.setCellValue(ss[0]);
                Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            }else{
                cell.setCellValue(headerList.get(i));
            }
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i)*2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        //sheet.setColumnWidth(3,10000);
        log.debug("Initialize success.");
    }

    /**
     * 创建表格样式
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private  Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        sheet.autoSizeColumn((short)0); //自动调整列宽
        sheet.autoSizeColumn((short)1);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GOLD.getIndex());//背景色
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);//字体粗细
        headerFont.setColor(IndexedColors.RED.getIndex());//首行字体颜色
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

    /**
     * 添加一行
     * @return 行对象
     */
    public Row addRow(){
        return sheet.createRow(rownum++);
    }


    /**
     * 添加一个单元格
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val){
//        return this.addCell(row, column, val, 0);
        row.createCell(column).setCellValue(String.valueOf(val));
        return row.getCell(column);
    }


    /**
     * 添加一个单元格
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @param align 对齐方式（1：靠左；2：居中；3：靠右）
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val, int align){
        Cell cell = row.createCell(column);
        String cellFormatString = "@";
        try {
            if(val == null){
                cell.setCellValue("");
            }{
                if(val instanceof String) {
                    cell.setCellValue((String) val);
                }else if(val instanceof Integer) {
                    cell.setCellValue((Integer) val);
                    cellFormatString = "0";
                }else if(val instanceof Long) {
                    cell.setCellValue((Long) val);
                    cellFormatString = "0";
                }else if(val instanceof Double) {
                    cell.setCellValue((Double) val);
                    cellFormatString = "0.00";
                }else if(val instanceof Float) {
                    cell.setCellValue((Float) val);
                    cellFormatString = "0.00";
                }else if(val instanceof Date) {
                    cell.setCellValue((Date) val);
                    cellFormatString = "yyyy-MM-dd HH:mm";
                }
            }
            if (val != null){
                CellStyle style = styles.get("data_column_"+column);
                if (style == null){
                    style = wb.createCellStyle();
                    style.cloneStyleFrom(styles.get("data"+(align>=1&&align<=3?align:2)));
                    style.setDataFormat(wb.createDataFormat().getFormat(cellFormatString));
                    styles.put("data_column_" + column, style);
                }
                cell.setCellStyle(style);
            }
        } catch (Exception ex) {
            log.info("Set cell value ["+row.getRowNum()+","+column+"] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        return cell;
    }



    /**
     * 输出数据流
     * @param os 输出数据流
     */
    public ExportExcel write(OutputStream os) throws IOException{
        wb.write(os);
        return this;
    }

    /**
     * 输出到客户端
     * @param fileName 输出文件名
     */
    public ExportExcel write(HttpServletResponse response, String fileName) throws IOException{
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到文件
     */
    public ExportExcel writeFile(String name) throws Exception{
        FileOutputStream os = new FileOutputStream(name);
        this.write(os);
        return this;
    }

    /**
     * 清理临时文件
     */
    public ExportExcel dispose(){
        wb.dispose();
        return this;
    }

    /**
     * @param wb   工作簿
     * @param title 标题
     * @param headerList 表头
     * @param mergeColums  合并表头列数，列数与列数间用逗号隔开 如1,4表示表头的第2列到第5列合并，不穿代表不合并
     * @param sheetName   sheet页名称
     * @param  headHeight 表头行高度
     */
    public static Sheet addSheet(SXSSFWorkbook wb,String title, List<String> headerList, List<String> mergeColums, String sheetName, Integer headHeight){
        Sheet sheet = null;
        if (com.hypersmart.framework.utils.StringUtils.isNotRealEmpty(sheetName)){
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.createSheet("sheet1");
        }
        Map<String, CellStyle> styleMap = setDefaultStyle(wb, sheet);

        if (mergeColums != null && mergeColums.size() > 0){
            for (String col : mergeColums){
                String[] cols = col.split("\\,");
                sheet.addMergedRegion(new CellRangeAddress(0,0,Integer.valueOf(cols[0]), Integer.valueOf(cols[1])));
            }
        }

        // Create title
        if (StringUtils.isNotBlank(title)){
            Row titleRow = sheet.createRow((sheet.getLastRowNum() + 1));
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styleMap.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
        }
        // Create header
        if (headerList == null){
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(headHeight);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styleMap.get("header"));
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length==2){
                cell.setCellValue(ss[0]);
                Comment comment = sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            }else{
                cell.setCellValue(headerList.get(i));
            }
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i)*2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        return sheet;
    }


    public static Map<String, CellStyle> setDefaultStyle(SXSSFWorkbook wb, Sheet sheet){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        styles.put("title", style);
        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);
        sheet.autoSizeColumn((short)0); //自动调整列宽
        sheet.autoSizeColumn((short)1);
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GOLD.getIndex());//背景色
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);//字体粗细
        headerFont.setColor(IndexedColors.RED.getIndex());//首行字体颜色
        style.setFont(headerFont);
        style .setWrapText(true);
        styles.put("header", style);
        return styles;
    }

    public static Boolean exportExcel(SXSSFWorkbook wb, OutputStream os) throws IOException {
        wb.write(os);
        return true;
    }

    public static Row setRowValue(Row row, List<String> rowValue){
        for (int i = 0; i < rowValue.size(); i++){
            row.createCell(i).setCellValue(rowValue.get(i));
        }
        return row;
    }

    public static Row setRowValueSkipMerged(Row row, List<String> rowValue){
        for (int i = 0; i < rowValue.size(); i++){
            if(null != rowValue.get(i)){
                row.createCell(i).setCellValue(rowValue.get(i));
            }
        }
        return row;
    }
}
