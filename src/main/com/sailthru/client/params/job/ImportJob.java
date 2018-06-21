package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.params.ApiFileParams;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class ImportJob extends Job implements ApiFileParams {
    
    private static final String JOB = "import";
    
    protected String emails;
    protected transient File file = null;
    protected String list;

    public ImportJob() {
        this.job = JOB;
    }
    
    public ImportJob setEmails(List<String> emails) {
        this.emails = SailthruUtil.arrayListToCSV(emails);
        return this;
    }
    
    public ImportJob setFile(String filePath) {
        this.file = new File(filePath);
        return this;
    }
    
    public ImportJob setFile(File file) {
        this.file = file;
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
    
    
    public Map<String, File> getFileParams() {
        Map<String, File> files = new HashMap<String, File>();
        if (this.file != null) {
            files.put("file", this.file);
        }
        return files;
    }
}
