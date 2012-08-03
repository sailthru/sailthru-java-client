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
    protected String amount;

    public Tender(String key, Integer amount) {
        this.key = key;
        this.amount = amount.toString();
    }
}
