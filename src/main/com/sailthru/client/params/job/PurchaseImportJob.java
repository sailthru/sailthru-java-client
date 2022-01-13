package com.sailthru.client.params.job;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.params.ApiFileParams;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PurchaseImportJob extends Job implements ApiFileParams {
    protected transient File file = null;

    public PurchaseImportJob() {
        this.job = "purchase_import";
    }

    public PurchaseImportJob setFile(String filePath) {
        this.file = new File(filePath);
        return this;
    }

    public PurchaseImportJob setFile(File file) {
        this.file = file;
        return this;
    }

    @Override
    public Type getType() {
        return new TypeToken<PurchaseImportJob>() {}.getType();
    }

    public Map<String, Object> getFileParams() {
        Map<String, Object> files = new HashMap<String, Object>();
        if (this.file != null) {
            files.put("file", this.file);
        }
        return files;
    }
}
