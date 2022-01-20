package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.params.ApiFileParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import java.util.HashMap;

public class ImportJob extends Job implements ApiFileParams {
    private static final String JOB = "import";
    protected String emails;
    protected transient File file = null;
    protected transient InputStream fileInputStream = null;
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

    public ImportJob setFileDataAsString(String data) {
        this.fileInputStream = new ByteArrayInputStream(data.getBytes());
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

    public Map<String, Object> getFileParams() {
        Map<String, Object> files = new HashMap<String, Object>();
        if (this.file != null) {
            files.put("file", this.file);
        } else if (this.fileInputStream != null) {
            files.put("file", this.fileInputStream);
        }
        return files;
    }
}
