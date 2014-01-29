package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import com.sailthru.client.ApiAction;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class Purchase extends AbstractApiParams implements ApiParams {
    protected String email;
    protected List<Map<String, Object>> items;
    protected Integer incomplete;

    @SerializedName("message_id")
    protected String messageId;

    @SerializedName("reminder_template")
    protected String reminderTemplate;

    @SerializedName("reminder_time")
    protected String reminderTime;

    @SerializedName("send_template")
    protected String sendTemplate;

    protected String date;

    protected List<Map<String, Object>> tenders;
    protected List<Map<String, Object>> adjustments;
    protected Map<String, Object> vars;

    public Purchase setEmail(String email) {
        this.email = email;
        return this;
    }

    public Purchase setItems(List<PurchaseItem> items) {
        this.items = new ArrayList<Map<String, Object>>();
        for (PurchaseItem item : items) {
            this.items.add(item.toHashMap());
        }
        return this;
    }

    public Purchase setTenders(List<Map<String, Object>> tenders) {
        this.tenders = tenders;
        return this;
    }

    public Purchase setAdjustments(List<Map<String, Object>> adjustments) {
        this.adjustments = adjustments;
        return this;
    }

    public Purchase setAsIncomplete() {
        this.incomplete = 1;
        return this;
    }

    public Purchase setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public Purchase setReminderTemplate(String reminderTemplate) {
        this.reminderTemplate =  reminderTemplate;
        return this;
    }

    public Purchase setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime.toString();
        return this;
    }

    public Purchase setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
        return this;
    }

    public Purchase setDate(Date date) {
        this.date = date.toString();
        return this;
    }

    public Purchase setDate(String date) {
        this.date = date;
        return this;
    }

    public Purchase setSendTemplate(String sendTemplate) {
        this.sendTemplate = sendTemplate;
        return this;
    }

    public Purchase setPurchaseLevelVars(Map<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<Purchase>() {}.getType();
        return type;
    }

    @Override
    public ApiAction getApiCall() {
        return ApiAction.purchase;
    }
}
