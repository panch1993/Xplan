package com.pans.xplan.basic;

import java.util.Calendar;

/**
 * @author android01
 * @date 2018/5/19.
 * @time 下午8:08.
 */
public class Main {
    public static void main(String[] args) {
        //System.out.println(getTodayStartTime());

        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(1526844444444L);
        System.out.println(instance.get(Calendar.DAY_OF_WEEK));


    }


    public static long getTodayStartTime() {
        Calendar calender = Calendar.getInstance();
        calender.setFirstDayOfWeek(Calendar.MONDAY);
        calender.set(Calendar.DAY_OF_WEEK,calender.getFirstDayOfWeek());
        calender.set(Calendar.HOUR_OF_DAY,0);
        calender.set(Calendar.MINUTE,0);
        calender.set(Calendar.SECOND,0);
        calender.set(Calendar.MILLISECOND,0);
        return calender.getTimeInMillis();
    }
}
