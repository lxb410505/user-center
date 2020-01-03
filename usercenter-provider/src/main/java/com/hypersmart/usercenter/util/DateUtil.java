package com.hypersmart.usercenter.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    /**
     * 获取今天
     * @return String
     * */
    public static String getToday(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 获取昨天
     * @return String
     */
    public static String getYestoday(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 获取本月开始日期
     * @return String
     */
    public static String getMonthStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 获取本月最后一天
     * @return String
     */
    public static String getMonthEnd(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取上月开始日期
     * @return
     */
    public static String getLastMonthStart(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取上月最后一天
     * @return
     */
    public static String getLastMonthEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取以当前时间为准的上一个月时间
     * @return
     */
    public static String getLastMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取本周的第一天
     * @return String
     */
    public static String getWeekStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    /**
     * 获取本周的最后一天
     * @return String
     */
    public static String getWeekEnd(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    /**
     * 获取本年的第一天
     * @return String
     */
    public static String getYearStart(){
        return new SimpleDateFormat("yyyy").format(new Date())+"-01-01";
    }

    /**
     * 获取本年的最后一天
     * @return String
     */
    public static String getYearEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取前/后半年的开始时间
     * @return
     */
    public static String getHalfYearStartTime(){
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String halfYearStartTime = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 0);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            halfYearStartTime = shortSdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return halfYearStartTime;
    }

    /**
     * 获取前/后半年的结束时间
     * @return
     */
    public static String getHalfYearEndTime(){
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String halfYearEndTime = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            halfYearEndTime = shortSdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return halfYearEndTime;
    }
    /**
     * 获取过去几天
     * @param num 过去几天
     * @return
     */
    public static String getPastNumDay(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - num);
        return format.format(c.getTime());
    }

    /**
     * 获取过去几个月
     * @param num 几个月
     * @return
     */
    public static String getPastNumMonth(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, - num);
        return format.format(c.getTime());
    }

    /**
     * 获取过去几年
     * @param num 几年
     * @return
     */
    public static String getPastNumYear(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, - num);
        return format.format(c.getTime());
    }

    /**
     * 获取两个日期之间所有的月份集合
     * @param startTime
     * @param endTime
     * @return：YYYY-MM
     */
    public static List<String> getMonthBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM").parse(startTime);
            Date endDate = new SimpleDateFormat("yyyy-MM").parse(endTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            // 获取开始年份和开始月份
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH);
            // 获取结束年份和结束月份
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            //
            for (int i = startYear; i <= endYear; i++) {
                String date = "";
                if (startYear == endYear) {
                    for (int j = startMonth; j <= endMonth; j++) {
                        if (j < 9) {
                            date = i + "-0" + (j + 1);
                        } else {
                            date = i + "-" + (j + 1);
                        }
                        list.add(date);
                    }

                } else {
                    if (i == startYear) {
                        for (int j = startMonth; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else if (i == endYear) {
                        for (int j = 0; j <= endMonth; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else {
                        for (int j = 0; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
