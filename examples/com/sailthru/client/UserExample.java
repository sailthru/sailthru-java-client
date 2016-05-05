package com.sailthru.client;

import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserExample {
    public static void main(String[] args) {
        String apiKey = "****";
        String apiSecret = "****";
        SailthruClient client = new SailthruClient(apiKey, apiSecret);
        try {
            String sailthruId = "4d371896cc0c1adbb079b8d0";
            User user = new User(sailthruId);

            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("name", "Prajwal Tuladhar");
            Map<String, String> addressVars = new HashMap<String, String>();
            addressVars.put("state", "NY");
            addressVars.put("city", "Jackson Heights");
            addressVars.put("zip", "11372");
            vars.put("address", addressVars);
            user.setVars(vars);

            JsonResponse response1 = client.getUser(user); // GET

            JsonResponse response2 = client.saveUser(user);

            if (response1.isOK()) {
                System.out.println(response1.getResponse());
            } else {
                System.out.println(response1.getResponse().get("error").toString());
            }
        } catch (ApiException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
    }
}
