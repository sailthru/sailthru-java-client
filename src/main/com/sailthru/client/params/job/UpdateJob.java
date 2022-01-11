package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;

import com.sailthru.client.SailthruUtil;
import com.sailthru.client.params.ApiFileParams;
import com.sailthru.client.params.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateJob extends Job implements ApiFileParams {
    protected static Logger logger = LoggerFactory.getLogger(UpdateJob.class);
    private static final String JOB = "update";

    protected String emails;
    protected String url;
    protected transient InputStream file;
    protected Map<String, Object> update;
    protected Map<String, Object> query;

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
        try {
            this.file = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        return this;
    }
    
    public UpdateJob setFile(String file) {
        try {
            this.file = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        return this;
    }
    
    public UpdateJob setUpdate(Map<String, Object> update) {
        this.update = update;
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

    public Map<String, InputStream> getFileParams() {
        Map<String, InputStream> files = new HashMap<String, InputStream>();
        if (this.file != null) {
            files.put("file", this.file);
        }
        return files;
    }
}
