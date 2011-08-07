package com.sailthru.client;

import com.sailthru.client.params.Alert;
import com.sailthru.client.params.Blast;
import com.sailthru.client.params.BlastStat;
import com.sailthru.client.params.Content;
import com.sailthru.client.params.Email;
import com.sailthru.client.params.job.ImportJob;
import com.sailthru.client.params.ListStat;
import com.sailthru.client.params.MultiSend;
import com.sailthru.client.params.Purchase;
import com.sailthru.client.params.Send;
import com.sailthru.client.params.Stats;
import com.sailthru.client.params.Template;
import com.sailthru.client.params.job.BlastQueryJob;
import com.sailthru.client.params.job.ExportListDataJob;
import com.sailthru.client.params.job.SnapshotJob;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar
 */
public class SailthruClient extends AbstractSailthruClient {

    /**
     * Singleton instance variable
     */
    private static SailthruClient _instance;

    /**
     * Public constructor for SailthruClient class with custom API URL
     * @param String apiKey
     * @param String apiSecret
     * @param String apiUrl
     */
    public SailthruClient(String apiKey, String apiSecret, String apiUrl) {
        super(apiKey, apiSecret, apiUrl);
    }

    /**
     * Public constructor for SailthruClient class with default API URL
     * @param String apiKey
     * @param String apiSecret
     */
    public SailthruClient(String apiKey, String apiSecret) {
        super(apiKey, apiSecret, DEFAULT_API_URL);
    }

    /**
     * Synchronized singleton instance method using default URL string
     * @param String apiKey
     * @param String apiSecret
     * @return SailthruClient _instance
     */
    public static synchronized SailthruClient getInstance(String apiKey, String apiSecret) {
        if (_instance == null) {
            _instance = new SailthruClient(apiKey, apiSecret, DEFAULT_API_URL);
        }
        return _instance;
    }

    /**
     * Synchronized singleton instance method using default URL string
     * @param String apiKey
     * @param String apiSecret
     * @param String apiUrl
     * @return SailthruClient _instance
     */
    public static synchronized SailthruClient getInstance(String apiKey, String apiSecret, String apiUrl) {
        if (_instance == null) {
            _instance = new SailthruClient(apiKey, apiSecret, apiUrl);
        }
        return _instance;
    }

    /**
     * Get information about one of your users.
     * @param String email
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/email
     */
    public Map<String, Object> getEmail(String email) throws IOException {
        Email emailObj = new Email();
        emailObj.setEmail(email);
        return (Map<String, Object>)this.apiGet("email", emailObj);
    }

    /**
     * Update information about one of your users, including adding and removing the user from lists.
     * @param Email email
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/email
     */
    public Map<String, Object> setEmail(Email email) throws IOException {
        return (Map<String, Object>)this.apiPost("email", email);
    }

    /**
     * Get the status of a transational send
     * @param String sendId
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/send
     */
    public Map<String, Object> getSend(String sendId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("send_id", sendId);
        return (Map<String, Object>)this.apiGet("send", data);
    }

    /**
     * send an email template to a single email address.
     * @param Send send
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/send
     */
    public Map<String, Object> send(Send send) throws IOException {
        return (Map<String, Object>)this.apiPost("send", send);
    }

    /**
     * send an email template to multiple email addresses
     * @param MultiSend multiSend
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/send
     */
    public Map<String, Object> multiSend(MultiSend multiSend) throws IOException {        
        return (Map<String, Object>)this.apiPost("send", multiSend);
    }

    /**
     * Cancel a send that was scheduled for a future time.
     * @param String sendId
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/send
     */
    public Map<String, Object> cancelSend(String sendId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("send_id", sendId);
        return (Map<String, Object>)this.apiDelete("send", data);
    }

    /**
     * get information about a blast
     * @param Integer blastId
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> getBlast(Integer blastId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blast_id", blastId);
        return (Map<String, Object>)this.apiGet("blast", data);
    }

    /**
     * Schedule a mass mail blast
     * @param blast
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> scheduleBlast(Blast blast) throws IOException {
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Schedule a mass mail from a template
     * @param String template
     * @param String list
     * @param Date scheduleTime
     * @param Blast blast
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> scheduleBlastFromTemplate(String template, String list, Date scheduleTime, Blast blast) throws IOException {
        blast.setCopyTemplate(template);
        blast.setList(list);
        blast.setScheduleTime(scheduleTime);
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Schedule a mass mail from a template
     * @param String template
     * @param String list
     * @param Date scheduleTime
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> scheduleBlastFromTemplate(String template, String list, Date scheduleTime) throws IOException {
        Blast blast = new Blast();
        blast.setCopyTemplate(template);
        blast.setList(list);
        blast.setScheduleTime(scheduleTime);
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Schedule a mass mail blast from previous blast
     * @param Integer blastId
     * @param Date scheduleTime
     * @param Blast blast
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> scheduleBlastFromBlast(Integer blastId, Date scheduleTime, Blast blast) throws IOException {
        blast.setCopyBlast(blastId);
        blast.setScheduleTime(scheduleTime);
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Schedule a mass mail blast from previous blast
     * @param Integer blastId
     * @param Date scheduleTime
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> scheduleBlastFromBlast(Integer blastId, Date scheduleTime) throws IOException {
        Blast blast = new Blast();
        blast.setCopyBlast(blastId);
        blast.setScheduleTime(scheduleTime);
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Update existing blast
     * @param Integer blastId
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> updateBlast(Integer blastId) throws IOException {
        Blast blast = new Blast();
        blast.setBlastId(blastId);
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Update existing blast
     * @param Integer blastId
     * @param Blast blast
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> updateBlast(Integer blastId, Blast blast) throws IOException {
        blast.setBlastId(blastId);
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Delete existing blast
     * @param Integer blastId
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> deleteBlast(Integer blastId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blast_id", blastId);
        return (Map<String, Object>)this.apiDelete("blast", data);
    }

    /**
     * Cancel a scheduled Blast
     * @param Integer blastId
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/blast
     */
    public Map<String, Object> cancelBlast(Integer blastId) throws IOException {
        Blast blast = new Blast();
        Date d = null;
        blast
            .setBlastId(blastId)
            .setScheduleTime(d);
        return (Map<String, Object>)this.apiPost("blast", blast);
    }

    /**
     * Get template information
     * @param String template
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/template
     */
    public Map<String, Object> getTemplate(String template) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("template", template);
        return (Map<String, Object>)this.apiGet("template", data);
    }

    /**
     * Save / update a template
     * @param String template
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/template
     */
    public Map<String, Object> saveTemplate(Template template) throws IOException {
        return (Map<String, Object>)this.apiPost("template", template);
    }

    /**
     * Delete existing template
     * @param String template
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/template
     */
    public Map<String, Object> deleteTemplate(String template) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("template", template);
        return (Map<String, Object>)this.apiDelete("template", data);
    }

    /**
     * Push a new piece of content to Sailthru, triggering any applicable alerts.
     * @param Content content
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/content
     */
    public Map<String, Object> pushContent(Content content) throws IOException {
        return (Map<String, Object>)this.apiPost("content", content);
    }

    /**
     * Retrieve a user's alert settings
     * @param String email
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/alert
     */
    public Map<String, Object> getAlert(String email) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", email);
        return (Map<String, Object>)this.apiGet("alert", data);
    }

    /**
     * Add a new alert to a user. You can add either a realtime or a summary alert (daily/weekly).
     * @param Alert alert
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/alert
     */
    public Map<String, Object> saveAlert(Alert alert) throws IOException {
        return (Map<String, Object>)this.apiPost("alert", alert);
    }

    /**
     * Delete existing user alert
     * @param String email
     * @param String alertId
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/alert
     */
    public Map<String, Object> deleteAlert(String email, String alertId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", email);
        data.put("alert_id", alertId);
        return (Map<String, Object>)this.apiDelete("alert", data);
    }

    /**
     * Record that a user has made a purchase, or has added items to their purchase total
     * @param Purchase purchase
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/purchase
     */
    public Map<String, Object> purchase(Purchase purchase) throws IOException {
        return (Map<String, Object>)this.apiPost("purchase", purchase);
    }

    /**
     * Make stats API request
     * @param Stats stats
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/stats
     */
    protected Map<String, Object> stats(Stats stats) throws IOException {
        return (Map<String, Object>)this.apiGet("stats", stats);
    }

    /**
     * get list stats information
     * @param ListStat stat
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/stats
     */
    public Map<String, Object> listStats(ListStat stat) throws IOException {
        return (Map<String, Object>)this.stats(stat);
    }

    /**
     * get blast stats information
     * @param BlastStat stat
     * @return Map<String, Object>
     * @throws IOException
     * @see http://docs.sailthru.com/api/stats
     */
    public Map<String, Object> blastStats(BlastStat stat) throws IOException {
        return (Map<String, Object>)this.stats(stat);
    }
    
    
    /**
     * Get status of a job
     * @param String jobId
     * @return Map<String, Object>
     * @see http://docs.sailthru.com/api/job
     * @throws IOException 
     */
    public Map<String, Object> getJobStatus(String jobId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("job_id", jobId);
        return (Map<String, Object>)this.apiGet("job", params);
    }
    
    
    /**
     * Process import job from given email string CSV or file path of a CSV or email per line file
     * @param job
     * @return Map<String, Object>
     * @see http://docs.sailthru.com/api/job
     * @throws IOException 
     */
    public Map<String, Object> processImportJob(ImportJob job) throws IOException {
        return (Map<String, Object>)this.apiPost("job", job, job);
    }
    
    
    /**
     * Query user data set and generate a detailed snapshot of their analytics similar to that shown in the Snapshot Report in the Sailthru interface.
     * @param job
     * @return Map<String, Object>
     * @see http://docs.sailthru.com/api/job
     * @throws IOException 
     */
    public Map<String, Object> processSnapshotJob(SnapshotJob job) throws IOException {
        return (Map<String, Object>)this.apiPost("job", job);
    }
    
    
    /**
     * Export blast data in CSV format
     * @param job
     * @return Map<String, Object>
     * @see http://docs.sailthru.com/api/job
     * @throws IOException 
     */
    public Map<String, Object> processBlastQueryJob(BlastQueryJob job) throws IOException {
        return (Map<String, Object>)this.apiPost("job", job);
    }
    
    
    /**
     * Export user data from a list in CSV format
     * @param job
     * @return Map<String, Object>
     * @see http://docs.sailthru.com/api/job
     * @throws IOException 
     */
    public Map<String, Object> processExportListDataJob(ExportListDataJob job) throws IOException {
        return (Map<String, Object>)this.apiPost("job", job);
    }
}