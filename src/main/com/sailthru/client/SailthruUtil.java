package com.sailthru.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * few static utility methods
 * @author Prajwal Tuladhar <praj@sailthru.com>
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

    /**
     * Add a new image entry to the images map.
     *
     * @param images map containing the references of images. Can be null, in that case the returned map will be a new instance with the entry
     * @param key key for the map, either "full" or "thumb"
     * @param url url for the image to use
     * @return a new map instance of the images parameter was null otherwise the updated map.
     */
    public static Map<String, Map<String, String>> putImage(Map<String, Map<String, String>> images, String key, String url) {
        if (images == null) {
            images = new HashMap<String, Map<String, String>>();
        }
        Map<String, String> urlMap = new HashMap<String, String>();
        urlMap.put("url", url);
        images.put(key, urlMap);
        return images;
    }

}
