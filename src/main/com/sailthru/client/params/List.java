package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import com.sailthru.client.params.query.Query;

import java.lang.reflect.Type;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class List extends AbstractApiParams implements ApiParams {
    protected String list;
    protected Integer primary;
    protected ListType type;
    protected Query query;

    protected static final Type _type = new TypeToken<List>() {}.getType();
    
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
        return List._type;
    }

    public enum ListType {
        smart,
        normal
    }
}
