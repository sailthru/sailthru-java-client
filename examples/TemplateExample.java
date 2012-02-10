package com.sailthru.client;

import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SendExample {
    public static void main(String[] args) {
        String apiKey = "****";
        String apiSecret = "****";
        SailthruClient client = new SailthruClient(apiKey, apiSecret);
        try {
            Send send = new Send();
            send.setTemplate("my-template");
            send.setEmail("praj@sailthru.com");

            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("name", "Prajwal Tuladhar");
            Map<String, String> addressVars = new HashMap<String, String>();
            addressVars.put("state", "NY");
            addressVars.put("city", "Jackson Heights");
            addressVars.put("zip", "11372");
            vars.put("address", addressVars);
            
            send.setVars(vars);

            send.setScheduleTime("+10 hours");
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("behalf_email", "user@example.com");
            options.put("test", 1);
            
            send.setOptions(options);
            
            JsonResponse response = client.send(send);
            if (response.isOK()) {
                System.out.println(response.getResponse());
            } else {
                System.out.println(response.getResponse().get("error").toString());
            }
        } catch (ApiException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
    }
}
