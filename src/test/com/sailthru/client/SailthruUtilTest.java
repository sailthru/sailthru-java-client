package com.sailthru.client;

import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com> <praj@sailthru.com>
 */
public class SailthruUtilTest extends TestCase {
    protected static Logger log = LoggerFactory.getLogger(SailthruUtilTest.class);
    protected Gson gson;
    public void testMd5() {
        String plainText1 = "hello_world";
        String hash1 = SailthruUtil.md5(plainText1);
        log.debug(hash1);
        String expectedHash1 = "99b1ff8f11781541f7f89f9bd41c4a17";
        assertEquals(hash1, expectedHash1);
    }
    
    public void testArrayListToCSV() {
        java.util.List<String> list1 = new ArrayList<String>();
        list1.add("windows");
        list1.add("linux");
        list1.add("BSD");
        String expectedList1 = "windows,linux,BSD";
        assertEquals(expectedList1, SailthruUtil.arrayListToCSV(list1));
        
        java.util.List<String> list2 = new ArrayList<String>();
        list2.add("one_item");
        String expectedList2 = "one_item";
        assertEquals(expectedList2, SailthruUtil.arrayListToCSV(list2));
    }

    public void testGson() {
        gson = SailthruUtil.createGson();
        java.util.Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("var1","value1");
        map1.put("var2","value2");
        String expectedmap1 = "{\"var1\":\"value1\",\"var2\":\"value2\"}";
        assertEquals(expectedmap1, gson.toJson(map1));

        java.util.Map<String, Object> map2 = new HashMap<String, Object>();
        String expectedmap2 = "{}";
        assertEquals(expectedmap2, gson.toJson(map2));

        java.util.Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("var1",null);
        map3.put("var2","value2");
        String expectedmap3 = "{\"var2\":\"value2\"}";
        assertEquals(expectedmap3, gson.toJson(map3));

        java.util.Map<String, Object> map4 = new HashMap<String, Object>();
        java.util.Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("var1",null);
        map4.put("var1",map5);
        java.util.Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("var2","value2");
        map4.put("var2",map6);
        String expectedmap4 = "{\"var1\":{},\"var2\":{\"var2\":\"value2\"}}";
        assertEquals(expectedmap4, gson.toJson(map4));
    }
}
