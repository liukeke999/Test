package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class DateUtil {

    public static void main(String[] args) {
        String startDate = "2018-04-01";
        String sEndDate = "2018-03-01";
        int i = differentDaysByString2(startDate, sEndDate);
        System.out.println(i);


    }

    /**
     * 计算两个日期之间的相差天数
     * @param startDate yyyy-MM-dd格式 开始日期
     * @param endDate  yyyy-MM-dd格式  结束日期
     * @return 返回相差的天数
     */
    public static int differentDaysByString2(String startDate, String endDate) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(startDate,df);
        LocalDate enTime = LocalDate.parse(endDate,df);
        int daysNum = (int)(enTime.toEpochDay() - beginDate.toEpochDay());
        return daysNum;
    }

    /**
     * 计算两个日期之间的相差天数
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @return 返回相差的天数
     */
    public static int differentDaysByString(String startDate, String endDate) {
        int days = 0;
        try {
            days = (int) ((Objects.requireNonNull(parseDate(endDate)).getTime() - Objects.requireNonNull(parseDate(startDate)).getTime()) / (1000 * 3600 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 日期格式转换
     * @param date 日期
     * @return 日期格式
     */
    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        sdf.setTimeZone(tz);
        if (date.isEmpty()) {
            return null;
        }
        return (Date)sdf.parse(date);
    }


    /**
     * 将月粒度日期格式yyyy-MM-dd格式向前推X个月或向后推X个月的方法
     * @param date 传入日期
     * @param month 要往后推的月数
     * @return
     */
    public static String getBeforeXMonthDate(String date,int month){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //将传过来的日期设置给calendar
            calendar.setTime(sdf.parse(date));
            //System.out.println("当前日期="+sdf.format(calendar.getTime()));
            //将传过来的月份减去X个月或者加上X个月
            calendar.add(Calendar.MONTH, month);
            //System.out.println("向前推12月之前的日期="+sdf.format(calendar.getTime()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return sdf1.format(calendar.getTime());
    }


}
