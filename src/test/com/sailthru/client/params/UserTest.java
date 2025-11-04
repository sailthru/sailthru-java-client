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
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author ianwhite
 */
class UserTest {
    private Gson gson = SailthruUtil.createGson();
    private User user = new User();

    @Test void serializationNull() {
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("baz", null);

        user.setVars(vars);
        
        String expected = "{\"vars\":{\"baz\":null}}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setId() {
        User user = new User("foo@bar.com");

        String expected = "{\"id\":\"foo@bar.com\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setKey() {
        user.setKey("email");

        String expected = "{\"key\":\"email\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setFields() {
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("keys", 1);
        user.setFields(fields);

        String expected = "{\"fields\":{\"keys\":1}}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setKeys() {
        Map<String, String> keys = new HashMap<String, String>();
        keys.put("email", "foo@bar.com");
        user.setKeys(keys);

        String expected = "{\"keys\":{\"email\":\"foo@bar.com\"}}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setKeysConflict() {
        user.setKeysConflict("error");

        String expected = "{\"keysconflict\":\"error\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setLists() {
        Map<String, Integer> lists = new HashMap<String, Integer>();
        lists.put("test list", 1);
        user.setLists(lists);

        String expected = "{\"lists\":{\"test list\":1}}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setOptoutEmail() {
        user.setOptoutEmail("none");

        String expected = "{\"optout_email\":\"none\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setOptoutSmsStatus() {
        user.setOptoutSmsStatus("opt-in");

        String expected = "{\"optout_sms_status\":\"opt-in\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsMarketingStatusTypeStart() {
        user.setSmsMarketingStatus(User.OptOutType.OPT_OUT_TYPE_START);

        String expected = "{\"sms_marketing_status\":\"opt-in\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsTransactionalStatusTypeStart() {
        user.setSmsTransactionalStatus(User.OptOutType.OPT_OUT_TYPE_START);

        String expected = "{\"sms_transactional_status\":\"opt-in\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsMarketingStatusTypeStop() {
        user.setSmsMarketingStatus(User.OptOutType.OPT_OUT_TYPE_STOP);

        String expected = "{\"sms_marketing_status\":\"opt-out\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsTransactionalStatusTypeStop() {
        user.setSmsTransactionalStatus(User.OptOutType.OPT_OUT_TYPE_STOP);

        String expected = "{\"sms_transactional_status\":\"opt-out\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsMarketingStatusTypePending() {
        user.setSmsMarketingStatus(User.OptOutType.OPT_OUT_TYPE_PENDING);

        String expected = "{\"sms_marketing_status\":\"pending\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsTransactionalStatusTypePending() {
        user.setSmsTransactionalStatus(User.OptOutType.OPT_OUT_TYPE_PENDING);

        String expected = "{\"sms_transactional_status\":\"pending\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsMarketingStatusTypeDoubleOptIn() {
        user.setSmsMarketingStatus(User.OptOutType.OPT_OUT_TYPE_DOUBLE_OPT_IN);

        String expected = "{\"sms_marketing_status\":\"double-opt-in\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setSmsTransactionalStatusTypeDoubleOptIn() {
        user.setSmsTransactionalStatus(User.OptOutType.OPT_OUT_TYPE_DOUBLE_OPT_IN);

        String expected = "{\"sms_transactional_status\":\"double-opt-in\"}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }

    @Test void setLogin() {
        Map<String, Object> login = new HashMap<String, Object>();
        login.put("ip", "123.456.789.0");
        user.setLogin(login);

        String expected = "{\"login\":{\"ip\":\"123.456.789.0\"}}";
        String result = gson.toJson(user);
        assertThat(result).isEqualTo(expected);
    }
}
