package com.sailthru.client.params;

import java.util.ArrayList;

/**
 *
 * @author Prajwal Tuladhar
 */
public class List extends ApiParams {
    protected String list;
    protected String emails;
    protected Integer primary;

    public List setList(String list) {
        this.list = list;
        return this;
    }

    public List setEmails(ArrayList<String> emails) {
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
}
