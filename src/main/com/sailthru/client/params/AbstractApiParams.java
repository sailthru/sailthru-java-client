package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JsonHandler;

import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public abstract class AbstractApiParams {
    public Map<String, Object> toHashMap() {
        Gson gson = SailthruUtil.createGson();
        String json = gson.toJson(this);
        JsonHandler handler = new JsonHandler();
        return (Map<String, Object>)handler.parseResponse(json);
    }
    
}
