package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.http.FileOrInputStream;
import com.sailthru.client.params.ApiFileOrInputStreamParams;
import com.sailthru.client.params.ApiFileParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class ImportJob extends Job implements ApiFileOrInputStreamParams {
    
    private static final String JOB = "import";
    
    protected String emails;
    protected transient FileOrInputStream fileOrInputStream = new FileOrInputStream();

    protected String list;

    public ImportJob() {
        this.job = JOB;
    }
    
    public ImportJob setEmails(List<String> emails) {
        this.emails = SailthruUtil.arrayListToCSV(emails);
        return this;
    }
    
    public ImportJob setFile(String filePath) {
        this.fileOrInputStream.file = new File(filePath);
        return this;
    }
    
    public ImportJob setFile(File file) {
        this.fileOrInputStream.file = file;
        return this;
    }

    public ImportJob setInputStreamData(String data) {
        this.fileOrInputStream.inputStream = new ByteArrayInputStream(data.getBytes());
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
    
    public Map<String, FileOrInputStream> getFileOrInputStreamParams() {
        Map<String, FileOrInputStream> filesOrInputStreams = new HashMap<String, FileOrInputStream>();
        if (this.fileOrInputStream.file != null) {
            filesOrInputStreams.put("file", this.fileOrInputStream);
        }
        if (this.fileOrInputStream.inputStream != null) {
            filesOrInputStreams.put("file", this.fileOrInputStream);
        }
        return filesOrInputStreams;
    }
}
