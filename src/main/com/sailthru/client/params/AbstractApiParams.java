package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.handler.JsonHandler;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public abstract class AbstractApiParams {
    public HashMap<String, Object> toHashMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        JsonHandler handler = new JsonHandler();
        return (HashMap<String, Object>)handler.parseResponse(json);
    }
    
}
