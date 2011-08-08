package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Alert extends AbstractApiParams implements ApiParams {
    protected String email;

    public static enum TypeMode {realtime, daily, weekly};
    protected String type;
    protected String template;
    protected String when;

    protected Map<String, List<String>> match;
    protected Map<String, Number> min;
    protected Map<String, Number> max;
    protected List<String> tags;

    public Alert setEmail(String email) {
        this.email = email;
        return this;
    }

    public Alert setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Alert setMatchQuery(Map<String, List<String>> match) {
        this.match = match;
        return this;
    }

    public Alert setMinQuery(Map<String, Number> min) {
        this.min = min;
        return this;
    }

    public Alert setMaxQuery(Map<String, Number> max) {
        this.max = max;
        return this;
    }

    public Alert setTagsQuery(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public Alert setType(TypeMode mode) {
        this.type = mode.toString();
        return this;
    }

    public Alert setWhen(String when) {
        this.when = when;
        return this;
    }

    public Type getType() {
        java.lang.reflect.Type _type = new TypeToken<Alert>() {}.getType();
        return _type;
    }
    
    @Override
    public ApiAction getApiCall() {
        return ApiAction.alert;
    }

}