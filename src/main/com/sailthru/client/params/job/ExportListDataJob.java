package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class ExportListDataJob extends Job {
    
    private static final String JOB = "export_list_data";
    
    protected String list;
    
    public ExportListDataJob() {
        this.job = JOB;
    }
    
    public ExportListDataJob setList(String list) {
        this.list = list;
        return this;
    }
    
    @Override
    public Type getType() {
        return new TypeToken<ExportListDataJob>() {}.getType();
    }
}
