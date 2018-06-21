package com.sailthru.client.params.query;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.handler.JsonHandler;

import java.util.ArrayList;
import java.util.Map;

/**
 * Map query builder parameters for API calls
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class Query {
    protected String source_list;
    protected QueryMode query_mode;
    protected java.util.List<String> criteria;
    protected java.util.List<String> value;
    protected java.util.List<Integer> engagement;
    protected java.util.List<Integer> geo_frequency;
    protected java.util.List<Integer> threshold;
    protected java.util.List<String> timerange;
    protected java.util.List<String> field;
    
    public Query() {
        this.criteria = new ArrayList<String>();
        this.value = new ArrayList<String>();
        this.engagement = new ArrayList<Integer>();
        this.geo_frequency = new ArrayList<Integer>();
        this.threshold = new ArrayList<Integer>();
        this.timerange = new ArrayList<String>();
        this.field = new ArrayList<String>();
    }
    
    public Query setSourceList(String list) {
        this.source_list = list;
        return this;
    }
    
    public Query addCriteria(Criteria criteria) {
        this.criteria.add(criteria.toString());
        return this;
    }
    
    public Query addValue(String value) {
        this.value.add(value);
        return this;
    }
    
    public Query addEngagement(int level) {
        this.engagement.add(level);
        return this;
    }
    
    public Query addGeoFrequency(int geoFrequency) {
        this.geo_frequency.add(geoFrequency);
        return this;
    }
    
    public Query addThreshold(int threshold) {
        this.threshold.add(threshold);
        return this;
    }
    
    public Query addTimeRange(TimeRange timeRange) {
        this.timerange.add(timeRange.toString());
        return this;
    }
    
    public Map<String, Object> toHashMap() {
        Gson gson = SailthruUtil.createGson();
        String json = gson.toJson(this);
        JsonHandler handler = new JsonHandler();
        return (Map<String, Object>)handler.parseResponse(json);
    }
}
