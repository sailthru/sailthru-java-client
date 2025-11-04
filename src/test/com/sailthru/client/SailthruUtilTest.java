package com.sailthru.client;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com> <praj@sailthru.com>
 */
class SailthruUtilTest {
    protected static Logger log = LoggerFactory.getLogger(SailthruUtilTest.class);
    protected Gson gson;

    @Test void md5() {
        String plainText1 = "hello_world";
        String hash1 = SailthruUtil.md5(plainText1);
        log.debug(hash1);
        String expectedHash1 = "99b1ff8f11781541f7f89f9bd41c4a17";
        assertThat(expectedHash1).isEqualTo(hash1);
    }

    @Test void arrayListToCSV() {
        java.util.List<String> list1 = new ArrayList<String>();
        list1.add("windows");
        list1.add("linux");
        list1.add("BSD");
        String expectedList1 = "windows,linux,BSD";
        assertThat(SailthruUtil.arrayListToCSV(list1)).isEqualTo(expectedList1);
        
        java.util.List<String> list2 = new ArrayList<String>();
        list2.add("one_item");
        String expectedList2 = "one_item";
        assertThat(SailthruUtil.arrayListToCSV(list2)).isEqualTo(expectedList2);
    }

    @Test void gson() {
        gson = SailthruUtil.createGson();
        java.util.Map<String, Object> map1 = new LinkedHashMap<String, Object>();
        map1.put("var1","value1");
        map1.put("var2","value2");
        String expectedmap1 = "{\"var1\":\"value1\",\"var2\":\"value2\"}";
        assertThat(gson.toJson(map1)).isEqualTo(expectedmap1);

        java.util.Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        String expectedmap2 = "{}";
        assertThat(gson.toJson(map2)).isEqualTo(expectedmap2);

        java.util.Map<String, Object> map3 = new LinkedHashMap<String, Object>();
        map3.put("var1",null);
        map3.put("var2","value2");
        String expectedmap3 = "{\"var1\":null,\"var2\":\"value2\"}";
        assertThat(gson.toJson(map3)).isEqualTo(expectedmap3);

        java.util.Map<String, Object> map4 = new LinkedHashMap<String, Object>();
        java.util.Map<String, Object> map5 = new LinkedHashMap<String, Object>();
        map5.put("var1",null);
        map4.put("var1",map5);
        java.util.Map<String, Object> map6 = new LinkedHashMap<String, Object>();
        map6.put("var2","value2");
        map4.put("var2",map6);
        String expectedmap4 = "{\"var1\":{\"var1\":null},\"var2\":{\"var2\":\"value2\"}}";
        assertThat(gson.toJson(map4)).isEqualTo(expectedmap4);
    }

    @Test void gsonNull() {
        gson = SailthruUtil.createGson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("baz", null);
        
        String expected = "{\"baz\":null}";
        String result = gson.toJson(map);

        assertThat(result).isEqualTo(expected);
    }

    @Test void imagesMapIsUpdated() {
        Map<String, Map<String, String>> map = SailthruUtil.putImage(null, "full", "https://something/full.jpg");
        assertThat(map.size()).isEqualTo(1);
        assertThat(map.get("full").get("url")).isEqualTo("https://something/full.jpg");

        map = SailthruUtil.putImage(map, "thumb", "https://something/thumb.jpg");
        assertThat(map.size()).isEqualTo(2);
        assertThat(map.get("thumb").get("url")).isEqualTo("https://something/thumb.jpg");

        map = SailthruUtil.putImage(map, "custom", "https://something/custom.jpg");
        assertThat(map.size()).isEqualTo(3);
        assertThat(map.get("custom").get("url")).isEqualTo("https://something/custom.jpg");

        map = SailthruUtil.putImage(map, "thumb", "https://something/anotherthumb.jpg");
        assertThat(map.size()).isEqualTo(3);
        assertThat(map.get("thumb").get("url")).isEqualTo("https://something/anotherthumb.jpg");
    }
}
