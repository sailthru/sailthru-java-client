/*
 * The MIT License
 *
 * Copyright 2013 Sailthru, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;


/**
 * Created with IntelliJ IDEA.
 * User: dennisyu
 * Date: 10/9/13
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendTest extends TestCase {
    private Gson gson = SailthruUtil.createGson();
    private Send send = new Send();

    public void testSetSendID(){
        send.setSendId("abcdefghijkl");

        String expected = "{\"send_id\":\"abcdefghijkl\",\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetEmail(){
        send.setEmail("support@sailthru.com");

        String expected = "{\"email\":\"support@sailthru.com\",\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetTemplate(){
        send.setTemplate("example template");

        String expected = "{\"template\":\"example template\",\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetReplyTo(){
        send.setReplyTo("support@sailthru.com");

        String expected = "{\"options\":{\"replyto\":\"support@sailthru.com\"}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetIsTest(){
        send.setIsTest();

        String expected = "{\"options\":{\"test\":1}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetVars(){
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("foo", "bar");
        Map<String, Object> examplemap = new HashMap<String, Object>();
        examplemap.put("nullvalue", null);
        vars.put("example map", examplemap);
        send.setVars(vars);

        String expected = "{\"vars\":{\"example map\":{\"nullvalue\":null},\"foo\":\"bar\"},\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetLimitString(){
        send.setLimit("limit name here");

        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name here\"}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetLimitStringString(){
        send.setLimit("limit name here", "within time here");

        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name here\",\"within_time\":\"within time here\"}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetLimitStringStringString(){
        send.setLimit("limit name", "within time", "update");

        String result = gson.toJson(send);
        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name\",\"conflict\":\"update\",\"within_time\":\"within time\"}}";
        assertEquals(expected, result);
    }

    public void testSetLimitMap(){
        Map<String, Object> limit = new HashMap<String, Object>();
        limit.put("name", "limit name");
        limit.put("within_time", "some amount of time");
        limit.put("conflict", "update");
        send.setLimit(limit);

        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name\",\"conflict\":\"update\",\"within_time\":\"some amount of time\"}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetScheduleTimeDate(){
        Date date = new Date(1380831494000L);
        send.setScheduleTime(date);

        String expected = "{\"schedule_time\":\"Thu Oct 03 16:18:14 EDT 2013\",\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetScheduleTimeString(){
        String date = "+ 1 hour";
        send.setScheduleTime(date);

        String expected = "{\"schedule_time\":\"+ 1 hour\",\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetScheduleTimeMap(){
        Map<String, Object> date = new HashMap<String, Object>();
        date.put("start_time", "+1 hour");
        date.put("end_time", "+10 hours");
        date.put("method","email");
        send.setScheduleTime(date);

        String expected = "{\"schedule_time\":{\"end_time\":\"+10 hours\",\"start_time\":\"+1 hour\",\"method\":\"email\"},\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetScheduleTimeObjectObjectString(){
        send.setScheduleTime("+1 hour","+ 5 hours", "email");

        String expected = "{\"schedule_time\":{\"end_time\":\"+ 5 hours\",\"start_time\":\"+1 hour\",\"method\":\"email\"},\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetScheduleTimeObjectObject(){
        send.setScheduleTime("+1 hour", "+5 hours");

        String expected = "{\"schedule_time\":{\"end_time\":\"+5 hours\",\"start_time\":\"+1 hour\"},\"options\":{}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetBehalfEmail(){
        send.setBehalfEmail("support@sailthru.com");

        String expected = "{\"options\":{\"behalf_email\":\"support@sailthru.com\"}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }

    public void testSetOptions(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("Cc", "support@sailthru.com");
        send.setOptions(options);

        String expected = "{\"options\":{\"Cc\":\"support@sailthru.com\"}}";
        String result = gson.toJson(send);
        assertEquals(expected, result);
    }
}
