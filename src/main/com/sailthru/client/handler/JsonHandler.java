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
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class JsonHandler implements SailthruResponseHandler {

    public static final String format = "json";

    
    public Object parseResponse(String response) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(SailthruUtil.SAILTHRU_API_DATE_FORMAT);
        builder.registerTypeAdapter(Object.class, new NaturalDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(response, Object.class);
    }

    
    public String getFormat() {
        return format;
    }

    // http://stackoverflow.com/questions/2779251/convert-json-to-hashmap-using-gson-in-java/4799594#4799594
    //Will get rid of this at some point
    class NaturalDeserializer implements JsonDeserializer<Object>  {

        public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            if (json.isJsonNull()) {
                return null;
            }
            else if (json.isJsonPrimitive()) {
                return handlePrimitive(json.getAsJsonPrimitive());
            }
            else if (json.isJsonArray()) {
                return handleArray(json.getAsJsonArray(), context);
            }
            else {
                return handleObject(json.getAsJsonObject(), context);
            }
        }

        private Object handlePrimitive(JsonPrimitive json) {
            if (json.isString()) {
                return json.getAsString();
            }
            else if (json.isBoolean()) {
                return json.getAsBoolean();
            }
            else {
                BigDecimal bigDec = json.getAsBigDecimal();
                // Find out if it is an int type
                try {
                    bigDec.toBigIntegerExact();
                    try {
                        return bigDec.intValueExact();
                    }
                    catch (ArithmeticException e) {}
                    return bigDec.longValue();
                }
                catch (ArithmeticException e) {}
                return bigDec.doubleValue();
            }
        }

        private Object handleArray(JsonArray json, JsonDeserializationContext context) {            
            Object[] array = new Object[json.size()];
            for (int i = 0; i < array.length; i++) {
                if (json.get(i).isJsonObject()) {
                    array[i] = handleObject((JsonObject)json.get(i), context);
                }
                else {
                    array[i] = context.deserialize(json.get(i), Object.class);
                }                                
            }
            return array;
        }

        private Object handleObject(JsonObject json, JsonDeserializationContext context) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {                
                map.put(entry.getKey(), context.deserialize(entry.getValue(), Object.class));
            }
            return map;
        }
    }
}
