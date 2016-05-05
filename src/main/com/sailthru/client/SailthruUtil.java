package com.sailthru.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * few static utility methods
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class SailthruUtil {

    public static final String SAILTHRU_API_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

    /**
     * generates MD5 Hash
     * @param data parameter data
     * @return MD5 hashed string 
     */
    public static String md5(String data) {
        try {
            return DigestUtils.md5Hex(data.toString().getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            return DigestUtils.md5Hex(data.toString());
        }
    }

    /**
     * Converts String ArrayList to CSV string
     * @param list List of String to create a CSV string
     * @return CSV string
     */
    public static String arrayListToCSV(List<String> list) {
        StringBuilder csv = new StringBuilder();
        for (String str : list) {
            csv.append(str);
            csv.append(",");
        }
        int lastIndex = csv.length() - 1;
        char last = csv.charAt(lastIndex);
        if (last == ',') {
            return csv.substring(0, lastIndex);
        }
        return csv.toString();
    }

    public static Gson createGson() {
        return new GsonBuilder().setDateFormat(SAILTHRU_API_DATE_FORMAT)
                .registerTypeHierarchyAdapter(Map.class, new NullSerializingMapTypeAdapter())
                .create();
    }
}
