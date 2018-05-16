package com.pans.xplan.data.realm.entity;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author android01
 * @date 2018/5/16.
 * @time 上午10:48.
 */
public class PLAN extends RealmObject {
    @Ignore
    public static final String PLAN_DAY = "plan_day";
    @Ignore
    public static final String PLAN_WEEK = "plan_week";
    @Ignore
    public static final String PLAN_MONTH = "plan_month";
    @Ignore
    public static final String PLAN_YEAR = "plan_year";

    @Ignore
    public static final String REPEAT_EACH = "repeat_each";
    @Ignore
    public static final String REPEAT_ONCE = "repeat_once";

    @PrimaryKey
    private String primary_key = UUID.randomUUID().toString();

    private String plan_type;

    private String plan_title;

    private String plan_describe;

    private RealmList<Date> complete_date;

    private Date create_date;

    private boolean editable;

    private int expected_completion_number;

    private String repeat_type;

    public PLAN() {

    }

    public PLAN(String plan_type, String plan_title, String plan_describe, RealmList<Date> complete_date, Date create_date, boolean editable, int expected_completion_number, String repeat_type) {
        this.plan_type = plan_type;
        this.plan_title = plan_title;
        this.plan_describe = plan_describe;
        this.complete_date = complete_date;
        this.create_date = create_date;
        this.editable = editable;
        this.expected_completion_number = expected_completion_number;
        this.repeat_type = repeat_type;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public String getPlan_title() {
        return plan_title;
    }

    public void setPlan_title(String plan_title) {
        this.plan_title = plan_title;
    }

    public String getPlan_describe() {
        return plan_describe;
    }

    public void setPlan_describe(String plan_describe) {
        this.plan_describe = plan_describe;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public RealmList<Date> getComplete_date() {
        return complete_date;
    }

    public void setComplete_date(RealmList<Date> complete_date) {
        this.complete_date = complete_date;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getExpected_completion_number() {
        return expected_completion_number;
    }

    public void setExpected_completion_number(int expected_completion_number) {
        this.expected_completion_number = expected_completion_number;
    }

    public String getRepeat_type() {
        return repeat_type;
    }

    public void setRepeat_type(String repeat_type) {
        this.repeat_type = repeat_type;
    }
}
