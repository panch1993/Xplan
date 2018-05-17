package com.pans.xplan.data.realm.entity;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author android01
 * @date 2018/5/16.
 * @time 上午10:48.
 */
public class PLAN extends RealmObject {
    @PrimaryKey
    private String primary_key;

    private String plan_type;

    private String plan_title;

    private String plan_describe;

    private RealmList<Date> complete_date;

    private Date create_date;

    private boolean editable;

    private int expected_completion_number;

    public PLAN() {

    }

    public PLAN(String plan_type, String plan_title, String plan_describe, RealmList<Date> complete_date, Date create_date, boolean editable, int expected_completion_number) {
        primary_key = UUID.randomUUID().toString();
        this.plan_type = plan_type;
        this.plan_title = plan_title;
        this.plan_describe = plan_describe;
        this.complete_date = complete_date;
        this.create_date = create_date;
        this.editable = editable;
        this.expected_completion_number = expected_completion_number;
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
}
