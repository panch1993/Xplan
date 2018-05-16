package com.pans.xplan.util;

import android.content.Context;
import android.os.AsyncTask;
import android.view.WindowManager;

import com.pans.xplan.basic.AppContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xiangkui on 16/3/18..
 */
public class MethodUtil {

    /**
     * 根据手机的分辨率从dp 的单位 转成为px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dp2px(float dpValue) {
        final float scale = AppContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素) 的单位 转成为dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机分辨率，从sp转换为px
     * @param spValue   字号大小
     */
    public static int sp2px(float spValue) {
        final float fontScale = AppContext.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void cancelAsyncTask(AsyncTask<?, ?, ?> a) {
        if (a != null && a.getStatus() == AsyncTask.Status.RUNNING) {
            a.cancel(true);
        }
    }

    public static int getWindowWidth(Context activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }

    public static int getWindowHeigth(Context activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return wm.getDefaultDisplay().getHeight();
    }


    public static String getDisplayDateTime(long originalDate){
        Calendar today = Calendar.getInstance();
        int ty = today.get(Calendar.YEAR);
        int td = today.get(Calendar.DAY_OF_YEAR);

        Calendar original = Calendar.getInstance();
        original.setTimeInMillis(originalDate);
        int oy = original.get(Calendar.YEAR);
        int od = original.get(Calendar.DAY_OF_YEAR);
        String returnMsg = "";
        SimpleDateFormat dateFormat;
        if(ty == oy){
            switch (td - od) {
                case 0:
                    dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
                    returnMsg = dateFormat.format(new Date(originalDate));
                    break;
                case 1:
                    dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
                    returnMsg = "昨天 "+dateFormat.format(new Date(originalDate));
                    break;
                case 2:
                    dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
                    returnMsg = "前天 "+dateFormat.format(new Date(originalDate));
                    break;
                case 3:
                    dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
                    returnMsg = convertNumToWeek(original.get(Calendar.DAY_OF_WEEK)) + " "+dateFormat.format(new Date(originalDate));
                    break;

                default:
                    dateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
                    returnMsg = dateFormat.format(new Date(originalDate));
                    break;
            }
        }else{
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            returnMsg = dateFormat.format(new Date(originalDate));
        }
        if("00:00".equals(returnMsg)){
            returnMsg = "今天";
        }
        return returnMsg;
    }

    public static String convertNumToWeek(int num){
        String str;
        switch (num) {
            case 1:
                str = "星期一";
                break;
            case 2:
                str = "星期二";
                break;
            case 3:
                str = "星期三";
                break;
            case 4:
                str = "星期四";
                break;
            case 5:
                str = "星期五";
                break;
            case 6:
                str = "星期六";
                break;
            case 7:
                str = "星期日";
                break;

            default:
                str = "";
                break;
        }
        return str;
    }
}
