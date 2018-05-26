package com.husen.mall2.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 处理时间的工具类
 * @author husen
 */
public class DateUtil {
    /**
     * 得到当月的最后一天
     * @return
     */
    public static int getCurrentMonthLastDay(int year, int month){
        if(month == 2 && year % 4 == 0){
            return 29;
        }else if(month == 2 && year % 4 != 0){
            return 28;
        }
        Calendar calendar = Calendar.getInstance();
        //把日期设为当月的第一天
        calendar.set(year, month + 1, 1);
        //日期回滚一天
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 将LocalDateTime转为Date
     * @param localDateTime
     * @return
     */
    public static Date LocalDateTimeForDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
