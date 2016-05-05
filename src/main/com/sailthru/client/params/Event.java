package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Event params
 * @author Ben Bartholomew <a href="bbartholomew@sailthru.com">bbartholomew@sailthru.com</a>
 */
public class Event extends AbstractApiParams implements ApiParams {
    protected String id;
    protected String key;
    protected Map<String, Object> vars;
    protected String event;
    protected String schedule_time;

    protected static final Type type = new TypeToken<Event>() {}.getType();
    
    public Event(String id) {
        this.id = id;
    }

    public Event() {
        // this will be used when new user_id is to be created
    }
    
    public Event setKey(String key) {
        this.key = key;
        return this;
    }

    public Event setEvent(String eventName) {
        this.event = eventName;
        return this;
    }

    public Event setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public Event setScheduleTime(String scheduleTime) {
        this.schedule_time = scheduleTime;
        return this;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.event;
    }

    @Override
    public Type getType() {
        return Event.type;
    }

}
