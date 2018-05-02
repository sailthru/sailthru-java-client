package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.TimeZone;

import java.text.*;

public class PurchaseTest extends TestCase {
    Gson gson = SailthruUtil.createGson();

    DateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");

    public void testSetEmail() {
        Purchase purchase = new Purchase();
        purchase.setEmail("support@sailthru.com");
        String expected = "{\"email\":\"support@sailthru.com\"}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetItems() {
        List<PurchaseItem> items = new ArrayList<PurchaseItem>();
        PurchaseItem firstpurchaseitem = new PurchaseItem(1, "example purchase item", 1999, "example id", "http://www.sailthru.com/example/purchase/url");
        PurchaseItem secondpurchaseitem = new PurchaseItem(2, "second purchase item", 2050, "example id2", "http://www.sailthru.com/2/example/purchase/url");
        items.add(firstpurchaseitem);
        items.add(secondpurchaseitem);
        Purchase purchase = new Purchase();
        purchase.setItems(items);
        String expected = "{\"items\":[{\"qty\":\"1\",\"title\":\"example purchase item\",\"price\":\"1999\",\"id\":\"example id\",\"url\":\"http://www.sailthru.com/example/purchase/url\"},{\"qty\":\"2\",\"title\":\"second purchase item\",\"price\":\"2050\",\"id\":\"example id2\",\"url\":\"http://www.sailthru.com/2/example/purchase/url\"}]}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetPurchaseLevelVars() {
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("baz", "foo");
        Purchase purchase = new Purchase();
        purchase.setPurchaseLevelVars(vars);
        String expected = "{\"vars\":{\"baz\":\"foo\"}}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetPurchaseKeys() {
        Map<String, String> purchaseKeys = new HashMap<String, String>();
        purchaseKeys.put("extid", "123");
        Purchase purchase = new Purchase();
        purchase.setPurchaseKeys(purchaseKeys);
        String expected = "{\"purchase_keys\":{\"extid\":\"123\"}}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testAdjustments() {
        Map<String, Object> adjustmentItem = new HashMap<String, Object>();
        adjustmentItem.put("title", "bar");
        adjustmentItem.put("price", 1000);
        ArrayList adjustments = new ArrayList();
        adjustments.add(adjustmentItem);
        Purchase purchase = new Purchase();
        purchase.setAdjustments(adjustments);
        String expected = "{\"adjustments\":[{\"price\":1000,\"title\":\"bar\"}]}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testTenders() {
        Map<String, Object> TenderItem = new HashMap<String, Object>();
        TenderItem.put("title", "bar");
        TenderItem.put("price", 1000);
        ArrayList tenders = new ArrayList();
        tenders.add(TenderItem);
        Purchase purchase = new Purchase();
        purchase.setTenders(tenders);
        String expected = "{\"tenders\":[{\"price\":1000,\"title\":\"bar\"}]}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetAsIncomplete() {
        Purchase purchase = new Purchase();
        purchase.setAsIncomplete();
        String expected = "{\"incomplete\":1}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetMessageId() {
        Purchase purchase = new Purchase();
        purchase.setMessageId("example message id");
        String expected = "{\"message_id\":\"example message id\"}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetReminderTemplate() {
        Purchase purchase = new Purchase();
        purchase.setReminderTemplate("example reminder template");
        String expected = "{\"reminder_template\":\"example reminder template\"}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetReminderTimeString() {
        Purchase purchase = new Purchase();
        purchase.setReminderTime("+10 days");
        String expected = "{\"reminder_time\":\"+10 days\"}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetReminderTimeDate() {
        Purchase purchase = new Purchase();
        Date date = new Date(1380831494000L);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        purchase.setReminderTime(format.format(date));
        String expected = "{\"reminder_time\":\"Thu Oct 03 20:18:14 UTC 2013\"}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetDateString() {
        Purchase purchase = new Purchase();
        purchase.setDate("+10 days");
        String expected = "{\"date\":\"+10 days\"}";
        String result = gson.toJson(purchase);
        assertEquals(expected, result);
    }

    public void testSetDateDate() {
        Purchase purchase = new Purchase();
        Date date = new Date(1380831494000L);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        purchase.setDate(format.format(date));
        String expected = "{\"date\":\"Thu Oct 03 20:18:14 UTC 2013\"}";
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
