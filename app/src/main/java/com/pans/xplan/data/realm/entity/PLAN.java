package com.pans.xplan.data.realm.entity;

import io.realm.RealmObject;

/**
 * @author android01
 * @date 2018/5/16.
 * @time 上午10:48.
 */
public class PLAN extends RealmObject {

    private String primary_key;
    private String plan_type;

    private int complete_times;

    private long create_time;

    private String plan_title;

    private String plan_describe;

    private int expected_completion_number;

    public PLAN() {
    }

    public PLAN(String plan_type,String primary_key, int complete_times, long create_time, String plan_title, String plan_describe, int expected_completion_number) {
        this.plan_type = plan_type;
        this.primary_key = primary_key;
        this.complete_times = complete_times;
        this.create_time = create_time;
        this.plan_title = plan_title;
        this.plan_describe = plan_describe;
        this.expected_completion_number = expected_completion_number;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }

    public int getComplete_times() {
        return complete_times;
    }

    public void setComplete_times(int complete_times) {
        this.complete_times = complete_times;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
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

    public int getExpected_completion_number() {
        return expected_completion_number;
    }

    public void setExpected_completion_number(int expected_completion_number) {
        this.expected_completion_number = expected_completion_number;
    }
}
