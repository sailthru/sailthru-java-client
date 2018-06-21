package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class Send extends AbstractApiParams implements ApiParams {
    
    public static final String PARAM_SEND_ID = "send_id";

    protected String send_id; 
    protected String template;
    protected String email;
    protected Map<String, Object> vars;
    protected Object schedule_time;
    protected Map<String, Object> options;
    protected Map<String, Object> limit;

    public Send() {
        this.options = new LinkedHashMap<String, Object>();
    }

    public Send setSendId(String sendId) {
        this.send_id = sendId;
        return this;
    }

    public Send setEmail(String email) {
        this.email = email;
        return this;
    }

    public Send setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Send setReplyTo(String replyTo) {
        this.options.put("replyto", replyTo);
        return this;
    }

    public Send setIsTest() {
        this.options.put("test", 1);
        return this;
    }

    public Send setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public Send setLimit(String name) {
        this.limit = new HashMap<String, Object>();
        this.limit.put("name",name);
        return this;
    }

    public Send setLimit(String name, String within_time) {
        this.limit = new HashMap<String, Object>();
        this.limit.put("name",name);
        this.limit.put("within_time",within_time);
        return this;
    }

    public Send setLimit(String name, String within_time, String conflict) {
        this.limit = new HashMap<String, Object>();
        this.limit.put("name",name);
        this.limit.put("within_time",within_time);
        this.limit.put("conflict",conflict);
        return this;
    }
    public Send setLimit(Map<String, Object> limit) {
        this.limit = limit;
        return this;
    }

    public Send setScheduleTime(Date scheduleTime) {
        this.schedule_time = scheduleTime.toString();
        return this;
    }

    public Send setScheduleTime(String scheduleTime) {
        this.schedule_time = scheduleTime;
        return this;
    }

    public Send setScheduleTime(Map<String, Object> scheduleTime) {
        this.schedule_time = (Object) scheduleTime;
        return this;
    }

    public Send setScheduleTime(Object startTime, Object endTime, String method) {
        Map<String, Object> scheduleTime = new HashMap<String, Object>();
        if (startTime instanceof String || startTime instanceof Number) {
            scheduleTime.put("start_time", startTime);
        }
        if (endTime instanceof String || endTime instanceof Number) {
            scheduleTime.put("end_time", endTime);
        }
        scheduleTime.put("method", method);
        this.schedule_time = (Object) scheduleTime;
        return this;
    }

    public Send setScheduleTime(Object startTime, Object endTime) {
        Map<String, Object> scheduleTime = new LinkedHashMap<String, Object>();
        if (startTime instanceof String || startTime instanceof Number) {
            scheduleTime.put("start_time", startTime);
        }
        if (endTime instanceof String || endTime instanceof Number) {
            scheduleTime.put("end_time", endTime);
        }
        this.schedule_time = (Object) scheduleTime;
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<Send>() {}.getType();
        return type;
    }

    public Send setBehalfEmail(String email) {
        this.options.put("behalf_email", email);
        return this;
    }

    public Send setOptions(Map<String, Object> options) {
        this.options = options;
        return this;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.send;
    }
}
