package com.sailthru.client;

import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.*;
import com.sailthru.client.params.job.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class exposing API calls for Sailthru API as per http://docs.sailthru.com/api
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class SailthruClient extends AbstractSailthruClient {

    /**
     * Singleton instance variable
     */
    private static SailthruClient _instance;

    /**
     * Public constructor for SailthruClient class with custom API URL
     * @param apiKey Sailthru API key string
     * @param apiSecret Sailthru API secret string
     * @param apiUrl Sailthru API URL
     */
    public SailthruClient(String apiKey, String apiSecret, String apiUrl) {
        super(apiKey, apiSecret, apiUrl);
    }

    /**
     * Public constructor for SailthruClient class with default API URL
     * @param apiKey Sailthru API key string
     * @param apiSecret Sailthru API secret string
     */
    public SailthruClient(String apiKey, String apiSecret) {
        super(apiKey, apiSecret, DEFAULT_API_URL);
    }

    /**
     * Synchronized singleton instance method using default URL string
     * @param apiKey Sailthru API key string
     * @param apiSecret Sailthru API secret string
     * @return singleton instance of SailthruClient
     * @deprecated
     */
    public static synchronized SailthruClient getInstance(String apiKey, String apiSecret) {
        if (_instance == null) {
            _instance = new SailthruClient(apiKey, apiSecret, DEFAULT_API_URL);
        }
        return _instance;
    }

    /**
     * Synchronized singleton instance method using default URL string
     * @param apiKey Sailthru API key string
     * @param apiSecret Sailthru API secret string
     * @param apiUrl Sailthru API URL
     * @return singleton instance of SailthruClient
     * @deprecated
     */
    public static synchronized SailthruClient getInstance(String apiKey, String apiSecret, String apiUrl) {
        if (_instance == null) {
            _instance = new SailthruClient(apiKey, apiSecret, apiUrl);
        }
        return _instance;
    }

    /**
     * Get information about one of your users.
     * @param email
     * @throws IOException
     */
    public JsonResponse getEmail(String email) throws IOException {
        Email emailObj = new Email();
        emailObj.setEmail(email);
        return apiGet(emailObj);
    }

    /**
     * Update information about one of your users, including adding and removing the user from lists.
     * @param email
     * @throws IOException
     */
    public JsonResponse setEmail(Email email) throws IOException {
        return apiPost(email);
    }

    /**
     * Get the status of a transational send
     * @param sendId Unique send id
     * @throws IOException
     */
    public JsonResponse getSend(String sendId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Send.PARAM_SEND_ID, sendId);
        return apiGet(ApiAction.send, data);
    }

    /**
     * send an email template to a single email address.
     * @param send Send Object
     * @throws IOException
     */
    public JsonResponse send(Send send) throws IOException {
        return apiPost(send);
    }

    /**
     * send an email template to multiple email addresses
     * @param multiSend
     * @throws IOException
     */
    public JsonResponse multiSend(MultiSend multiSend) throws IOException {        
        return apiPost(multiSend);
    }

    /**
     * Cancel a send that was scheduled for a future time.
     * @param sendId
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse cancelSend(String sendId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Send.PARAM_SEND_ID, sendId);
        return apiDelete(ApiAction.send, data);
    }
    
    /**
     * Cancel a send that was scheduled for a future time.
     * @param send
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse cancelSend(Send send) throws IOException {
        return apiDelete(send);
    }

    /**
     * get information about a blast
     * @param blastId
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse getBlast(Integer blastId) throws IOException {
        Blast blast = new Blast();
        blast.setBlastId(blastId);
        return apiGet(blast);
    }

    /**
     * Schedule a mass mail blast
     * @param blast
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse scheduleBlast(Blast blast) throws IOException {
        return apiPost(blast);
    }

    /**
     * Schedule a mass mail from a template
     * @param template template name
     * @param list list name
     * @param scheduleTime schedule time for the blast
     * @param blast Blast Object
     * @throws IOException
     */
    public JsonResponse scheduleBlastFromTemplate(String template, String list, Date scheduleTime, Blast blast) throws IOException {
        blast.setCopyTemplate(template);
        blast.setList(list);
        blast.setScheduleTime(scheduleTime);
        return apiPost(blast);
    }

    /**
     * Schedule a mass mail from a template
     * @param template template name
     * @param list list name
     * @param scheduleTime schedule time for the blast
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse scheduleBlastFromTemplate(String template, String list, Date scheduleTime) throws IOException {
        Blast blast = new Blast();
        blast.setCopyTemplate(template);
        blast.setList(list);
        blast.setScheduleTime(scheduleTime);
        return apiPost(blast);
    }

    /**
     * Schedule a mass mail blast from previous blast
     * @param blastId blast ID
     * @param scheduleTime schedule time for the blast
     * @param blast Blast object
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse scheduleBlastFromBlast(Integer blastId, Date scheduleTime, Blast blast) throws IOException {
        blast.setCopyBlast(blastId);
        blast.setScheduleTime(scheduleTime);
        return apiPost(blast);
    }

    /**
     * Schedule a mass mail blast from previous blast
     * @param blastId blast ID
     * @param scheduleTime schedule time for the blast
     * @throws IOException
     */
    public JsonResponse scheduleBlastFromBlast(Integer blastId, Date scheduleTime) throws IOException {
        Blast blast = new Blast();
        blast.setCopyBlast(blastId);
        blast.setScheduleTime(scheduleTime);
        return apiPost(blast);
    }

    /**
     * Update existing blast
     * @param blastId
     * @throws IOException
     */
    public JsonResponse updateBlast(Integer blastId) throws IOException {
        Blast blast = new Blast();
        blast.setBlastId(blastId);
        return apiPost(blast);
    }

    /**
     * Update existing blast
     * @param blastId
     * @param blast
     * @throws IOException
     */
    public JsonResponse updateBlast(Integer blastId, Blast blast) throws IOException {
        blast.setBlastId(blastId);
        return apiPost(blast);
    }

    /**
     * Delete existing blast
     * @param blastId
     * @throws IOException
     */
    public JsonResponse deleteBlast(Integer blastId) throws IOException {
        Blast blast = new Blast();
        blast.setBlastId(blastId);
        return apiDelete(blast);
    }

    /**
     * Cancel a scheduled Blast
     * @param blastId Unique Blast ID
     * @throws IOException
     */
    public JsonResponse cancelBlast(Integer blastId) throws IOException {
        Blast blast = new Blast();
        Date d = null;
        blast
            .setBlastId(blastId)
            .setScheduleTime(d);
        return apiPost(blast);
    }

    /**
     * Get template information
     * @param template template name
     * @throws IOException
     */
    public JsonResponse getTemplate(String template) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Template.PARAM_TEMPLATE, template);
        return apiGet(ApiAction.template, data);
    }

    /**
     * Save / update a template
     * @param template template name
     * @throws IOException
     */
    public JsonResponse saveTemplate(Template template) throws IOException {
        return apiPost(template);
    }

    /**
     * Delete existing template
     * @param template template name
     * @throws IOException
     */
    public JsonResponse deleteTemplate(String template) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Template.PARAM_TEMPLATE, template);
        return apiDelete(ApiAction.template, data);
    }

    /**
     * Push a new piece of content to Sailthru, triggering any applicable alerts.
     * @param content
     * @throws IOException
     */
    public JsonResponse pushContent(Content content) throws IOException {
        return apiPost(content);
    }
    
    /**
     * Push an event to Sailthru, triggering any applicable triggers.
     * @param event
     * @throws IOException
     */
    public JsonResponse pushEvent(Event event) throws IOException {
        return apiPost(event);
    }

    /**
     * Retrieve a user's alert settings
     * @param email
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse getAlert(String email) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Alert.PARAM_EMAIL, email);
        return apiGet(ApiAction.alert, data);
    }

    /**
     * Add a new alert to a user. You can add either a realtime or a summary alert (daily/weekly).
     * @param alert
     * @return JsonResponse
     * @throws IOException
     */
    public JsonResponse saveAlert(Alert alert) throws IOException {
        return apiPost(alert);
    }

    /**
     * Delete existing user alert
     * @param email User.java Email
     * @param alertId Alert ID
     * @throws IOException
     */
    public JsonResponse deleteAlert(String email, String alertId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Alert.PARAM_EMAIL, email);
        data.put(Alert.PARAM_ALERT_ID, alertId);
        return apiDelete(ApiAction.alert, data);
    }

    /**
     * Record that a user has made a purchase, or has added items to their purchase total
     * @param purchase
     * @throws IOException
     */
    public JsonResponse purchase(Purchase purchase) throws IOException {
        return apiPost(purchase);
    }

    /**
     * Make stats API request
     * @param stats
     * @throws IOException
     */
    protected JsonResponse stats(Stats stats) throws IOException {
        return apiGet(stats);
    }

    /**
     * get list stats information
     * @param stat
     * @return JsonResponse
     * @throws IOException
     */
    public Map<String, Object> listStats(ListStat stat) throws IOException {
        return (Map<String, Object>)this.stats(stat);
    }

    /**
     * get blast stats information
     * @param stat
     * @return JsonResponse
     * @throws IOException
     */
    public Map<String, Object> blastStats(BlastStat stat) throws IOException {
        return (Map<String, Object>)this.stats(stat);
    }
    
    
    /**
     * Get status of a job
     * @param jobId
     * @return JsonResponse
     * @throws IOException 
     */
    public JsonResponse getJobStatus(String jobId) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Job.JOB_ID, jobId);
        return apiGet(ApiAction.job, params);
    }
    
    
    /**
     * Process import job from given email string CSV or file path of a CSV or email per line file
     * @param job
     * @throws IOException 
     */
    public JsonResponse processImportJob(ImportJob job) throws IOException {
        return apiPost(job, job);
    }
    
    
    /**
     * Query user data set and generate a detailed snapshot of their analytics similar to that shown in the Snapshot Report in the Sailthru interface.
     * @param job SnapshotJob
     * @throws IOException 
     */
    public JsonResponse processSnapshotJob(SnapshotJob job) throws IOException {
        return apiPost(job);
    }
    
    
    /**
     * Export blast data in CSV format
     * @param job BlastQueryJob
     * @throws IOException 
     */
    public JsonResponse processBlastQueryJob(BlastQueryJob job) throws IOException {
        return apiPost(job);
    }
    
    
    /**
     * Export user data from a list in CSV format
     * @param job ExportListDataJob
     * @throws IOException 
     */
    public JsonResponse processExportListDataJob(ExportListDataJob job) throws IOException {
        return apiPost(job);
    }
    
    
    /**
     * Perform a bulk update of any number of user profiles
     * @param job UpdateJob
     * @throws IOException
     */
    public JsonResponse processUpdateJob(UpdateJob job) throws IOException {
        return apiPost(job, job);
    }

    public JsonResponse getUser(User user) throws IOException {
        return apiGet(user);
    }

    public JsonResponse saveUser(User user) throws IOException {
        return apiPost(user);
    }
}
