package com.sailthru.client.params;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Send extends ApiParams {
    protected String template;
    protected String email;
    protected HashMap<String, Object> vars;
    protected Date schedule_time;
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
        this.schedule_time = scheduleTime;
        return this;
    }
}