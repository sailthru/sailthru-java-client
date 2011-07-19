package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;
import com.sailthru.client.SailthruUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class MultiSend extends Send {

    protected HashMap<String, Object> evars;

    public MultiSend() {
        this.options = new HashMap<String, Object>();
    }

    public MultiSend setEmails(ArrayList<String> emails) {
        this.email = SailthruUtil.ArrayListToCSV(emails);        
        return this;
    }

    public MultiSend setEvars(HashMap<String, Object> evars) {
        this.evars = evars;
        return this;
    }

    @Override
    public Type getType() {
         Type type = new TypeToken<MultiSend>() {}.getType();
        return type;
    }
}