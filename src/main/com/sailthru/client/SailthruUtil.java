package com.sailthru.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.lang.reflect.Type;

import org.apache.commons.codec.digest.DigestUtils;

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
        JsonSerializer<Date> ser = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(json.getAsLong());
            }
        };

        return new GsonBuilder()
            .setDateFormat(SAILTHRU_API_DATE_FORMAT)
            .registerTypeAdapter(Date.class, ser)
            .registerTypeAdapter(Date.class, deser)
            .create();
    }
}
