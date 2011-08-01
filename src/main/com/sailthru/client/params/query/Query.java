package com.sailthru.client.params.query;

import com.sailthru.client.params.AbstractApiParams;
import java.util.ArrayList;

/**
 * Map query builder parameters for API calls
 * @see http://docs.sailthru.com/api/query
 * @author praj
 */
public class Query extends AbstractApiParams {
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
}
