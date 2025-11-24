package com.sailthru.client.params;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.List;
import org.junit.jupiter.api.Test;

import java.text.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class ContentTest  {
    Gson gson = SailthruUtil.createGson();

    DateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");

    @Test
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
        String expected = "{\"url\":\"http://sailthru.com\",\"title\":\"testGetContent Title\",\"date\":\"Thu Oct 03 20:18:14 UTC 2013\",\"vars\":{\"baz\":\"foo\"}}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetTitle(){
        Content content = new Content();
        String title = "Test Title";
        content.setTitle(title);
        String expected = "{\"title\":\"Test Title\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetKeys() {
        Content content = new Content();
        Map<String, String> keys = new HashMap<String, String>();
        keys.put("sku", "123abc");
        content.setKeys(keys);

        String expected = "{\"keys\":{\"sku\":\"123abc\"}}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetUrl(){
        Content content = new Content();
        String url = "http://sailthru.com";
        content.setUrl(url);
        String expected = "{\"url\":\"http://sailthru.com\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetDateFormat(){
        Content content = new Content();
        Date date = new Date(1380831494000L);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        content.setDate(format.format(date));
        String expected = "{\"date\":\"Thu Oct 03 20:18:14 UTC 2013\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetTags(){
        Content content = new Content();
        List tags = new ArrayList<String>();
        tags.add("foo");
        tags.add("bar");
        content.setTags(tags);
        String expected = "{\"tags\":[\"foo\",\"bar\"]}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetDateString(){
        Content content = new Content();
        String date = "1380831494000L";
        content.setDate(date);
        String expected = "{\"date\":\"1380831494000L\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetVars(){
        Content content = new Content();
        Map<String, Object> vars = new LinkedHashMap<String, Object>();
        vars.put("test","result");
        vars.put("test2","result2");
        vars.put("test3","result3");
        content.setVars(vars);
        String expected = "{\"vars\":{\"test\":\"result\",\"test2\":\"result2\",\"test3\":\"result3\"}}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetExpireDate(){
        Content content = new Content();
        Date date = new Date(1380831494000L);
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        content.setExpireDate(format.format(date));
        String expected = "{\"expire_date\":\"Thu Oct 03 20:18:14 UTC 2013\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetExpireDateString(){
        Content content = new Content();
        String date = "1380831494000L";
        content.setExpireDate(date);
        String expected = "{\"expire_date\":\"1380831494000L\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetImages() {
        Content content = new Content();
        Map<String, Map<String, String>> images = new LinkedHashMap<String, Map<String, String>>();
        Map<String, String> fullUrl = new HashMap<String, String>();
        fullUrl.put("url", "https://images.google.com/abc");
        Map<String, String> thumbUrl = new HashMap<String, String>();
        thumbUrl.put("url", "https://images.google.com/def");
        images.put("full", fullUrl);
        images.put("thumb", thumbUrl);
        content.setImages(images);
        String expected = "{\"images\":{\"full\":{\"url\":\"https://images.google.com/abc\"},\"thumb\":{\"url\":\"https://images.google.com/def\"}}}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetFullImage() {
        Content content = new Content();
        content.setFullImage("https://images.google.com/abc");
        String expected = "{\"images\":{\"full\":{\"url\":\"https://images.google.com/abc\"}}}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetThumbImage() {
        Content content = new Content();
        content.setThumbImage("https://images.google.com/abc");
        String expected = "{\"images\":{\"thumb\":{\"url\":\"https://images.google.com/abc\"}}}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetLocationValue() {
        Content content = new Content();
        content.setLocation(40.256, -74.1239);
        String expected = "{\"location\":[40.256,-74.1239]}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetLocationObject() {
        Content content = new Content();
        List<Double> location = new ArrayList<Double>();
        location.add(40.256);
        location.add(-74.1239);
        content.setLocation(location);
        String expected = "{\"location\":[40.256,-74.1239]}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetPrice() {
        Content content = new Content();
        content.setPrice(1200);
        String expected = "{\"price\":1200}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetDescription() {
        Content content = new Content();
        content.setDescription("this is a test.");
        String expected = "{\"description\":\"this is a test.\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetSiteName() {
        Content content = new Content();
        content.setSiteName("Hello New York");
        String expected = "{\"site_name\":\"Hello New York\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testSetAuthor() {
        Content content = new Content();
        content.setAuthor("Dr. Java");
        String expected = "{\"author\":\"Dr. Java\"}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testEnableSpider() {
        Content content = new Content();
        content.enableSpider();
        String expected = "{\"spider\":1}";
        String result = gson.toJson(content);
        assertThat(result).isEqualTo(expected);
    }
}
