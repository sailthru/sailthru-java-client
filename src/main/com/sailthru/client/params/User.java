package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * User params
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class User extends AbstractApiParams implements ApiParams {
    public static final String PARAM_TEMPLATE = "user";
    
    protected String id;
    protected String key;
    protected Map<String, Object> fields;
    protected Map<String, String> keys;
    protected String keysconflict;
    protected Map<String, Object> vars;
    protected Map<String, Integer> lists;
    protected String optout_email;
    protected Map<String, Object> login;

    protected static final Type _type = new TypeToken<User>() {}.getType();
    
    public User(String id) {
        this.id = id;
    }

    public User() {
        // this will be used when new user_id is to be created
    }
    
    public User setKey(String key) {
        this.key = key;
        return this;
    }
    
    public User setFields(Map<String, Object> fields) {
        this.fields = fields;
        return this;
    }
    
    public User setKeys(Map<String, String> keys) {
        this.keys = keys;
        return this;
    }

    public User setKeysConflict(String keysConflict) {
        this.keysconflict = keysConflict;
        return this;
    }
    
    public User setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }
    
    public User setLists(Map<String, Integer> lists) {
        this.lists = lists;
        return this;
    }
    
    public User setOptoutEmail(String optoutEmail) {
        this.optout_email = optoutEmail;
        return this;
    }
    
    public User setLogin(Map<String, Object> login) {
        this.login = login;
        return this;
    }

    @Override
    public Type getType() {
        return User._type;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.user;
    }

}
