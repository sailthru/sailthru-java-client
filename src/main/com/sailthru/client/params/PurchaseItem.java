package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JSONHandler;

import java.util.Map;
import java.util.List;

/**
 *
 * @author Prajwal Tuladhar
 */
public class PurchaseItem {
    protected String qty;
    protected String title;
    protected String price;
    protected String id;
    protected String url;
    protected String tags;
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

    public Map<String, String> toHashMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        JSONHandler handler = new JSONHandler();
        return (Map<String, String>)handler.parseResponse(json);
    }
}