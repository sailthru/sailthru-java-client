package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.ApiAction;
import com.sailthru.client.params.AbstractApiParams;
import com.sailthru.client.params.ApiParams;
import java.lang.reflect.Type;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
abstract public class Job extends AbstractApiParams implements ApiParams {
    
    protected String job_id;
    protected String job;
    protected String report_email;
    protected String postback_url;
    
    public static final String JOB_ID = "job_id";

    public Type getType() {
        return new TypeToken<Job>() {}.getType();
    }
    
    public Job setReportEmail(String reportEmail) {
        this.report_email = reportEmail;
        return this;
    }
    
    public Job setPostbackUrl(String postbackUrl) {
        this.postback_url = postbackUrl;
        return this;
    }
    
    public Job setJob(String job) {
        this.job = job;
        return this;
    }
    
    @Override
    public ApiAction getApiCall() {
        return ApiAction.job;
    }
}
