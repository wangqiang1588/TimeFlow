package com.mobibrw.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by longsky on 2017/9/3.
 */

public class TimeUtils {
    private final static String TIME_STAMP_FORMAT = "yyyyMMddHHmmssSSS";
    private final static String GMT_TIME_FORMAT = "EEE,yyyy-MM-dd HH:mm:ss";

    public static String getGMT(int year,int month,int day,int hour,int minute) {
        final SimpleDateFormat sdf = new SimpleDateFormat(GMT_TIME_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(year,month,day,hour,minute);
        return sdf.format(calendar.getTime());
    }

    public static String generateTimeStamp() {
        final String timestamp = new SimpleDateFormat(TIME_STAMP_FORMAT).format(new Date());
        return timestamp;
    }
}
