package com.sailthru.client.response;

import java.util.ArrayList;

/**
 *
 * @author Prajwal Tuladhar
 */
public class MultiSendResponse extends AbstractResponse {

    private ArrayList<String> send_ids;
    private int send_count;
    private String email;
    private String template;
    private String status;
    private String send_time;
    private String schedule_time;
    private String schdule_time;
    private String open_time;
    private String click_time;

}
