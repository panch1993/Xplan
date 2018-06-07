package com.pans.xplan.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * @author android01
 * @date 2018/5/21.
 * @time 上午10:23.
 */
public class StringUtil {


    public static SpannableStringBuilder getSpannable(String firstStr,String connect, String secondStr, int nameSize, int idSize, int nameColor, int idColor) {
        String format = String.format("%s%s%s", firstStr, connect,secondStr);
        int i = format.lastIndexOf(connect);
        SpannableStringBuilder style = new SpannableStringBuilder(format);
        style.setSpan(new AbsoluteSizeSpan(nameSize), 0, i, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new AbsoluteSizeSpan(idSize), i, format.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //字号
        style.setSpan(new ForegroundColorSpan(nameColor), 0, i, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        style.setSpan(new ForegroundColorSpan(idColor), i, format.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        return style;
    }
}
