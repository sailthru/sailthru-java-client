package com.sailthru.client.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sailthru.client.SailthruUtil;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * handles JSON response from server
 */
public class JsonHandler implements SailthruResponseHandler {

    public static final String format = "json";

    @Override
    public Object parseResponse(String response) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(SailthruUtil.SAILTHRU_API_DATE_FORMAT);
        Gson gson = builder.create();
        return gson.fromJson(response, Map.class);
    }

    @Override
    public String getFormat() {
        return format;
    }

}
