package com.sailthru.client;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * few static utility methods
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class SailthruUtil {

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
}