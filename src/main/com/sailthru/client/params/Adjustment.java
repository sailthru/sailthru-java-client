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
    protected String key;
    protected String amount;

    public Adjustment(String key, Integer amount) {
        this.key = key;
        this.amount = amount.toString();
    }
}
