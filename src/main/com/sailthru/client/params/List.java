package com.sailthru.client.params;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import com.sailthru.client.params.query.Query;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class List extends AbstractApiParams implements ApiParams { 
    public static final String PARAM_LIST_ID = "list_id";

    protected String list;
    protected Integer primary;
    protected ListType type;
    protected Query query;

    public List setQuery(Query query) {
        this.query = query;
        return this;
    }

    public List setList(String list) {
        this.list = list;
        return this;
    }

    public List setPrimary() {
        this.primary = 1;
        return this;
    }

    public List setListType(ListType listType) {
        this.type = listType;
        return this;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.list;
    }

    @Override
    public Type getType() {
        return new TypeToken<List>() {}.getType();
    }

    public enum ListType {
        smart,
        normal
    }
}
