package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;

import java.lang.reflect.Type;
import java.util.Date;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class ListStat extends Stats {
    protected String list;
    protected String date;

    protected static final Type type = new TypeToken<ListStat>() {}.getType();

    public ListStat() {
        super(MODE_LIST);
    }

    public ListStat setList(String list)  {
        this.list = list;
        return this;
    }

    public ListStat setDate(Date date) {
        this.date = date.toString();
        return this;
    }

    public ListStat setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.list;
    }

    @Override
    public Type getType() {
        return ListStat.type;
    }
}
