package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class MultiSend extends Send {

    protected Map<String, Object> evars;
    protected static final Type type = new TypeToken<MultiSend>() {}.getType();

    public MultiSend() {
        this.options = new HashMap<String, Object>();
    }

    public MultiSend setEmails(java.util.List<String> emails) {
        this.email = SailthruUtil.arrayListToCSV(emails);        
        return this;
    }

    public MultiSend setEvars(Map<String, Object> evars) {
        this.evars = evars;
        return this;
    }

    @Override
    public Type getType() {
        return MultiSend.type;
    }
}
