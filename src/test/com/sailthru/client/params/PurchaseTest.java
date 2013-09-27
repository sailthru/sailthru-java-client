package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: dennisyu
 * Date: 9/27/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class PurchaseTest extends TestCase {
    Gson gson = SailthruUtil.createGson();

    public void testSetPurchaseLevelVars() {
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("baz", "foo");

        Purchase purchase = new Purchase();
        purchase.setPurchaseLevelVars(vars);

        String expected = "{\"vars\":{\"baz\":\"foo\"}}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testAdjustments() {
        Map<String, Object> adjustmentItem = new HashMap<String, Object>();
        adjustmentItem.put("title", "bar");
        adjustmentItem.put("price", 1000);
        java.util.ArrayList adjustments = new ArrayList();
        adjustments.add(adjustmentItem);

        Purchase purchase = new Purchase();
        purchase.setAdjustments(adjustments);

        String expected = "{\"adjustments\":[{\"title\":\"bar\",\"price\":1000}]}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testTenders() {
        Map<String, Object> TenderItem = new HashMap<String, Object>();
        TenderItem.put("title", "bar");
        TenderItem.put("price", 1000);
        java.util.ArrayList tenders = new ArrayList();
        tenders.add(TenderItem);

        Purchase purchase = new Purchase();
        purchase.setTenders(tenders);

        String expected = "{\"tenders\":[{\"title\":\"bar\",\"price\":1000}]}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSendTemplate() {
        String sendTemplate = "template name";

        Purchase purchase = new Purchase();
        purchase.setSendTemplate(sendTemplate);

        String expected = "{\"send_template\":\"template name\"}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }
}
