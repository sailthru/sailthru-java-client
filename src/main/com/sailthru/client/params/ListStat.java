package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Date;

/**
 *
 * @author Prajwal Tuladhar
 */
public class ListStat extends Stats {
    protected String list;
    protected String date;

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

    public Type getType() {
        Type type = new TypeToken<BlastStat>() {}.getType();
        return type;
    }
}
