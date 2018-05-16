package com.pans.xplan.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZapFive on 2018/1/3.
 * <p>
 * wuzhuang@mirahome.me
 */

public class SpUtil {
    private static SharedPreferences sp;
    private static volatile SpUtil spUtil;

    public static SpUtil get() {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil();
                }
            }
        }
        return spUtil;
    }

    public static SharedPreferences getSp() {
        return sp;
    }

    /**
     * SharedPreferences 存入Value
     *
     * @param key   key值
     * @param value 存入值
     */
    public static void put(String key, Object value) {
        SharedPreferences.Editor editor = sp.edit();
        if (TextUtils.isEmpty(key) || value == null) return;
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        editor.apply();
    }

    public static void put(String key, Set<String> value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, value).apply();
    }

    public static int get(String key, int def) {
        return sp.getInt(key, def);
    }

    public static String get(String key, String def) {
        return sp.getString(key, def);
    }

    public static boolean get(String key, boolean def) {
        return sp.getBoolean(key, def);
    }

    public static long get(String key, long def) {
        return sp.getLong(key, def);
    }

    public static float get(String key, float def) {
        return sp.getFloat(key, def);
    }

    public static Set<String> get(String key, Set<String> def) {
        return sp.getStringSet(key, def);
    }

    public static HashSet<String> get(String key) {
        return (HashSet<String>) sp.getStringSet(key, new HashSet<String>());
    }


    public void init(SharedPreferences sharedPreferences) {
        sp = sharedPreferences;
    }

}
