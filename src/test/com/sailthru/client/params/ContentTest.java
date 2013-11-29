package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import junit.framework.TestCase;

import java.util.*;
import java.util.List;

public class ContentTest extends TestCase {
    Gson gson = SailthruUtil.createGson();

    public void testGetContent() {
        Content content = new Content();
        Date date = new Date('1385553600');
        content.setDate(date);
        content.setTitle('testGetContent Title');
        content.setUrl('http://sailthru.com');
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("baz", "foo");
        content.setVars(vars);
        String expected = "{\"date\" : new Date(\'Wed, 27 Nov 2013 12:00:00 GMT\'),\"tags\" : [\"baz\":\"foo\"],\"title\" : \"testGetContent Title\",\"url\" : \"http://sailthru.com\"}";
        String result = gson.toJson(content);
        assertEquals(expected, result);
    }



}
