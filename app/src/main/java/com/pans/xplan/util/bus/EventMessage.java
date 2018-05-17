package com.pans.xplan.util.bus;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持多类型多参数传递消息实体类
 */
public class EventMessage {

    public enum TYPE {
        SHOW_DAY,
        SHOW_WEEK,
        SHOW_MONTH,
        SHOW_YEAR
    }

    private TYPE type;
    private Map<String, Object> params;


    public EventMessage(TYPE type) {
        this.type = type;
        this.params = null;
    }

    public EventMessage(TYPE type, String key, String value) {
        this.type = type;
        if (this.params == null) this.params = new HashMap<>();
        this.params.put(key, value);
    }

    public EventMessage(TYPE type, Map<String, Object> params) {
        this.type = type;
        this.params = params;
    }

    @NonNull
    public TYPE getType() {
        return type;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "type=" + type +
                ", params=" + params +
                '}';
    }
}
