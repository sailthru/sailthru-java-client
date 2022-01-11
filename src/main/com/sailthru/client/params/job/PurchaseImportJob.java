package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.params.ApiFileParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PurchaseImportJob extends Job implements ApiFileParams {
    protected static Logger logger = LoggerFactory.getLogger(PurchaseImportJob.class);
    protected transient InputStream file = null;

    public PurchaseImportJob() {
        this.job = "purchase_import";
    }

    public PurchaseImportJob setFile(String filePath) {
        try {
            this.file = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public PurchaseImportJob setFile(File file) {
        try {
            this.file = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    @Override
    public Type getType() {
        return new TypeToken<PurchaseImportJob>() {}.getType();
    }

    public Map<String, InputStream> getFileParams() {
        Map<String, InputStream> files = new HashMap<String, InputStream>();
        if (this.file != null) {
            files.put("file", this.file);
        }
        return files;
    }
}
