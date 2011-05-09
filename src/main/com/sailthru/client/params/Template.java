package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Template extends AbstractApiParams implements ApiParams {
    protected String template;
    protected String sample;
    protected String from_name;
    protected String from_email;
    protected String subject;
    protected String content_html;
    protected String content_text;
    protected String content_sms;
    protected Integer is_link_tracking;
    protected Integer is_google_analytics;
    protected String verify_post_url;
    protected HashMap<String, String> link_params;

    public Template setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Template setSample(String sample) {
        this.sample = sample;
        return this;
    }

    public Template setFromName(String fromName) {
        this.from_name = fromName;
        return this;
    }

    public Template setFromEmail(String fromEmail) {
        this.from_email = fromEmail;
        return this;
    }

    public Template setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Template setContentHtml(String contentHtml) {
        this.content_html = contentHtml;
        return this;
    }

    public Template setContentText(String contentText) {
        this.content_text = contentText;
        return this;
    }

    public Template setContentSms(String contentSms) {
        this.content_sms = contentSms;
        return this;
    }

    public Template enableLinkTracking() {
        this.is_link_tracking = 1;
        return this;
    }

    public Template enableGoogleAnalytics() {
        this.is_google_analytics = 1;
        return this;
    }

    public Template setLinkParams(HashMap<String, String> linkParams) {
        this.link_params = linkParams;
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<Template>() {}.getType();
        return type;
    }
}