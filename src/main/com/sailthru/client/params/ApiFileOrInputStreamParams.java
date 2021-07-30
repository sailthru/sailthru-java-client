package com.sailthru.client.params;

import com.sailthru.client.http.FileOrInputStream;

import java.io.File;
import java.util.Map;


public interface ApiFileOrInputStreamParams {
    public Map<String, FileOrInputStream> getFileOrInputStreamParams();
}
