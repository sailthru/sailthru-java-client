package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.params.query.Query;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 *
 * @author praj
 */
public class SnapshotJob extends Job {
    
    private static final String JOB = "snapshot";
    
    protected HashMap<String, Object> query;
    
    public SnapshotJob() {
        this.job = JOB;
    }
    
    public SnapshotJob setQuery(Query query) {
        this.query = query.toHashMap();
        return this;
    }
    
    @Override
    public Type getType() {
        return new TypeToken<SnapshotJob>() {}.getType();
    }
}
