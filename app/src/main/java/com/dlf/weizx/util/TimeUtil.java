package com.dlf.weizx.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    /**
     * 格式化日期,将calendar转成8位日期:20200202
     * @param calendar
     * @return
     */
    public static String parseTime(Calendar calendar){
        //格式化日期
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date time = calendar.getTime();
        return format.format(time);
    }
}
