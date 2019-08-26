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

public class ExportExcelUtils {

    private static Logger log = LoggerFactory.getLogger(ExportExcelUtils.class);

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
    private int rowNum;


    public ExportExcelUtils() {
        this.wb = new SXSSFWorkbook(500);
    }


    /**
     * 添加一行
     * @return 行对象
     */
    public Row addRow(){
        return sheet.createRow(rowNum++);
    }


    /**
     * 添加一个单元格
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val){
        row.createCell(column).setCellValue(String.valueOf(val));
        return row.getCell(column);
    }
    public void merge(int firstRow, int lastRow, int firstCol, int lastCol ){
        sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstCol,lastCol));
    }




    /**
     * 输出数据流
     * @param os 输出数据流
     */
    public ExportExcelUtils write(OutputStream os) throws IOException{
        wb.write(os);
        return this;
    }

    /**
     * 输出到客户端
     * @param fileName 输出文件名
     */
    public ExportExcelUtils write(HttpServletResponse response, String fileName) throws IOException{
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到文件
     */
    public ExportExcelUtils writeFile(String name) throws Exception{
        FileOutputStream os = new FileOutputStream(name);
        this.write(os);
        return this;
    }

    /**
     * 清理临时文件
     */
    public ExportExcelUtils dispose(){
        wb.dispose();
        return this;
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
    public int getRowNum(){
        return this.rowNum;
    }

    public SXSSFWorkbook getWb() {
        return wb;
    }

    public Sheet addSheet(List<String> headerList, String sheetName){
        this.rowNum=0;
        if (com.hypersmart.framework.utils.StringUtils.isNotRealEmpty(sheetName)){
            this.sheet = this.wb.createSheet(sheetName);
        } else {
            this.sheet = this.wb.createSheet("sheet1");
        }
        Map<String, CellStyle> styleMap = setDefaultStyle(wb, sheet);

        // Create header
        if (headerList == null){
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rowNum++);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styleMap.get("header"));
            cell.setCellValue(headerList.get(i));
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
}
