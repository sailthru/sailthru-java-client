package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import junit.framework.TestCase;

import java.util.*;
import java.util.List;

import java.text.*;

public class ContentTest extends TestCase {
    Gson gson = SailthruUtil.createGson();

    DateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");

    public void testGetContent() {
        Content content = new Content();
        Date date = new Date(1380831494000L);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        content.setDate(format.format(date));
        content.setTitle("testGetContent Title");
        content.setUrl("http://sailthru.com");
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("baz", "foo");
        content.setVars(vars);
        String expected = "{\"title\":\"testGetContent Title\",\"url\":\"http://sailthru.com\",\"date\":\"Thu Oct 03 20:18:14 UTC 2013\",\"vars\":{\"baz\":\"foo\"}}";
        String result = gson.toJson(content);
        assertEquals(expected, result);
    }

    public void testSetTitle(){
        Content content = new Content();
        String title = "Test Title";
        content.setTitle(title);
        String expected = "{\"title\":\"Test Title\"}";
        String result = gson.toJson(content);
        assertEquals(expected,result);
    }


    public void testSetUrl(){
        Content content = new Content();
        String url = "http://sailthru.com";
        content.setUrl(url);
        String expected = "{\"url\":\"http://sailthru.com\"}";
        String result = gson.toJson(content);
        assertEquals(expected,result);
    }

    public void testSetDateFormat(){
        Content content = new Content();
        Date date = new Date(1380831494000L);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        content.setDate(format.format(date));
        String expected = "{\"date\":\"Thu Oct 03 20:18:14 UTC 2013\"}";
        String result = gson.toJson(content);
        assertEquals(expected,result);
    }

    public void testSetDateString(){
        Content content = new Content();
        String date = "1380831494000L";
        content.setDate(date);
        String expected = "{\"date\":\"1380831494000L\"}";
        String result = gson.toJson(content);
        assertEquals(expected,result);
    }

    public void testSetVars(){
        Content content = new Content();
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("test","result");
        vars.put("test2","result2");
        vars.put("test3","result3");
        content.setVars(vars);
        String expected = "{\"vars\":{\"test\":\"result\",\"test2\":\"result2\",\"test3\":\"result3\"}}";
        String result = gson.toJson(content);
        assertEquals(expected,result);
    }


}
