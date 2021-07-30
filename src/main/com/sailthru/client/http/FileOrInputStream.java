package com.sailthru.client.http;

import java.io.File;
import java.io.InputStream;

public class FileOrInputStream {
    public File file;
    public InputStream inputStream;
    public String inputStreamName = "noname.csv";

    public boolean isAFile() {
        return this.file != null;
    }
    public boolean isAnInputStream() {
        return this.inputStream != null;
    }
}
