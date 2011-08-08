package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.handler.JSONHandler;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public abstract class AbstractApiParams {
    public HashMap<String, Object> toHashMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        JSONHandler handler = new JSONHandler();
        return (HashMap<String, Object>)handler.parseResponse(json);
    }
    
}
