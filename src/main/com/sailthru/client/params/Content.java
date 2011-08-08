package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Content extends AbstractApiParams implements ApiParams {
    protected String title;
    protected String url;

    protected String date;
    protected String tags;
    protected HashMap<String, Object> vars;

    @Override
    public ApiAction getApiCall() {
        return ApiAction.blast;
    }

    public static enum ContentSepecialVar {PRICE, DESCRIPTION, BRAND};

    public Content setTitle(String title) {
        this.title = title;
        return this;
    }

    public Content setUrl(String url) {
        this.url = url;
        return this;
    }

    public Content setDate(Date date) {
        this.date = date.toString();
        return this;
    }

    public Content setDate(String date) {
        this.date = date;
        return this;
    }

    public Content setVars(HashMap<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public Content setSpecialVars(ContentSepecialVar var, String value) {
        switch (var) {
            case PRICE:
                this.vars.put("price", value);
                break;

            case DESCRIPTION:
                this.vars.put("description", value);
                break;

            case BRAND:
                this.vars.put("brand", value);
                break;
        }
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<Content>() {}.getType();
        return type;
    }
}