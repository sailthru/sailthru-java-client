package com.sailthru.client.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JsonHandlerTest {

    private JsonHandler jsonHandler;

    @BeforeEach
    public void setUp() {
        jsonHandler = new JsonHandler();
    }

    @Test
    public void testGetFormat() {
        assertThat(jsonHandler.getFormat()).isEqualTo("json");
    }

    @Test
    public void testParseResponseWithContent() {
        String json = "{\"url\":\"http://sailthru.com\",\"title\":\"testGetContent Title\",\"date\":\"Thu Oct 03 20:18:14 UTC 2013\",\"vars\":{\"baz\":\"foo\"}}";

        Map<String, Object> result = parseAsMap(json);

        assertThat(result.get("url")).isEqualTo("http://sailthru.com");
        assertThat(result.get("title")).isEqualTo("testGetContent Title");
        assertThat(result.get("date")).isEqualTo("Thu Oct 03 20:18:14 UTC 2013");

        Map<String, Object> vars = getNestedMap(result, "vars");
        assertThat(vars.get("baz")).isEqualTo("foo");
    }

    @Test
    public void testParseResponseWithImages() {
        String json = "{\"images\":{\"full\":{\"url\":\"https://images.google.com/abc\"},\"thumb\":{\"url\":\"https://images.google.com/def\"}}}";

        Map<String, Object> result = parseAsMap(json);
        Map<String, Object> images = getNestedMap(result, "images");
        Map<String, Object> full = getNestedMap(images, "full");
        Map<String, Object> thumb = getNestedMap(images, "thumb");

        assertThat(full.get("url")).isEqualTo("https://images.google.com/abc");
        assertThat(thumb.get("url")).isEqualTo("https://images.google.com/def");
    }

    @Test
    public void testParseResponseWithLocation() {
        String json = "{\"location\":[40.256,-74.1239]}";

        Map<String, Object> result = parseAsMap(json);
        List<Object> location = getNestedList(result, "location");

        assertThat(location).hasSize(2);
        assertThat(location).contains(40.256);
        assertThat(location).contains(-74.1239);
    }

    @Test
    public void testParseResponseWithPrice() {
        String json = "{\"price\":1200.0}";

        Map<String, Object> result = parseAsMap(json);

        assertThat(result.get("price")).isEqualTo(1200.0);
    }

    @Test
    public void testParseResponseWithComplexNestedStructure() {
        String json = "{\"url\":\"http://example.com\",\"images\":{\"full\":{\"url\":\"https://example.com/image.jpg\"}},\"location\":[40.256,-74.1239],\"price\":999.99,\"metadata\":{\"key\":\"value\"}}";

        Map<String, Object> result = parseAsMap(json);

        assertThat(result.get("url")).isEqualTo("http://example.com");
        assertThat(result.get("price")).isEqualTo(999.99);
        assertThat(result).containsKey("images");
        assertThat(result).containsKey("location");
        assertThat(result).containsKey("metadata");
    }

    @Test
    public void testParseResponseWithEmptyJson() {
        Map<String, Object> result = parseAsMap("{}");
        assertThat(result).isEmpty();
    }

    @Test
    public void testParseResponseReturnsMap() {
        String json = "{\"key\":\"value\",\"number\":42}";
        Object result = jsonHandler.parseResponse(json);
        assertThat(result).isInstanceOf(Map.class);
    }

    @Test
    public void testParseResponseWithMalformedJson() {
        assertThatThrownBy(() -> jsonHandler.parseResponse("{\"key\":\"value\""))
            .isInstanceOf(Exception.class);
    }

    @Test
    public void testParseResponseWithDifferentNumberFormats() {
        String json = "{\"integer\":100,\"decimal\":123.45,\"negative\":-99,\"scientific\":1.23E+3}";

        Map<String, Object> result = parseAsMap(json);

        assertThat(result.get("integer")).isEqualTo(100L);
        assertThat(result.get("decimal")).isEqualTo(123.45);
        assertThat(result.get("negative")).isEqualTo(-99L);
    }

    @Test
    public void testParseResponseWithNullValues() {
        Map<String, Object> result = parseAsMap("{\"key\":null}");

        assertThat(result).containsKey("key");
        assertThat(result.get("key")).isNull();
    }

    @Test
    public void testParseResponseWithBooleanValues() {
        Map<String, Object> result = parseAsMap("{\"flagTrue\":true,\"flagFalse\":false}");

        assertThat(result.get("flagTrue")).isEqualTo(true);
        assertThat(result.get("flagFalse")).isEqualTo(false);
    }

    @Test
    public void testParseResponseWithEmptyArrayAndEmptyString() {
        Map<String, Object> result = parseAsMap("{\"emptyArray\":[],\"emptyString\":\"\"}");

        List<?> emptyArray = getNestedList(result, "emptyArray");
        assertThat(emptyArray).isEmpty();
        assertThat(result.get("emptyString")).isEqualTo("");
    }

    @Test
    public void testParseResponseWithNestedArrays() {
        Map<String, Object> result = parseAsMap("{\"matrix\":[[1,2],[3,4]]}");

        List<Object> matrix = getNestedList(result, "matrix");
        assertThat(matrix).hasSize(2);

        List<Object> firstRow = castToList(matrix.get(0));
        assertThat(firstRow.get(0)).isEqualTo(1L);
        assertThat(firstRow.get(1)).isEqualTo(2L);

        List<Object> secondRow = castToList(matrix.get(1));
        assertThat(secondRow.get(0)).isEqualTo(3L);
        assertThat(secondRow.get(1)).isEqualTo(4L);
    }

    @Test
    public void testParseResponseWithLargeNumbers() {
        Map<String, Object> result = parseAsMap("{\"bigNumber\":9223372036854775807}");
        assertThat(result.get("bigNumber")).isEqualTo(9223372036854775807L);
    }

    // Helper methods

    private Map<String, Object> parseAsMap(String json) {
        Object result = jsonHandler.parseResponse(json);
        assertThat(result).as("Expected result to be a Map").isInstanceOf(Map.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) result;
        return map;
    }

    private Map<String, Object> getNestedMap(Map<String, Object> parent, String key) {
        assertThat(parent).as("Expected key '" + key + "' to exist").containsKey(key);
        Object value = parent.get(key);
        assertThat(value).as("Expected '" + key + "' to be a Map").isInstanceOf(Map.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) value;
        return map;
    }

    private List<Object> getNestedList(Map<String, Object> parent, String key) {
        assertThat(parent).as("Expected key '" + key + "' to exist").containsKey(key);
        Object value = parent.get(key);
        assertThat(value).as("Expected '" + key + "' to be a List").isInstanceOf(List.class);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) value;
        return list;
    }

    private List<Object> castToList(Object value) {
        assertThat(value).as("Expected value to be a List").isInstanceOf(List.class);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) value;
        return list;
    }
}
