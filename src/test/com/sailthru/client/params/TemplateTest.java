package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateTest  {
    private Gson gson = SailthruUtil.createGson();
    private Template template = new Template();

    @Test
    public void testSetTemplate(){
        template.setTemplate("example template");

        String expected = "{\"template\":\"example template\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetSample(){
        template.setSample("A");

        String expected = "{\"sample\":\"A\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetFromName(){
        template.setFromName("Sailthru");

        String expected = "{\"from_name\":\"Sailthru\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetFromEmail(){
        template.setFromEmail("support@sailthru.com");

        String expected = "{\"from_email\":\"support@sailthru.com\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetSubject(){
        template.setSubject("This is my subject");

        String expected = "{\"subject\":\"This is my subject\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetContentHTML(){
        template.setContentHtml("This is my content in HTML");

        String expected = "{\"content_html\":\"This is my content in HTML\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetContentText(){
        template.setContentText("This is my content text");

        String expected = "{\"content_text\":\"This is my content text\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetContentSMS(){
        template.setContentSms("This is my SMS content");

        String expected = "{\"content_sms\":\"This is my SMS content\"}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testEnableLinkTracking(){
        template.enableLinkTracking();

        String expected = "{\"is_link_tracking\":1}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testEnableGoogleAnalytics(){
        template.enableGoogleAnalytics();

        String expected = "{\"is_google_analytics\":1}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetLinkParams(){
        Map<String, String> linkParams = new LinkedHashMap<String, String>();
        linkParams.put("utm_campaign", "Sailthru");
        linkParams.put("utm_template", "Example Template");
        linkParams.put("utm_date", "{date('MMddYYYY')}");
        template.setLinkParams(linkParams);

        String expected = "{\"link_params\":{\"utm_campaign\":\"Sailthru\",\"utm_template\":\"Example Template\",\"utm_date\":\"{date(\\u0027MMddYYYY\\u0027)}\"}}";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }
}