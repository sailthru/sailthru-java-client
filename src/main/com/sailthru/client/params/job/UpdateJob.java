package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;

import com.sailthru.client.SailthruUtil;
import com.sailthru.client.params.ApiFileParams;
import com.sailthru.client.params.query.Query;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class UpdateJob extends Job implements ApiFileParams {
    
    private static final String JOB = "update";
    
    protected String emails;
    protected String url;
    protected File file;
    protected HashMap<String, Object> update;
    protected HashMap<String, Object> query;
    
    public UpdateJob() {
        this.job = JOB;
        this.update = new HashMap<String, Object>();
    }
    
    public UpdateJob setEmails(List<String> emails) {
        this.emails = SailthruUtil.arrayListToCSV(emails);
        return this;
    }
    
    public UpdateJob setUrl(String url) {
        this.url = url;
        return this;
    }
    
    public UpdateJob setUrl(URI url) {
        this.url = url.toString();
        return this;
    }
    
    public UpdateJob setFile(File file) {
        this.file = file;
        return this;
    }
    
    public UpdateJob setFile(String file) {
        this.file = new File(file);
        return this;
    }
    
    public UpdateJob setUpdate(Map<String, Object> update) {
        this.update = (HashMap<String, Object>)update;
        return this;
    }
    
    public UpdateJob setUpdateVars(Map<String, Object> vars) {
        this.update.put("vars", vars);
        return this;
    }
    
    public UpdateJob setUpdateLists(Map<String, Integer> lists) {
        this.update.put("lists", lists);
        return this;
    }
    
    public UpdateJob setUpdateOptout(String optout) {
        this.update.put("optout", optout);
        return this;
    }
    
    public UpdateJob setQuery(Query query) {
        this.query = query.toHashMap();
        return this;
    }
    
    @Override
    public Type getType() {
        return new TypeToken<UpdateJob>() {}.getType();
    }

    public Map<String, File> getFileParams() {
        Map<String, File> files = new HashMap<String, File>();
        if (this.file != null) {
            files.put("file", this.file);
        }
        return files;
    }
}
