package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.response.EmailResponse;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Email extends AbstractApiParams implements ApiParams {
    protected String email;
    protected Integer verified;
    protected String optout;
    
    protected HashMap<String, Integer> lists;
    protected HashMap<String, Integer> templates;

    protected String send;
    protected HashMap<String, Object> send_vars;
    protected HashMap<String, Object> vars;

    public Email() {
        this.vars = new HashMap<String, Object>();
        this.send_vars = new HashMap<String, Object>();
        this.lists = new HashMap<String, Integer>();
        this.templates = new HashMap<String, Integer>();
    }

    public Email setEmail(String email) {
        this.email = email;
        return this;
    }

    public Email setVerified(Integer verified) {
        this.verified = verified;
        return this;
    }

    public Email setOptout(String optout) {
        this.optout = optout;
        return this;
    }

    public Email setLists(ArrayList<String> lists) {
        for (String list : lists) {
            this.lists.put(list, 1);
        }
        return this;
    }

    public Email setTemplates(ArrayList<String> templates) {
        for (String template : templates) {
            this.templates.put(template, 1);
        }
        return this;
    }

    public Email setSend(String template) {
        this.send = template;
        return this;
    }

    public Email setSendVars(HashMap<String, Object> sendVars) {
        this.send_vars = sendVars;
        return this;
    }

    @SuppressWarnings("unchecked")
    public Email setVars(HashMap<String, Object> vars) {
        this.vars = vars;
        return this;
    }
    

    public Email setTextOnly() {
        this.vars.put("text_only", 1);
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<Email>() {}.getType();
        return type;
    }
}