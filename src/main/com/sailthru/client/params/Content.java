package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class Content extends AbstractApiParams implements ApiParams {
    // required
    protected String url;

    // optional
    protected String title;
    protected String date;
    protected String expire_date;
    protected List tags;
    protected Map<String, Object> vars;
    protected Map<String, Map<String, String>> images;
    protected List<Double> location;
    protected Long price;
    protected String description;
    protected String site_name;
    protected String author;
    protected Integer spider;

    @Override
    public ApiAction getApiCall() {
        return ApiAction.content;
    }

    public static enum ContentSpecialVar {PRICE, DESCRIPTION, BRAND};

    public Type getType() {
        Type type = new TypeToken<Content>() {}.getType();
        return type;
    }

    public Content setTitle(String title) {
        this.title = title;
        return this;
    }

    public Content setUrl(String url) {
        this.url = url;
        return this;
    }

    public Content setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }
    
    public Content setDate(Date date) {
        this.date = date.toString();
        return this;
    }

    public Content setDate(String date) {
        this.date = date;
        return this;
    }

    public Content setExpireDate(Date date) {
        this.expire_date = date.toString();
        return this;
    }

    public Content setExpireDate(String date) {
        this.expire_date = date;
        return this;
    }

    public Content setVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public Content setSpecialVars(ContentSpecialVar var, String value) {
        switch (var) {
            case PRICE:
                this.vars.put("price", value);
                break;

            case DESCRIPTION:
                this.vars.put("description", value);
                break;

            case BRAND:
                this.vars.put("brand", value);
                break;
        }
        return this;
    }

    /*
     * A map of image names to { “url” : <url> } image maps.
     * Use the name “full” to denote the full-sized image, and “thumb” to denote
     * the thumbnail-sized image. Other image names are not reserved.
     * Or use setFullImage and setThumbImage to set them separately.
     */
    public Content setImages(Map<String, Map<String, String>> images) {
        this.images = images;
        return this;
    }

    public Content setFullImage(String url) {
        if (images == null) {
            images = new HashMap<String, Map<String, String>>();
        }
        Map<String, String> urlMap = new HashMap<String, String>();
        urlMap.put("url", url);
        images.put("full", urlMap);
        return this;
    }

    public Content setThumbImage(String url) {
        if (images == null) {
            images = new HashMap<String, Map<String, String>>();
        }
        Map<String, String> urlMap = new HashMap<String, String>();
        urlMap.put("url", url);
        images.put("thumb", urlMap);
        return this;
    }

    /*
     * specify location of the content.
     * List should be in order of [latitude, longitude]
     */
    public Content setLocation(List<Double> location) {
        this.location = location;
        return this;
    }

    public Content setLocation(double latitude, double longitude) {
        location = new ArrayList<Double>();
        location.add(latitude);
        location.add(longitude);
        return this;
    }

    /*
     * For pieces of content with a purchase value, this param should be used
     * for the local price of the product, measured in cents. For example, a
     * product that costs $172.99 should have price = 17299
     */
    public Content setPrice(long price) {
        this.price = new Long(price);
        return this;
    }

    public Content setDescription(String description) {
        this.description = description;
        return this;
    }

    public Content setSiteName(String site_name) {
        this.site_name = site_name;
        return this;
    }

    public Content setAuthor(String author) {
        this.author = author;
        return this;
    }

    /*
     * enable spider will force a respidering of the content within a few minutes
     */
    public Content enableSpider() {
        this.spider = 1;
        return this;
    }
}
