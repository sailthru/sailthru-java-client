package com.sailthru.client.params;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JsonHandler;
import java.lang.reflect.Type;
import java.util.Map;

/**
 *
 * @author George Liao <gliao@sailthru.com>
 */
public class Adjustment {
    protected String title;
    protected String price;

    public Adjustment(String title, Integer price) {
        this.title = title;
        this.price = price.toString();
    }

    public Map<String, Object> toHashMap() {
        Type type = new TypeToken<Adjustment>() {}.getType();
        Gson gson = SailthruUtil.createGson();
        String json = gson.toJson(this, type);
        JsonHandler handler = new JsonHandler();
        return (Map<String, Object>)handler.parseResponse(json);
    }
}
