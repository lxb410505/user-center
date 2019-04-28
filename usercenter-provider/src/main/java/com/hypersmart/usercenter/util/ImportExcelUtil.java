package com.hypersmart.usercenter.util;

import com.hypersmart.base.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportExcelUtil {

    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < 1; i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row != null) {
                    //遍历所有的列
//                    cell = row.getCell(0);
//                    Object str = String.valueOf(cell);
//                    if (str == null || str.equals("")) {
//                        break;
//                    }
                    List<Object> li = new ArrayList<Object>();
                    for (int y = 0; y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        li.add(cell == null ? "" : this.getCellValue(cell));
                    }
                    list.add(li);
                }
            }
        }
//        work.close();
        return list;
    }


    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcelPK(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < 1; i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //遍历当前sheet中的所有行
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row != null) {
                    List<Object> li = new ArrayList<Object>();
                    for (int y = 0; y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        li.add(cell == null ? "" : this.getCellValue(cell));
                    }
                    list.add(li);
                }
            }
        }
//        work.close();
        return list;
    }

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcelFromTwo(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < 1; i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = 0; y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell == null ? "" : this.getCellValue(cell));
                }
                list.add(li);

            }
        }
//        work.close();
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        System.out.print(fileType);
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (excel2007U.equals(fileType)) {
            wb = WorkbookFactory.create(inStr); /*new XSSFWorkbook(inStr); */ //2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                //去除前后空字符串
                value = removeEmptyStrings(value.toString());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 去除数据前后空字符串
     * @param textContent
     * @return
     */
    private Object removeEmptyStrings(String textContent) {
        if(StringUtil.isEmpty(textContent)) {
            return "";
        }
        textContent = textContent.trim();
        while (textContent.startsWith("　")) {//这里判断是不是全角空格
            textContent = textContent.substring(1, textContent.length()).trim();
        }
        while (textContent.endsWith("　")) {
            textContent = textContent.substring(0, textContent.length() - 1).trim();
        }
        return textContent;
    }

    /**
     * 判断EXCEL表头 从而判断excel模板是否正确
     * @param headList
     * @param origin
     * @param ignoreColum 不用比较的列
     * @return
     */
    public static Boolean checkHead(List<String> headList, List<String> origin, List<Integer> ignoreColum){
        if (headList != null && origin != null){
            if (headList.size() != origin.size()){
                return false;
            }
            for (int i = 0; i < headList.size(); i++){
                if (ignoreColum != null && ignoreColum.size() > 0){
                    if (ignoreColum.contains(i)){
                        continue;
                    }
                }
                if (!headList.get(i).replaceAll("\r|\n", "").replaceAll(" ", "").equals(origin.get(i))){
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
