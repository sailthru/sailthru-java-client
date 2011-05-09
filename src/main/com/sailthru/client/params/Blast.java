package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Blast extends AbstractApiParams implements ApiParams {
    protected String name;
    protected String list;
    protected Date schedule_time;
    protected String from_name;
    protected String from_email;
    protected String subject;
    protected String content_html;
    protected String content_text;

    protected Integer blast_id;
    protected Integer copy_blast;
    protected String copy_template;
    protected String eval_template;
    protected String replyto;
    protected String report_email;
    protected Integer is_link_tracking;
    protected Integer is_google_tracking;
    protected Integer is_public;
    protected String suppress_list;
    protected HashMap<String, Object> test_vars;
    protected Integer email_hour_range;
    protected Integer abtest;
    protected Integer test_percent;
    protected String data_feed_url;

    public Blast(String name, String list, Date scheduleTime, String fromName, String fromEmail, String subject, String contentHtml, String contentText) {
        this.name = name;
        this.list = list;
        this.schedule_time = scheduleTime;
        this.from_name = fromName;
        this.from_email = fromEmail;
        this.subject = subject;
        this.content_html = contentHtml;
        this.content_text = contentText;
    }

    public Blast() {
        
    }

    public Blast setName(String name) {
        this.name = name;
        return this;
    }

    public Blast setList(String list) {
        this.list = list;
        return this;
    }

    public Blast setScheduleTime(Date schedule_time) {
        this.schedule_time = schedule_time;
        return this;
    }

    public Blast setFromName(String fromName) {
        this.from_name = fromName;
        return this;
    }

    public Blast setFromEmail(String fromEmail) {
        this.from_email = fromEmail;
        return this;
    }

    public Blast setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Blast setContentHtml(String contentHtml) {
        this.content_html = contentHtml;
        return this;
    }

    public Blast setContentText(String contentText) {
        this.content_text = contentText;
        return this;
    }

    
    public Blast setBlastId(Integer blastId) {
        this.blast_id = blastId;
        return this;
    }

    public Blast setCopyBlast(Integer copyBlastId) {
        this.copy_blast = copyBlastId;
        return this;
    }

    public Blast setCopyTemplate(String copyTemplate) {
        this.copy_template = copyTemplate;
        return this;
    }

    public Blast setEvalTemplate(String evalTemplate) {
        this.eval_template = evalTemplate;
        return this;
    }

    public Blast setReplyTo(String replyTo) {
        this.replyto = replyTo;
        return this;
    }

    public Blast setReportEmail(String reportEmail) {
        this.report_email = reportEmail;
        return this;
    }

    public Blast enableLinkTracking() {
        this.is_link_tracking = 1;
        return this;
    }

    public Blast enableGoogleAnalytics() {
        this.is_google_tracking = 1;
        return this;
    }

    public Blast setAsPublic() {
        this.is_public = 1;
        return this;
    }

    public Blast setSupressList(String supressList) {
        this.suppress_list = supressList;
        return this;
    }

    public Blast setTestVars(HashMap<String, Object> testVars) {
        this.test_vars = testVars;
        return this;
    }

    public Blast setEmailHourRange(Integer hours) {
        this.email_hour_range = hours;
        return this;
    }

    public Blast enableABTest() {
        this.abtest = 1;
        return this;
    }

    public Blast setTestPercent(Integer percentage) {
        this.test_percent = percentage;
        return this;
    }

    public Blast setDataFeedUrl(String dataFeedUrl) {
        this.data_feed_url = dataFeedUrl;
        return this;
    }

    public Blast setDataFeedUrl(URI dataFeedUrl) {
        this.data_feed_url = dataFeedUrl.toString();
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<Blast>() {}.getType();
        return type;
    }
}