package com.sailthru.client.params;


import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateTest {
    private Gson gson = SailthruUtil.createGson();
    private Template template = new Template();

    @Test void setTemplate() {
        template.setTemplate("example template");

        String expected = """
                {"template":"example template"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSample() {
        template.setSample("A");

        String expected = """
                {"sample":"A"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setFromName() {
        template.setFromName("Sailthru");

        String expected = """
                {"from_name":"Sailthru"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setFromEmail() {
        template.setFromEmail("support@sailthru.com");

        String expected = """
                {"from_email":"support@sailthru.com"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSubject() {
        template.setSubject("This is my subject");

        String expected = """
                {"subject":"This is my subject"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setContentHTML() {
        template.setContentHtml("This is my content in HTML");

        String expected = """
                {"content_html":"This is my content in HTML"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setContentText() {
        template.setContentText("This is my content text");

        String expected = """
                {"content_text":"This is my content text"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setContentSMS() {
        template.setContentSms("This is my SMS content");

        String expected = """
                {"content_sms":"This is my SMS content"}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void enableLinkTracking() {
        template.enableLinkTracking();

        String expected = """
                {"is_link_tracking":1}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void enableGoogleAnalytics() {
        template.enableGoogleAnalytics();

        String expected = """
                {"is_google_analytics":1}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setLinkParams() {
        Map<String, String> linkParams = new LinkedHashMap<>();
        linkParams.put("utm_campaign", "Sailthru");
        linkParams.put("utm_template", "Example Template");
        linkParams.put("utm_date", "{date('MMddYYYY')}");
        template.setLinkParams(linkParams);

        String expected = """
                {"link_params":{"utm_campaign":"Sailthru","utm_template":"Example Template","utm_date":"{date(\\u0027MMddYYYY\\u0027)}"}}""";
        String result = gson.toJson(template);
        assertThat(result).isEqualTo(expected);
    }
}
