package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import com.sailthru.client.params.query.Query;

import java.lang.reflect.Type;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class List extends AbstractApiParams implements ApiParams {
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

    public ApiAction getApiCall() {
        return ApiAction.list;
    }

    public Type getType() {
        return new TypeToken<List>() {}.getType();
    }

    public enum ListType {
        smart,
        normal
    }
}
