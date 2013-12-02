package com.sailthru.client;

import junit.framework.TestCase;
import org.apache.http.params.HttpConnectionParams;

/**
 * author: matt <matt@sailthru.com>
 */
public class SailthruClientTest extends TestCase {
    private static final String FAKE_API_KEY = "api_key";
    private static final String FAKE_SECRET = "secret";
    private static final String FAKE_API_URI = "http://api.sailthru-test.com";
    private static final int DEFAULT_TIMEOUT = 60 * 1000;

    public void testDefaultTimeout() {
        SailthruClient sailthruClient = new SailthruClient(FAKE_API_KEY, FAKE_SECRET, FAKE_API_URI);
        assertEquals(DEFAULT_TIMEOUT, HttpConnectionParams.getConnectionTimeout(sailthruClient.getHttpParams()));
        assertEquals(DEFAULT_TIMEOUT, HttpConnectionParams.getSoTimeout(sailthruClient.getHttpParams()));
    }

    public void testConfigureTimeout() {
        int connTimeout = 30 * 1000;
        int soTimeout = 5 * 1000;
        SailthruClient sailthruClient = new SailthruClient(FAKE_API_KEY, FAKE_SECRET, FAKE_API_URI, connTimeout, soTimeout);
        assertEquals(connTimeout, HttpConnectionParams.getConnectionTimeout(sailthruClient.getHttpParams()));
        assertEquals(soTimeout, HttpConnectionParams.getSoTimeout(sailthruClient.getHttpParams()));
    }
}
