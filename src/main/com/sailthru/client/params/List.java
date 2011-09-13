package com.sailthru.client.params;

import com.sailthru.client.ApiAction;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class List extends AbstractApiParams implements ApiParams {
    protected String list;
    protected String emails;
    protected Integer primary;

    public List setList(String list) {
        this.list = list;
        return this;
    }

    public List setEmails(java.util.List<String> emails) {
        this.emails = "";
        for (String email : emails) {
            this.emails += email + ",";
        }
        int lastIndex = this.emails.length() - 1;
        char last =this.emails.charAt(lastIndex);
        if (last == ',') {
            this.emails = this.emails.substring(0, lastIndex);
        }
        return this;
    }

    public List setPrimary() {
        this.primary = 1;
        return this;
    }

    public ApiAction getApiCall() {
        return ApiAction.list;
    }

    public Type getType() {
        return new TypeToken<List>() {}.getType();
    }
}
