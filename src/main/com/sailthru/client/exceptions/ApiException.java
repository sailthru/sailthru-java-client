package com.sailthru.client.exceptions;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

/**
 * Handle API related Exceptions
 * @author Prajwal Tuladhar
 */
public class ApiException extends IOException {

    private static Logger logger = Logger.getLogger(ApiException.class.getName());

    private Object jsonResponse;
    private int statusCode;

    public ApiException(int statusCode, String reason, Object jsonResponse) {
        super(reason);
        logger.info(String.format("%d: %s", statusCode, reason));
        this.jsonResponse = jsonResponse;
        this.statusCode = statusCode;
    }

    public Object getResponse() {
        return jsonResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static ApiException create(HttpEntity entity, StatusLine statusLine, Object jsonResponse) {
        int statusCode = statusLine.getStatusCode();
        String reason = null;
        try {
            InputStream inputStream = entity.getContent();
            DataInputStream dis = new DataInputStream(inputStream);
            reason = dis.readUTF();
        } catch (EOFException ex) {
            ;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ApiException(statusCode, reason, jsonResponse);
    }
}
