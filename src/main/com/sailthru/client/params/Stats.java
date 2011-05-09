package com.sailthru.client.params;

/**
 *
 * @author Prajwal Tuladhar
 */
public abstract class Stats implements ApiParams {
    protected String stat;

    protected static final String MODE_BLAST = "blast";
    protected static final String MODE_LIST = "list";

    public Stats(String stat) {
        this.stat = stat;
    }
}
