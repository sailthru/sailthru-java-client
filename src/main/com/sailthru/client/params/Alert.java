package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;

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

    protected HashMap<String, ArrayList<String>> match;
    protected HashMap<String, Number> min;
    protected HashMap<String, Number> max;
    protected ArrayList<String> tags;

    public Alert setEmail(String email) {
        this.email = email;
        return this;
    }

    public Alert setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Alert setMatchQuery(HashMap<String, ArrayList<String>> match) {
        this.match = match;
        return this;
    }

    public Alert setMinQuery(HashMap<String, Number> min) {
        this.min = min;
        return this;
    }

    public Alert setMaxQuery(HashMap<String, Number> max) {
        this.max = max;
        return this;
    }

    public Alert setTagsQuery(ArrayList<String> tags) {
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

    public java.lang.reflect.Type getType() {
        java.lang.reflect.Type _type = new TypeToken<Alert>() {}.getType();
        return _type;
    }
}