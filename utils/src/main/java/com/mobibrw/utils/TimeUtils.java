package com.mobibrw.utils;

import android.text.format.DateUtils;
import android.text.format.Time;

import java.text.ParseException;
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

    public static Date timeStampFmtToDate(final String timestamp) throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_STAMP_FORMAT);
        final Date date = simpleDateFormat.parse(timestamp);
        return date;
    }

    public static boolean isToday(Date date) {
        return DateUtils.isToday(date.getTime());
    }

    public static boolean isThisYear(Date date) {
        final Time time = new Time();
        time.set(System.currentTimeMillis());
        final int thisYear = time.year;
        time.set(date.getTime());
        final int thenYear = time.year;
        return thisYear == thenYear;
    }

    public static int getDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHourOfDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getSecond(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

    public static int getMinute(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static int getMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getYear(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
}
