package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class Email extends AbstractApiParams implements ApiParams {
    protected String email;
    protected Integer verified;
    protected String optout;
    
    protected Map<String, Integer> lists;
    protected Map<String, Integer> templates;

    protected String send;
    protected Map<String, Object> send_vars;
    protected Map<String, Object> vars;

    protected static final Type type = new TypeToken<Email>() {}.getType();

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

    public Email setLists(java.util.List<String> lists) {
        this.lists.clear();
        for (String list : lists) {
            this.lists.put(list, 1);
        }
        return this;
    }

    public Email setLists(Map<String, Integer> lists) {
        this.lists.clear();
        for (Map.Entry<String, Integer> entry : lists.entrySet()) {
            String list = entry.getKey();
            int value = entry.getValue() != 0 ? 1 : 0;
            this.lists.put(list, value);
        }
        return this;
    }

    public Email setTemplates(java.util.List<String> templates) {
        for (String template : templates) {
            this.templates.put(template, 1);
        }
        return this;
    }

    public Email setTemplates(Map<String, Integer> templates) {
        this.templates = templates;
        return this;
    }

    public Email setSend(String template) {
        this.send = template;
        return this;
    }

    public Email setSendVars(Map<String, Object> sendVars) {
        this.send_vars = sendVars;
        return this;
    }

    @SuppressWarnings("unchecked")
    public Email setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }
    

    public Email setTextOnly() {
        this.vars.put("text_only", 1);
        return this;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.email;
    }

    @Override
    public Type getType() {
        return Email.type;
    }

}
