package com.pans.xplan.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.pans.xplan.basic.AppContext;

/**
 * Created by panchenhuan on 16/12/14..
 */

public class ResourceUtil {
    //得到资源管理的类
    public static Resources getResources() {
        return AppContext.getInstance().getResources();
    }

    //在屏幕适配时候使用
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    public static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(AppContext.getInstance(),drawableId);
    }
    //得到颜色
    public static int getColor(int resId) {
        return ContextCompat.getColor(AppContext.getInstance(), resId);
    }

    //得到文字
    public static String getString(int strId) {
        return getResources().getString(strId);
    }

    public static String getString(int strId,String... arguments ) {
        return getResources().getString(strId,arguments);
    }

    public static String[] getStringArray(int arrayId) {
        return getResources().getStringArray(arrayId);
    }
}
