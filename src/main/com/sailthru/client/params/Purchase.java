package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Purchase extends AbstractApiParams implements ApiParams {
    protected String email;
    protected ArrayList<HashMap<String, String>> items;
    protected Integer incomplete;
    protected String message_id;
    protected String reminder_template;
    protected Date reminder_time;

    public Purchase setEmail(String email) {
        this.email = email;
        return this;
    }

    public Purchase setItems(ArrayList<PurchaseItem> items) {
        this.items = new ArrayList<HashMap<String, String>>();
        for (PurchaseItem item : items) {
            this.items.add(item.toHashMap());
        }
        return this;
    }

    public Purchase setAsIncomplete() {
        this.incomplete = 1;
        return this;
    }

    public Purchase setMessageId(String messageId) {
        this.message_id = messageId;
        return this;
    }

    public Purchase setReminderTemplate(String reminderTemplate) {
        this.reminder_template =  reminderTemplate;
        return this;
    }

    public Purchase setReminderTime(Date reminderTime) {
        this.reminder_time = reminderTime;
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<Purchase>() {}.getType();
        return type;
    }
}