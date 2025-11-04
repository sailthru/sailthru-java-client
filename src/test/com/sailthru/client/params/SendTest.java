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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.*;

class SendTest {
    private Gson gson = SailthruUtil.createGson();
    private Send send = new Send();
    private static final Date THURSDAY_OCT_3 = new Date(1380831494000L);

    DateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");

    @Test void setSendID() {
        send.setSendId("abcdefghijkl");

        String expected = "{\"send_id\":\"abcdefghijkl\",\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setEmail() {
        send.setEmail("support@sailthru.com");

        String expected = "{\"email\":\"support@sailthru.com\",\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setTemplate() {
        send.setTemplate("example template");

        String expected = "{\"template\":\"example template\",\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setReplyTo() {
        send.setReplyTo("support@sailthru.com");

        String expected = "{\"options\":{\"replyto\":\"support@sailthru.com\"}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setIsTest() {
        send.setIsTest();

        String expected = "{\"options\":{\"test\":1}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setVars() {
        Map<String, Object> vars = new LinkedHashMap<>();
        vars.put("foo", "bar");
        Map<String, Object> examplemap = new LinkedHashMap<>();
        examplemap.put("nullvalue", null);
        vars.put("example map", examplemap);
        send.setVars(vars);

        String expected = "{\"vars\":{\"foo\":\"bar\",\"example map\":{\"nullvalue\":null}},\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setLimitString() {
        send.setLimit("limit name here");

        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name here\"}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setLimitStringString() {
        send.setLimit("limit name here", "within time here");

        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name here\",\"within_time\":\"within time here\"}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setLimitStringStringString() {
        send.setLimit("limit name", "within time", "update");

        String result = gson.toJson(send);
        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name\",\"within_time\":\"within time\",\"conflict\":\"update\"}}";
        assertThat(result).isEqualTo(expected);
    }

    @Test void setLimitMap() {
        Map<String, Object> limit = new LinkedHashMap<>();
        limit.put("name", "limit name");
        limit.put("conflict", "update");
        limit.put("within_time", "some amount of time");
        send.setLimit(limit);

        String expected = "{\"options\":{},\"limit\":{\"name\":\"limit name\",\"conflict\":\"update\",\"within_time\":\"some amount of time\"}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setScheduleTimeDate() {
        Date date = new Date(1380831494000L);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        send.setScheduleTime(format.format(date));

        String expected = "{\"schedule_time\":\"Thu Oct 03 20:18:14 UTC 2013\",\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setScheduleTimeString() {
        String date = "+ 1 hour";
        send.setScheduleTime(date);

        String expected = "{\"schedule_time\":\"+ 1 hour\",\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setScheduleTimeMap() {
        Map<String, Object> date = new LinkedHashMap<>();
        date.put("start_time", "+1 hour");
        date.put("end_time", "+10 hours");
        date.put("method","email");
        send.setScheduleTime(date);

        String expected = "{\"schedule_time\":{\"start_time\":\"+1 hour\",\"end_time\":\"+10 hours\",\"method\":\"email\"},\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setScheduleTimeObjectObjectString() {
        send.setScheduleTime("+1 hour","+ 5 hours", "email");

        String expected = "{\"schedule_time\":{\"start_time\":\"+1 hour\",\"end_time\":\"+ 5 hours\",\"method\":\"email\"},\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setScheduleTimeObjectObject() {
        send.setScheduleTime("+1 hour", "+5 hours");

        String expected = "{\"schedule_time\":{\"start_time\":\"+1 hour\",\"end_time\":\"+5 hours\"},\"options\":{}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setBehalfEmail() {
        send.setBehalfEmail("support@sailthru.com");

        String expected = "{\"options\":{\"behalf_email\":\"support@sailthru.com\"}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put("Cc", "support@sailthru.com");
        send.setOptions(options);

        String expected = "{\"options\":{\"Cc\":\"support@sailthru.com\"}}";
        String result = gson.toJson(send);
        assertThat(result).isEqualTo(expected);
    }
}
