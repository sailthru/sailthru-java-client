package com.sailthru.client.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link JsonHandler} class.
 * Tests the parseResponse method to ensure it correctly parses JSON payloads
 * and returns a Map of objects with the appropriate structure and data types.
 */
class JsonHandlerTest {

    private JsonHandler jsonHandler;

    @BeforeEach void setUp() {
        jsonHandler = new JsonHandler();
    }

    @Test void parseResponseWithContent() {
        // Given: JSON payload with nested object and vars
        String content = "{\"url\":\"http://sailthru.com\",\"title\":\"testGetContent Title\",\"date\":\"Thu Oct 03 20:18:14 UTC 2013\",\"vars\":{\"baz\":\"foo\"}}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(content);

        // Then: result should be a Map with correct structure and values
        assertInstanceOf(Map.class, result);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertThat(responseMap.get("url")).isEqualTo("http://sailthru.com");
        assertThat(responseMap.get("title")).isEqualTo("testGetContent Title");
        assertThat(responseMap.get("date")).isEqualTo("Thu Oct 03 20:18:14 UTC 2013");
        assertThat(responseMap.containsKey("vars")).isTrue();

        // Verify nested vars object
        Object varsObj = responseMap.get("vars");
        assertInstanceOf(Map.class, varsObj);

        @SuppressWarnings("unchecked")
        Map<String, Object> varsMap = (Map<String, Object>) varsObj;
        assertThat(varsMap.get("baz")).isEqualTo("foo");
    }

    @Test void parseResponseWithImages() {
        // Given: JSON payload with nested images structure
        String images = "{\"images\":{\"full\":{\"url\":\"https://images.google.com/abc\"},\"thumb\":{\"url\":\"https://images.google.com/def\"}}}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(images);

        // Then: result should be a Map with nested image objects
        assertInstanceOf(Map.class, result);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertThat(responseMap.containsKey("images")).isTrue();

        // Verify nested images object
        Object imagesObj = responseMap.get("images");
        assertInstanceOf(Map.class, imagesObj);

        @SuppressWarnings("unchecked")
        Map<String, Object> imagesMap = (Map<String, Object>) imagesObj;

        assertThat(imagesMap.containsKey("full")).isTrue();
        assertThat(imagesMap.containsKey("thumb")).isTrue();

        // Verify full image structure
        Object fullObj = imagesMap.get("full");
        assertInstanceOf(Map.class, fullObj);

        @SuppressWarnings("unchecked")
        Map<String, Object> fullMap = (Map<String, Object>) fullObj;
        assertThat(fullMap.get("url")).isEqualTo("https://images.google.com/abc");

        // Verify thumb image structure
        Object thumbObj = imagesMap.get("thumb");
        assertInstanceOf(Map.class, thumbObj);

        @SuppressWarnings("unchecked")
        Map<String, Object> thumbMap = (Map<String, Object>) thumbObj;
        assertThat(thumbMap.get("url")).isEqualTo("https://images.google.com/def");
    }

    @Test void parseResponseWithLocation() {
        // Given: JSON payload with array of coordinates
        String location = "{\"location\":[40.256,-74.1239]}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(location);

        // Then: result should be a Map with location array
        assertInstanceOf(Map.class, result);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertThat(responseMap.containsKey("location")).isTrue();

        // Verify location array
        Object locationObj = responseMap.get("location");
        assertInstanceOf(List.class, locationObj);

        @SuppressWarnings("unchecked")
        List<Object> locationList = (List<Object>) locationObj;

        assertThat(locationList.size()).isEqualTo(2);
        assertThat(locationList.contains(40.256)).isTrue();
        assertThat(locationList.contains(-74.1239)).isTrue();
    }

    @Test void parseResponseWithPrice() {
        // Given: JSON payload with simple numeric value
        String price = "{\"price\":1200}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(price);

        // Then: result should be a Map with price value
        assertInstanceOf(Map.class, result);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertThat(responseMap.get("price")).isEqualTo(1200.0);
    }

    @Test void parseResponseWithComplexNestedStructure() {
        // Given: JSON payload combining all data types
        String complexJson = "{\n" +
                "                \"url\":\"http://example.com\",\n" +
                "                \"images\":{\"full\":{\"url\":\"https://example.com/image.jpg\"}},\n" +
                "                \"location\":[40.256,-74.1239],\n" +
                "                \"price\":999.99,\n" +
                "                \"metadata\":{\"key\":\"value\"}\n" +
                "                }";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(complexJson);

        // Then: result should correctly handle all nested structures
        assertInstanceOf(Map.class, result);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertThat(responseMap.get("url")).isEqualTo("http://example.com");
        assertThat(responseMap.get("price")).isEqualTo(999.99);
        assertThat(responseMap.containsKey("images")).isTrue();
        assertThat(responseMap.containsKey("location")).isTrue();
        assertThat(responseMap.containsKey("metadata")).isTrue();
    }

    @Test void parseResponseWithEmptyJson() {
        // Given: Empty JSON object
        String emptyJson = "{}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(emptyJson);

        // Then: result should be an empty Map
        assertInstanceOf(Map.class, result);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertThat(responseMap.isEmpty()).isTrue();
    }

    @Test void parseResponseReturnsMap() {
        // Given: Valid JSON payload
        String json = "{\"key\":\"value\",\"number\":42}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(json);

        // Then: result type should be Map
        assertInstanceOf(Map.class, result);
    }

    @Test void parseResponseWithMalformedJson() {
        // Given: Malformed JSON payload
        String malformedJson = "{\"key\":\"value\"";

        // When/Then: parseResponse should throw an exception
        boolean exceptionThrown = false;
        try {
            jsonHandler.parseResponse(malformedJson);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "Expected an exception to be thrown for malformed JSON");
    }

    @Test void getFormat() {
        // Given: JsonHandler instance
        // When/Then: getFormat should return "json"
        assertThat(jsonHandler.getFormat()).isEqualTo("json");
    }

    @Test void parseResponseWithDifferentNumberFormats() {
        // Given: JSON payload with various number formats
        String numberJson = "{\n" +
                "                \"integer\":100,\n" +
                "                \"decimal\":123.45,\n" +
                "                \"negative\":-99,\n" +
                "                \"scientific\":1.23E+3\n" +
                "                }";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(numberJson);

        // Then: all number formats should be parsed correctly
        assertInstanceOf(Map.class, result);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertThat(responseMap.get("integer")).isEqualTo(100.0);
        assertThat(responseMap.get("decimal")).isEqualTo(123.45);
        assertThat(responseMap.get("negative")).isEqualTo(-99.0);
    }

}
