package com.sailthru.client.handler;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonHandlerTest {

    private JsonHandler jsonHandler;

    @Before
    public void setUp() {
        jsonHandler = new JsonHandler();
    }

    @Test
    public void testGetFormat() {
        assertEquals("json", jsonHandler.getFormat());
    }

    @Test
    public void testParseResponseWithContent() {
        String json = "{\"url\":\"http://sailthru.com\",\"title\":\"testGetContent Title\",\"date\":\"Thu Oct 03 20:18:14 UTC 2013\",\"vars\":{\"baz\":\"foo\"}}";

        Map<String, Object> result = parseAsMap(json);

        assertEquals("http://sailthru.com", result.get("url"));
        assertEquals("testGetContent Title", result.get("title"));
        assertEquals("Thu Oct 03 20:18:14 UTC 2013", result.get("date"));

        Map<String, Object> vars = getNestedMap(result, "vars");
        assertEquals("foo", vars.get("baz"));
    }

    @Test
    public void testParseResponseWithImages() {
        String json = "{\"images\":{\"full\":{\"url\":\"https://images.google.com/abc\"},\"thumb\":{\"url\":\"https://images.google.com/def\"}}}";

        Map<String, Object> result = parseAsMap(json);
        Map<String, Object> images = getNestedMap(result, "images");
        Map<String, Object> full = getNestedMap(images, "full");
        Map<String, Object> thumb = getNestedMap(images, "thumb");

        assertEquals("https://images.google.com/abc", full.get("url"));
        assertEquals("https://images.google.com/def", thumb.get("url"));
    }

    @Test
    public void testParseResponseWithLocation() {
        String json = "{\"location\":[40.256,-74.1239]}";

        Map<String, Object> result = parseAsMap(json);
        List<Object> location = getNestedList(result, "location");

        assertEquals(2, location.size());
        assertTrue(location.contains(40.256));
        assertTrue(location.contains(-74.1239));
    }

    @Test
    public void testParseResponseWithPrice() {
        String json = "{\"price\":1200.0}";

        Map<String, Object> result = parseAsMap(json);

        assertEquals(1200.0, result.get("price"));
    }

    @Test
    public void testParseResponseWithComplexNestedStructure() {
        String json = "{\"url\":\"http://example.com\",\"images\":{\"full\":{\"url\":\"https://example.com/image.jpg\"}},\"location\":[40.256,-74.1239],\"price\":999.99,\"metadata\":{\"key\":\"value\"}}";

        Map<String, Object> result = parseAsMap(json);

        assertEquals("http://example.com", result.get("url"));
        assertEquals(999.99, result.get("price"));
        assertTrue(result.containsKey("images"));
        assertTrue(result.containsKey("location"));
        assertTrue(result.containsKey("metadata"));
    }

    @Test
    public void testParseResponseWithEmptyJson() {
        Map<String, Object> result = parseAsMap("{}");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testParseResponseReturnsMap() {
        String json = "{\"key\":\"value\",\"number\":42}";
        Object result = jsonHandler.parseResponse(json);
        assertTrue(result instanceof Map);
    }

    @Test(expected = Exception.class)
    public void testParseResponseWithMalformedJson() {
        jsonHandler.parseResponse("{\"key\":\"value\"");
    }

    @Test
    public void testParseResponseWithDifferentNumberFormats() {
        String json = "{\"integer\":100,\"decimal\":123.45,\"negative\":-99,\"scientific\":1.23E+3}";

        Map<String, Object> result = parseAsMap(json);

        assertEquals(100L, result.get("integer"));
        assertEquals(123.45, result.get("decimal"));
        assertEquals(-99L, result.get("negative"));
    }

    @Test
    public void testParseResponseWithNullValues() {
        Map<String, Object> result = parseAsMap("{\"key\":null}");

        assertTrue(result.containsKey("key"));
        assertNull(result.get("key"));
    }

    @Test
    public void testParseResponseWithBooleanValues() {
        Map<String, Object> result = parseAsMap("{\"flagTrue\":true,\"flagFalse\":false}");

        assertEquals(true, result.get("flagTrue"));
        assertEquals(false, result.get("flagFalse"));
    }

    @Test
    public void testParseResponseWithEmptyArrayAndEmptyString() {
        Map<String, Object> result = parseAsMap("{\"emptyArray\":[],\"emptyString\":\"\"}");

        List<?> emptyArray = getNestedList(result, "emptyArray");
        assertTrue(emptyArray.isEmpty());
        assertEquals("", result.get("emptyString"));
    }

    @Test
    public void testParseResponseWithNestedArrays() {
        Map<String, Object> result = parseAsMap("{\"matrix\":[[1,2],[3,4]]}");

        List<Object> matrix = getNestedList(result, "matrix");
        assertEquals(2, matrix.size());

        List<Object> firstRow = castToList(matrix.get(0));
        assertEquals(1L, firstRow.get(0));
        assertEquals(2L, firstRow.get(1));

        List<Object> secondRow = castToList(matrix.get(1));
        assertEquals(3L, secondRow.get(0));
        assertEquals(4L, secondRow.get(1));
    }

    @Test
    public void testParseResponseWithLargeNumbers() {
        Map<String, Object> result = parseAsMap("{\"bigNumber\":9223372036854775807}");
        assertEquals(9223372036854775807L, result.get("bigNumber"));
    }

    // Helper methods

    private Map<String, Object> parseAsMap(String json) {
        Object result = jsonHandler.parseResponse(json);
        assertTrue("Expected result to be a Map", result instanceof Map);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) result;
        return map;
    }

    private Map<String, Object> getNestedMap(Map<String, Object> parent, String key) {
        assertTrue("Expected key '" + key + "' to exist", parent.containsKey(key));
        Object value = parent.get(key);
        assertTrue("Expected '" + key + "' to be a Map", value instanceof Map);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) value;
        return map;
    }

    private List<Object> getNestedList(Map<String, Object> parent, String key) {
        assertTrue("Expected key '" + key + "' to exist", parent.containsKey(key));
        Object value = parent.get(key);
        assertTrue("Expected '" + key + "' to be a List", value instanceof List);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) value;
        return list;
    }

    private List<Object> castToList(Object value) {
        assertTrue("Expected value to be a List", value instanceof List);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) value;
        return list;
    }
}
