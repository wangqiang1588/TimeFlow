package com.mobibrw.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by longsky on 2017/9/3.
 */

public class TimeUtils {

    public static String getGMT(int year,int month,int day,int hour,int minute) {
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE,yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getDefault());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(year,month,day,hour,minute);
        return sdf.format(calendar.getTime());
    }
}
