package com.sailthru.client.params;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class BlastStat extends Stats {
    protected Integer blast_id;
    protected String start_date;
    protected String end_date;
    protected String list;
    protected Integer beacon_times;
    protected Integer click_times;
    protected Integer clickmap;
    protected Integer domain;
    protected Integer engagement;
    protected Integer signup;
    protected Integer subject;
    protected Integer urls;

    public BlastStat() {
        super(MODE_BLAST);
    }

    public BlastStat setBlastId(Integer blastId) {
        this.blast_id = blastId;
        return this;
    }

    public BlastStat setStartDate(Date startDate) {
        this.start_date = startDate.toString();
        return this;
    }

    public BlastStat setStartDate(String startDate) {
        this.start_date = startDate;
        return this;
    }

    public BlastStat setEndDate(Date endDate) {
        this.end_date = endDate.toString();
        return this;
    }

    public BlastStat setEndDate(String endDate) {
        this.end_date = endDate;
        return this;
    }

    public BlastStat setList(String list) {
        this.list = list;
        return this;
    }

    public BlastStat enableBeaconTimes() {
        this.beacon_times = 1;
        return this;
    }

    public BlastStat enableClickTimes() {
        this.click_times = 1;
        return this;
    }

    public BlastStat enableClickMap() {
        this.clickmap = 1;
        return this;
    }

    public BlastStat enableDomain() {
        this.domain = 1;
        return this;
    }

    public BlastStat enableEngagement() {
        this.engagement = 1;
        return this;
    }

    public BlastStat enableSignup() {
        this.signup = 1;
        return this;
    }

    public BlastStat enableSubject() {
        this.subject = 1;
        return this;
    }

    public BlastStat enableUrls() {
        this.urls = 1;
        return this;
    }

    public Type getType() {
        Type type = new TypeToken<BlastStat>() {}.getType();
        return type;
    }
}
