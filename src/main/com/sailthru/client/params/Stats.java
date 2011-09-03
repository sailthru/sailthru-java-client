package com.sailthru.client.params;

import com.sailthru.client.ApiAction;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public abstract class Stats implements ApiParams {
    protected String stat;

    protected static final String MODE_BLAST = "blast";
    protected static final String MODE_LIST = "list";

    public Stats(String stat) {
        this.stat = stat;
    }
    
    public ApiAction getApiCall() {
        return ApiAction.blast;
    }
}
