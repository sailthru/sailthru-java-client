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

    public ApiException(int statusCode, String reason) {
        super(reason);
        logger.info(String.format("%d: %s", statusCode, reason));
    }


    public static ApiException create(HttpEntity entity, StatusLine statusLine) {
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
        return new ApiException(statusCode, reason);
    }
}
