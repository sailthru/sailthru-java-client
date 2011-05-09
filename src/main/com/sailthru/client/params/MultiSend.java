package com.sailthru.client.params;

import com.sailthru.client.SailthruUtil;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class MultiSend extends AbstractApiParams {
    protected String email;
    protected String template;
    protected HashMap<String, Object> vars;
    protected HashMap<String, Object> evars;
    protected HashMap<String, Object> options;

    public MultiSend() {
        this.options = new HashMap<String, Object>();
    }

    public MultiSend setEmails(ArrayList<String> emails) {
        this.email = SailthruUtil.ArrayListToCSV(emails);        
        return this;
    }

    public MultiSend setTemplate(String template) {
        this.template = template;
        return this;
    }

    public MultiSend setVars(HashMap<String, Object> vars) {
        this.vars = vars;
        return this;
    }

    public MultiSend setEvars(HashMap<String, Object> evars) {
        this.evars = evars;
        return this;
    }

    public MultiSend setReplyTo(String replyTo) {
        this.options.put("replyTo", replyTo);
        return this;
    }

    public MultiSend setIsTest() {
        this.options.put("test", 1);
        return this;
    }
}