package com.agoda.poc.model;

import java.sql.DatabaseMetaData;
import java.util.Date;

import static com.agoda.poc.contstant.Constant.*;

public class RateLimit {

    //default value

    private final int rateLimitId;
    private final String rateUrlname;
    private int ratesize;
    private int rateFrame;
    private int rateSleep;
    private Date startDate;
  //  private Date lastDate;
    private Date sleepTime;

    private int requestCount = 0;

    protected RateLimit(RateLimitBuilder rateLimitBuilder) {
        this.rateUrlname = rateLimitBuilder.rateUrlname;
        this.rateLimitId = rateLimitBuilder.rateLimitId;
        this.ratesize = rateLimitBuilder.ratesize;
        this.rateFrame = rateLimitBuilder.rateFrame;
        this.sleepTime = new Date(new Date().toInstant().minusSeconds(rateSleep+1).toEpochMilli());

    }

    public int getRatesize() {
        return ratesize;
    }

    public void setRatesize(int ratesize) {
        this.ratesize = ratesize;
    }

    public int getRateSleep() {
        return rateSleep;
    }

    public void setRateSleep(int rateSleep) {
        this.rateSleep = rateSleep;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

   /* public Date getLastDate() {
        return lastDate;
    }*/

    /*public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }*/

    public int getRateFrame() {
        return rateFrame;
    }

    public int getRateLimitId() {
        return rateLimitId;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void resetRequestCount() {
        requestCount = 1;
    }


    public void incrRequestCount(){
        requestCount++;
    }

    public Date getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Date sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getRateUrlname() {
        return rateUrlname;
    }

    public static class RateLimitBuilder {
        private int rateLimitId;
        private String rateUrlname;
        private int ratesize = DEFAULT_API_LIMIT;
        private int rateFrame = DEFAULT_API_FRAME;
        private int rateSleep = DEFAULT_API_SLEEP;

        public RateLimitBuilder(int rateLimitId,String rateUrlname){
            this.rateLimitId = rateLimitId;
            this.rateUrlname = rateUrlname;
        }

        public RateLimitBuilder rateSize(int ratesize){
            this.ratesize = ratesize;
            return this;
        }

        public RateLimitBuilder rateFrame(int rateFrame){
            this.rateFrame = rateFrame;
            return this;
        }

        public RateLimitBuilder rateSleep(int rateSleep){
            this.rateSleep = rateSleep;
            return this;
        }

        public RateLimit build(){
            RateLimit rateLimit = new RateLimit(this);
            return rateLimit;
        }
    }

}
