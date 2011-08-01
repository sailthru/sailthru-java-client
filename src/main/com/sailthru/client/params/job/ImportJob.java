package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import java.io.File;
import java.util.List;
import java.lang.reflect.Type;

/**
 *
 * @author praj
 */
public class ImportJob extends Job {
    
    private static final String JOB = "import";
    
    protected String emails;
    protected File file;
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
}
