package com.sailthru.client;

import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemplateExample {
    public static void main(String[] args) {
        String apiKey = "****";
        String apiSecret = "****";
        SailthruClient client = new SailthruClient(apiKey, apiSecret);
        try {
            Template template = new Template();
            template.setTemplate("my-template");
            template.setFromEmail("no-reply@example.com");
            template.setFromName("Example Example");
            template.setSubject("Hello {name}!");
            template.setContentHtml("HTML content goes here");
            template.setContentText("Text content goes here");


            JsonResponse response = client.saveTemplate(template);

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
