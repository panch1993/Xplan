package com.pans.xplan.util;

import com.pans.xplan.R;
import com.pans.xplan.data.Const;

import java.util.Calendar;
import java.util.Date;

/**
 * @author android01
 * @date 2018/5/19.
 * @time 下午5:19.
 */
public class DateUtil {
    public static final long DAY_MILLI_SECOND = 24*60*60*1000L;

    private DateUtil() {

    }

    public static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(SpUtil.get(Const.WEEK_START,Calendar.MONDAY));
        return calendar;
    }
    public static String getDayOfWeek(long date, boolean sortStr) {
        return getDayOfWeekStr(getDayOfWeekIndex(date), sortStr);
    }

    public static int getDayOfWeekIndex(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(SpUtil.get(Const.WEEK_START,Calendar.MONDAY));
        calendar.setTime(new Date(date));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayOfWeekStr(int index,boolean sortStr) {
        switch (index) {
            case Calendar.MONDAY:
                return ResourceUtil.getString(sortStr ? R.string.mon : R.string.monday);
            case Calendar.TUESDAY:
                return ResourceUtil.getString(sortStr ? R.string.tue : R.string.tuesday);
            case Calendar.WEDNESDAY:
                return ResourceUtil.getString(sortStr ? R.string.wed : R.string.wednesday);
            case Calendar.THURSDAY:
                return ResourceUtil.getString(sortStr ? R.string.thur : R.string.thursday);
            case Calendar.FRIDAY:
                return ResourceUtil.getString(sortStr ? R.string.fri : R.string.friday);
            case Calendar.SATURDAY:
                return ResourceUtil.getString(sortStr ? R.string.sat : R.string.saturday);
            case Calendar.SUNDAY:
                return ResourceUtil.getString(sortStr ? R.string.sun : R.string.sunday);
            default:
                return "err";
        }
    }

    /**
     * 今日0点 毫秒值
     * @return
     */
    public static long getCurrentDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    public static long getTargetDayStartTime(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    public static long getTargetWeekStartTime(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        calendar.setFirstDayOfWeek(SpUtil.get(Const.WEEK_START,Calendar.MONDAY));
        calendar.set(Calendar.DAY_OF_WEEK,calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    public static long getTargetMonthStartTime(int year,int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }
    public static long getTargetYearStartTime(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 本周第一天 0点毫秒值
     * @return
     */
    public static long getCurrentWeekStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(SpUtil.get(Const.WEEK_START,Calendar.MONDAY));
        calendar.set(Calendar.DAY_OF_WEEK,calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }
    /**
     * 本月第一天 0点毫秒值
     * @return
     */
    public static long getCurrentMonthStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(SpUtil.get(Const.WEEK_START,Calendar.MONDAY));
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }
    /**
     * 本年第一天 0点毫秒值
     * @return
     */
    public static long getCurrentYearStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(SpUtil.get(Const.WEEK_START,Calendar.MONDAY));
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    public static long getTimeMilliSecond(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        return calendar.getTimeInMillis();
    }


}
