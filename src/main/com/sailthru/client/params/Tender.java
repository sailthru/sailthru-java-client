package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JsonHandler;
import java.util.Map;

/**
 *
 * @author George Liao <gliao@sailthru.com>
 */
public class Tender {
    protected String key;
    protected String text;
    protected String amount;

    public Tender(String key, String text, Integer amount) {
        this.key = key;
        this.text = text;
        this.amount = amount.toString();
    }

    public Map<String, Object> toHashMap() {
        Gson gson = SailthruUtil.createGson();
        String json = gson.toJson(this);
        JsonHandler handler = new JsonHandler();
        return (Map<String, Object>)handler.parseResponse(json);
    }
}
