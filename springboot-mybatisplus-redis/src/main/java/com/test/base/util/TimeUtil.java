package com.test.base.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TimeUtil <br>
 *
 * @author hao.wang <br>
 * @version 1.0 <br>
 * @createDate 2020/6/23 16:55 <br>
 */
@Slf4j
public class TimeUtil {

    private static final String PATTERN_YYYY_MM = "yyyy-MM";

    /**
    * getNextMonth 获取当前日期下一个月 <br>
    *
    * @param date <br>
    * @return java.lang.String <br>
    * @author hao.wang <br>
    * @createDate 2020/6/23 17:02 <br>
    **/
    public static String getNextMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_YYYY_MM);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static void main(String[] args) {

    }
}
