package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class Send extends AbstractApiParams implements ApiParams {
    
    public static final String PARAM_SEND_ID = "send_id";
    
    protected String template;
    protected String email;
    protected HashMap<String, Object> vars;
    protected String schedule_time;
    protected HashMap<String, Object> options;

    public Send() {
        this.options = new HashMap<String, Object>();
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
        this.options.put("replyTo", replyTo);
        return this;
    }

    public Send setIsTest() {
        this.options.put("test", 1);
        return this;
    }

    public Send setVars(HashMap<String, Object> vars) {
        this.vars = vars;
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

    public Type getType() {
        Type type = new TypeToken<Send>() {}.getType();
        return type;
    }

    public Send setBehalfEmail(String email) {
        this.options.put("behalf_email", email);
        return this;
    }

    public Send setOptions(HashMap<String, Object> options) {
        this.options = options;
        return this;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.send;
    }
}