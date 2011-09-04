package com.sailthru.client.params.query;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.sailthru.client.handler.JSONHandler;
import java.util.HashMap;

/**
 * Map query builder parameters for API calls
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class Query {
    protected String source_list;
    protected QueryMode query_mode;
    protected ArrayList<String> criteria;
    protected ArrayList<String> value;
    protected ArrayList<Integer> engagement;
    protected ArrayList<Integer> geo_frequency;
    protected ArrayList<Integer> threshold;
    protected ArrayList<String> timerange;
    protected ArrayList<String> field;
    
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
    
    public HashMap<String, Object> toHashMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        JSONHandler handler = new JSONHandler();
        return (HashMap<String, Object>)handler.parseResponse(json);
    }
}
