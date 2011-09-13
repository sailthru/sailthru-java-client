package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class MultiSend extends Send {

    protected Map<String, Object> evars;

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
         Type type = new TypeToken<MultiSend>() {}.getType();
        return type;
    }
}
