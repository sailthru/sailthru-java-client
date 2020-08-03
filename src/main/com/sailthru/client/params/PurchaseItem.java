package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JsonHandler;

import java.util.Map;
import java.util.List;

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
    protected List<String> tags;
    protected Map<String, Object> vars;
    protected Map<String, Map<String, String>> images;

    public PurchaseItem(Integer qty, String title, Integer price, String id, String url) {
        this.qty = qty.toString();
        this.title = title;
        this.price = price.toString();
        this.id = id;
        this.url = url;
    }

    public PurchaseItem setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PurchaseItem setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public Map<String, Map<String, String>> getImages() {
        return images;
    }

    /*
     * A map of image names to { “url” : <url> } image maps.
     * Use the name “full” to denote the full-sized image, and “thumb” to denote
     * the thumbnail-sized image. Other image names are not reserved.
     *
     * @see #setFullImage(String)
     * @see #setThumbImage(String)
     */
    public PurchaseItem setImages(Map<String, Map<String, String>> images) {
        this.images = images;
        return this;
    }

    public PurchaseItem setFullImage(String url) {
        this.images = SailthruUtil.putImage(this.images, "full", url);
        return this;
    }

    public PurchaseItem setThumbImage(String url) {
        this.images = SailthruUtil.putImage(this.images, "thumb", url);
        return this;
    }


    public Map<String, Object> toHashMap() {
        Gson gson = SailthruUtil.createGson();
        String json = gson.toJson(this);
        JsonHandler handler = new JsonHandler();
        return (Map<String, Object>)handler.parseResponse(json);
    }
}
