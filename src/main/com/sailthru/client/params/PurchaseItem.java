package com.sailthru.client.params;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JsonHandler;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class PurchaseItem {
    protected String qty;
    protected String title;
    protected String price;
    protected String id;
    protected String url;
    protected String tags;
    protected java.util.List<Adjustment> adjustments;
    protected Map<String, Object> vars;

    public PurchaseItem(Integer qty, String title, Integer price, String id, String url) {
        this.qty = qty.toString();
        this.title = title;
        this.price = price.toString();
        this.id = id;
        this.url = url;
    }

    public PurchaseItem setTags(List<String> tags) {
        this.tags = SailthruUtil.arrayListToCSV(tags);
        return this;
    }

    public PurchaseItem setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public PurchaseItem setAdjustments(java.util.List<Adjustment> adjustments) {
        this.adjustments = adjustments;
        return this;
    }

    public Map<String, Object> toHashMap() {
        Type type = new TypeToken<PurchaseItem>() {}.getType();
        Gson gson = SailthruUtil.createGson();
        String json = gson.toJson(this, type);
        JsonHandler handler = new JsonHandler();
        return (Map<String, Object>)handler.parseResponse(json);
    }
    
}
