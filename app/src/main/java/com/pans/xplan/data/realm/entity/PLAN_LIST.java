package com.pans.xplan.data.realm.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author android01
 * @date 2018/5/17.
 * @time 上午11:21.
 */
public class PLAN_LIST extends RealmObject {

    @PrimaryKey
    private String primary_key;

    private String plan_type;

    private String plan_title;

    private String plan_describe;

    private Date create_date;

    private boolean editable;

    private int expected_completion_number;

    private String repeat_type;

    //{0,1,2,3,4,5,6}
    private byte[] repeat_days;

    public PLAN_LIST() {
    }

    public PLAN_LIST(String plan_type, String plan_title, String plan_describe, Date create_date, boolean editable, int expected_completion_number, String repeat_type,byte[] repeat_days) {
        primary_key = UUID.randomUUID().toString();
        this.plan_type = plan_type;
        this.plan_title = plan_title;
        this.plan_describe = plan_describe;
        this.create_date = create_date;
        this.editable = editable;
        this.expected_completion_number = expected_completion_number;
        this.repeat_type = repeat_type;
        this.repeat_days = repeat_days;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
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

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }

    public byte[] getRepeat_days() {
        return repeat_days;
    }

    public void setRepeat_days(byte[] repeat_days) {
        this.repeat_days = repeat_days;
    }

    @Override
    public String toString() {
        return "PLAN_LIST{" +
                "primary_key='" + primary_key + '\'' +
                ", plan_type='" + plan_type + '\'' +
                ", plan_title='" + plan_title + '\'' +
                ", plan_describe='" + plan_describe + '\'' +
                ", create_date=" + create_date +
                ", editable=" + editable +
                ", expected_completion_number=" + expected_completion_number +
                ", repeat_type='" + repeat_type + '\'' +
                ", repeat_days=" + Arrays.toString(repeat_days) +
                '}';
    }
}
