package com.sailthru.client.handler;

import com.google.gson.*;
import com.sailthru.client.SailthruUtil;

/**
 * handles JSON response from server
 */
public class JsonHandler implements SailthruResponseHandler {

    public static final String format = "json";

    @Override
    public Object parseResponse(String response) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(SailthruUtil.SAILTHRU_API_DATE_FORMAT);
        builder.setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE);
        Gson gson = builder.create();
        return gson.fromJson(response, Object.class);
    }

    @Override
    public String getFormat() {
        return format;
    }

}