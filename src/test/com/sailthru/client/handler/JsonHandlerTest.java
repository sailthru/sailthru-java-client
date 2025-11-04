package com.sailthru.client.handler;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link JsonHandler} class.
 * Tests the parseResponse method to ensure it correctly parses JSON payloads
 * and returns a Map of objects with the appropriate structure and data types.
 */
public class JsonHandlerTest {

    private JsonHandler jsonHandler;

    @Before
    public void setUp() {
        jsonHandler = new JsonHandler();
    }

    @Test
    public void testParseResponseWithContent() {
        // Given: JSON payload with nested object and vars
        String content = "{\"url\":\"http://sailthru.com\",\"title\":\"testGetContent Title\",\"date\":\"Thu Oct 03 20:18:14 UTC 2013\",\"vars\":{\"baz\":\"foo\"}}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(content);

        // Then: result should be a Map with correct structure and values
        assertTrue(result instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertEquals("http://sailthru.com", responseMap.get("url"));
        assertEquals("testGetContent Title", responseMap.get("title"));
        assertEquals("Thu Oct 03 20:18:14 UTC 2013", responseMap.get("date"));
        assertTrue(responseMap.containsKey("vars"));

        // Verify nested vars object
        Object varsObj = responseMap.get("vars");
        assertTrue(varsObj instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> varsMap = (Map<String, Object>) varsObj;
        assertEquals("foo", varsMap.get("baz"));
    }

    @Test
    public void testParseResponseWithImages() {
        // Given: JSON payload with nested images structure
        String images = "{\"images\":{\"full\":{\"url\":\"https://images.google.com/abc\"},\"thumb\":{\"url\":\"https://images.google.com/def\"}}}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(images);

        // Then: result should be a Map with nested image objects
        assertTrue(result instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertTrue(responseMap.containsKey("images"));

        // Verify nested images object
        Object imagesObj = responseMap.get("images");
        assertTrue(imagesObj instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> imagesMap = (Map<String, Object>) imagesObj;

        assertTrue(imagesMap.containsKey("full"));
        assertTrue(imagesMap.containsKey("thumb"));

        // Verify full image structure
        Object fullObj = imagesMap.get("full");
        assertTrue(fullObj instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> fullMap = (Map<String, Object>) fullObj;
        assertEquals("https://images.google.com/abc", fullMap.get("url"));

        // Verify thumb image structure
        Object thumbObj = imagesMap.get("thumb");
        assertTrue(thumbObj instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> thumbMap = (Map<String, Object>) thumbObj;
        assertEquals("https://images.google.com/def", thumbMap.get("url"));
    }

    @Test
    public void testParseResponseWithLocation() {
        // Given: JSON payload with array of coordinates
        String location = "{\"location\":[40.256,-74.1239]}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(location);

        // Then: result should be a Map with location array
        assertTrue(result instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertTrue(responseMap.containsKey("location"));

        // Verify location array
        Object locationObj = responseMap.get("location");
        assertTrue(locationObj instanceof List);

        @SuppressWarnings("unchecked")
        List<Object> locationList = (List<Object>) locationObj;

        assertEquals(2, locationList.size());
        assertTrue(locationList.contains(40.256));
        assertTrue(locationList.contains(-74.1239));
    }

    @Test
    public void testParseResponseWithPrice() {
        // Given: JSON payload with simple numeric value
        String price = "{\"price\":1200}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(price);

        // Then: result should be a Map with price value
        assertTrue(result instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertEquals(1200.0, responseMap.get("price"));
    }

    @Test
    public void testParseResponseWithComplexNestedStructure() {
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
        assertTrue(result instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertEquals("http://example.com", responseMap.get("url"));
        assertEquals(999.99, responseMap.get("price"));
        assertTrue(responseMap.containsKey("images"));
        assertTrue(responseMap.containsKey("location"));
        assertTrue(responseMap.containsKey("metadata"));
    }

    @Test
    public void testParseResponseWithEmptyJson() {
        // Given: Empty JSON object
        String emptyJson = "{}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(emptyJson);

        // Then: result should be an empty Map
        assertTrue(result instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertTrue(responseMap.isEmpty());
    }

    @Test
    public void testParseResponseReturnsMap() {
        // Given: Valid JSON payload
        String json = "{\"key\":\"value\",\"number\":42}";

        // When: parseResponse is called
        Object result = jsonHandler.parseResponse(json);

        // Then: result type should be Map
        assertTrue(result instanceof Map);
    }

    @Test
    public void testParseResponseWithMalformedJson() {
        // Given: Malformed JSON payload
        String malformedJson = "{\"key\":\"value\"";

        // When/Then: parseResponse should throw an exception
        boolean exceptionThrown = false;
        try {
            jsonHandler.parseResponse(malformedJson);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertTrue("Expected an exception to be thrown for malformed JSON", exceptionThrown);
    }

    @Test
    public void testGetFormat() {
        // Given: JsonHandler instance
        // When/Then: getFormat should return "json"
        assertEquals("json", jsonHandler.getFormat());
    }

    @Test
    public void testParseResponseWithDifferentNumberFormats() {
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
        assertTrue(result instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = (Map<String, Object>) result;

        assertEquals(100.0, responseMap.get("integer"));
        assertEquals(123.45, responseMap.get("decimal"));
        assertEquals(-99.0, responseMap.get("negative"));
    }

}
