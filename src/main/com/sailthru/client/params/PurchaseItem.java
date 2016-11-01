package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JsonHandler;

import java.util.List;
import java.util.Map;

/**
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class PurchaseItem {
    protected String qty;
    protected String title;
    protected String price;
    protected String id;
    protected String image;
    protected List tags;
    protected String url;
    protected Map<String, Object> vars;

    public PurchaseItem(Integer qty, String title, Integer price, String id, String url, String image) {
        this.qty = qty.toString();
        this.title = title;
        this.price = price.toString();
        this.id = id;
        this.url = url;
        this.image = image;
    }

    public PurchaseItem setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PurchaseItem setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public Map<String, Object> toHashMap() {
        Gson gson = SailthruUtil.createGson();
        String json = gson.toJson(this);
        JsonHandler handler = new JsonHandler();
        return (Map<String, Object>) handler.parseResponse(json);
    }
}
