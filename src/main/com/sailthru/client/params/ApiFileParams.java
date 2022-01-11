package com.sailthru.client.params;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public interface ApiFileParams {
    public Map<String, FileInputStream> getFileParams();
}
