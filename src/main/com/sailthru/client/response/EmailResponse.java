package com.sailthru.client.response;

import java.util.HashMap;

/**
 *
 * @author Prajwal Tuladhar
 */
public class EmailResponse extends AbstractResponse {
    private String email;
    private int hardbounce;
    private int verified;
    private int optout;
    private HashMap<String, Object> vars;
    private HashMap<String, Integer> lists;
    private HashMap<String, Integer> templates;
    private HashMap<String, Integer> lists_signup;
}
