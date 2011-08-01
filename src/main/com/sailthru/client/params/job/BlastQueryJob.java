package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author praj
 */
public class BlastQueryJob extends Job {
    
    private static final String JOB = "blast_query";
    
    protected int blast_id;
    
    public BlastQueryJob() {
        this.job = JOB;
    }
    
    public BlastQueryJob setBlastId(int blastId) {
        this.blast_id = blastId;
        return this;
    }
    
    @Override
    public Type getType() {
        return new TypeToken<BlastQueryJob>() {}.getType();
    }
}
