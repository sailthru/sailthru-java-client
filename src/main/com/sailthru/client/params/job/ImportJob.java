package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.params.ApiFileParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import java.util.HashMap;

public class ImportJob extends Job implements ApiFileParams {
    private static final String JOB = "import";
    protected static Logger logger = LoggerFactory.getLogger(ImportJob.class);
    protected String emails;
    protected transient InputStream file = null;
    protected String list;

    public ImportJob() {
        this.job = JOB;
    }
    
    public ImportJob setEmails(List<String> emails) {
        this.emails = SailthruUtil.arrayListToCSV(emails);
        return this;
    }

    public ImportJob setFile(String filePath) {
        try {
            this.file = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public ImportJob setFile(File file) {
        try {
            this.file = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public ImportJob setFileInputStream(String data) {
        this.file = new ByteArrayInputStream(data.getBytes());
        return this;
    }
    
    public ImportJob setList(String list) {
        this.list = list;
        return this;
    }
    
    @Override
    public Type getType() {
        return new TypeToken<ImportJob>() {}.getType();
    }

    public Map<String, InputStream> getFileParams() {
        Map<String, InputStream> files = new HashMap<String, InputStream>();
        if (this.file != null) {
            files.put("file", this.file);
        }
        return files;
    }
}
