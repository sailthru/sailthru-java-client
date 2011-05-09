package com.sailthru.client;

import com.sailthru.client.params.Alert;
import com.sailthru.client.params.Blast;
import com.sailthru.client.params.BlastStat;
import com.sailthru.client.params.Content;
import com.sailthru.client.params.Email;
import com.sailthru.client.params.ListStat;
import com.sailthru.client.params.MultiSend;
import com.sailthru.client.params.Purchase;
import com.sailthru.client.params.Send;
import com.sailthru.client.params.Stats;
import com.sailthru.client.params.Template;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar
 */
public class SailthruClient extends AbstractSailthruClient {

    private static SailthruClient _instance;

    private SailthruClient(String apiKey, String apiSecret, String apiUrl) {
        super(apiKey, apiSecret, apiUrl);
    }

    public static synchronized SailthruClient getInstance(String apiKey, String apiSecret) {
        if (_instance == null) {
            _instance = new SailthruClient(apiKey, apiSecret, DEFAULT_API_URL);
        }
        return _instance;
    }

    public static synchronized SailthruClient getInstance(String apiKey, String apiSecret, String apiUrl) {
        if (_instance == null) {
            _instance = new SailthruClient(apiKey, apiSecret, apiUrl);
        }
        return _instance;
    }

    public HashMap<String, Object> getEmail(String email) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", email);
        return (HashMap<String, Object>)this.apiGet("email", data);
    }

    public HashMap<String, Object> setEmail(Email email) throws IOException {
        HashMap<String, Object> data = email.toHashMap();
        return (HashMap<String, Object>)this.apiPost("email", data);
    }

    public HashMap<String, Object> getSend(String sendId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("send_id", sendId);
        return (HashMap<String, Object>)this.apiGet("send", data);
    }

    public HashMap<String, Object> send(Send send) throws IOException {
        HashMap<String, Object> data = send.toHashMap();
        return (HashMap<String, Object>)this.apiPost("send", data);
    }

    public HashMap<String, Object> multiSend(MultiSend multiSend) throws IOException {
        HashMap<String, Object> data = multiSend.toHashMap();
        return (HashMap<String, Object>)this.apiPost("send", data);
    }

    public HashMap<String, Object> cancelSend(String sendId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("send_id", sendId);
        return (HashMap<String, Object>)this.apiDelete("send", data);
    }

    public HashMap<String, Object> getBlast(Integer blastId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blast_id", blastId);
        return (HashMap<String, Object>)this.apiGet("blast", data);
    }

    public HashMap<String, Object> scheduleBlast(Blast blast) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        return (HashMap<String, Object>)this.apiPost("blast", data);
    }

    public HashMap<String, Object> scheduleBlastFromTemplate(String template, String list, Date scheduleTime, HashMap<String, Object> options) throws IOException {
        Map<String, Object> data = options;
        data.put("copy_template", template);
        data.put("list", list);
        data.put("schedule_time", scheduleTime);
        return (HashMap<String, Object>)this.apiPost("blast", data);
    }

    public HashMap<String, Object> scheduleBlastFromBlast(Integer blastId, Date scheduleTime, HashMap<String, Object> options) throws IOException {
        Map<String, Object> data = options;
        data.put("copy_blast", blastId);
        data.put("schedule_time", scheduleTime);
        return (HashMap<String, Object>)this.apiPost("blast", data);
    }

    public HashMap<String, Object> updateBlast(Integer blastId, Blast blast) throws IOException {
        Map<String, Object> data = blast.toHashMap();
        data.put("blast_id", blastId);
        return (HashMap<String, Object>)this.apiPost("blast", data);
    }

    public HashMap<String, Object> deleteBlast(Integer blastId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blast", blastId);
        return (HashMap<String, Object>)this.apiDelete("blast", data);
    }

    public HashMap<String, Object> cancelBlast(Integer blastId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("blast_id", blastId);
        data.put("schedule_time", null);
        return (HashMap<String, Object>)this.apiPost("blast", data);
    }

    public HashMap<String, Object> getTemplate(String template) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("template", template);
        return (HashMap<String, Object>)this.apiGet("template", data);
    }

    public HashMap<String, Object> saveTemplate(Template template) throws IOException {
        Map<String, Object> data = template.toHashMap();
        return (HashMap<String, Object>)this.apiPost("template", data);
    }

    public HashMap<String, Object> deleteTemplate(String template) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("template", template);
        return (HashMap<String, Object>)this.apiDelete("template", data);
    }

    public HashMap<String, Object> pushContent(Content content) throws IOException {
        Map<String, Object> data = content.toHashMap();
        return (HashMap<String, Object>)this.apiPost("content", data);
    }

    public HashMap<String, Object> getAlert(String email) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", email);
        return (HashMap<String, Object>)this.apiGet("alert", data);
    }

    public HashMap<String, Object> saveAlert(Alert alert) throws IOException {
        Map<String, Object> data = alert.toHashMap();
        return (HashMap<String, Object>)this.apiPost("alert", data);
    }

    public HashMap<String, Object> deleteAlert(String email, String alertId) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", email);
        data.put("alert_id", alertId);
        return (HashMap<String, Object>)this.apiDelete("alert", data);
    }

    public HashMap<String, Object> purchase(Purchase purchase) throws IOException {
        return (HashMap<String, Object>)this.httpRequest("purchase", AbstractSailthruClient.HttpRequestMethod.POST, purchase);
    }

    protected HashMap<String, Object> stats(Stats stats) throws IOException {
        return (HashMap<String, Object>)this.apiGet("stats", stats);
    }

    public HashMap<String, Object> listStats(ListStat stat) throws IOException {
        return (HashMap<String, Object>)this.stats(stat);
    }

    public HashMap<String, Object> blastStats(BlastStat stat) throws IOException {
        return (HashMap<String, Object>)this.stats(stat);
    }
}