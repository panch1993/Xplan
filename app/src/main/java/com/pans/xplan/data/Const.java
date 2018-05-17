package com.pans.xplan.data;

/**
 * @author android01
 * @date 2018/5/16.
 * @time 下午3:10.
 */
public class Const {

    public static final String SP_NAME = "x_plan_sp";

    public static final String FRAGMENT_TITLE = "fragment_title";
    /**
     * Date common
     */
    public static final String Y_M_D_L = "%04d-%02d-%02d";//年月日 横线间隔
    public static final String Y_M_D_P = "%04d.%02d.%02d";//年月日 点间隔
    public static final String W_Y_M_D = "%04d.%02d.%02d - %04d.%02d.%02d";//一周首末对应日期
    public static final String Y_M_P = "%04d.%02d";//年月 点间隔
    public static final String H_M_C = "%02d:%02d";//时分冒号格式
    public static final String P_H_M_C = "%02d:%02d - %02d:%02d";//时间段 时分冒号格式
    public static final String Y_M_D_FORMAT = "yyyy-MM-dd";
    public static final String Y_M_D_H_M_S_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Plan type
     */
    public static final String TYPE_PLAN_DAY = "plan_day";
    public static final String TYPE_PLAN_WEEK = "plan_week";
    public static final String TYPE_PLAN_MONTH = "plan_month";
    public static final String TYPE_PLAN_YEAR = "plan_year";
    public static final String TYPE_REPEAT_EACH = "repeat_each";
    public static final String TYPE_REPEAT_ONCE = "repeat_once";
    public static final String TYPE_REPEAT_SPECIAL= "repeat_special";

    /**
     * Realm field
     */
    public static final String FIELD_PRIMARY_KEY = "primary_key";
    public static final String FIELD_PLAN_TYPE = "plan_type";
    public static final String FIELD_PLAN_TITLE = "plan_title";
    public static final String FIELD_PLAN_DESCRIBE = "plan_describe";
    public static final String FIELD_COMPLETE_DATE = "complete_date";
    public static final String FIELD_CREATE_DATE = "create_date";
    public static final String FIELD_EDITABLE = "editable";
    public static final String FIELD_EXPECTED_COMPLETION_NUMBER = "expected_completion_number";
    public static final String FIELD_REPEAT_TYPE = "repeat_type";
    public static final String FIELD_REPEAT_DAYS = "repeat_days";

    /**
     * Setting field
     */
    public static final String FINGER_ENABLE = "FINGER_ENABLE";
}
