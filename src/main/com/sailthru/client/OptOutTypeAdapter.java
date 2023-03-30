package com.sailthru.client;

import com.google.gson.*;
import com.sailthru.client.params.User;

import java.lang.reflect.Type;

public class OptOutTypeAdapter implements JsonSerializer<User.OptOutType>, JsonDeserializer<User.OptOutType> {

    @Override
    public JsonElement serialize(User.OptOutType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.optOutTypeStr);
    }

    @Override
    public User.OptOutType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String optOutTypeStr = json.getAsString();
        for (User.OptOutType optOutType : User.OptOutType.values()) {
            if (optOutType.optOutTypeStr.equals(optOutTypeStr)) {
                return optOutType;
            }
        }
        throw new JsonParseException("Invalid OptOutType value: " + optOutTypeStr);
    }
}