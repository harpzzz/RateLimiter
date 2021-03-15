package com.agoda.poc.service;

import com.agoda.poc.model.RateLimit;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class RateService {

    Logger logger = LoggerFactory.getLogger(RateService.class);


    private HashMap<String,RateLimit> rateLimitMap = new HashMap<String,RateLimit>();
    private int rateIdCount =0;

    {
        rateLimitMap.put("city",new RateLimit.RateLimitBuilder(++rateIdCount,"city")
                .rateSize(10)
                .rateFrame(5)
                .build());
        rateLimitMap.put("room",new RateLimit.RateLimitBuilder(++rateIdCount,"room")
                .rateSize(100)
                .rateFrame(10)
                .build());
        rateLimitMap.put("test2",new RateLimit.RateLimitBuilder(++rateIdCount,"test2")
                .rateSize(1)
                .rateFrame(5)
                .build());
        rateLimitMap.put("test6",new RateLimit.RateLimitBuilder(++rateIdCount,"test6")
                .rateSize(1)
                .rateFrame(5)
                .build());
    }

    public boolean checkRateLimit(String urlPath) {
        if(!rateLimitMap.containsKey(urlPath)){
            rateLimitMap.put(urlPath,new RateLimit.RateLimitBuilder(++rateIdCount,urlPath).build());
        }

        RateLimit rateLimit = rateLimitMap.get(urlPath);

        return isOnline(rateLimit);
    }

    private synchronized boolean isOnline(RateLimit rateLimit) {



        Date curDate = new Date();

        if(rateLimit.getSleepTime().before(curDate)) {
            long diff = (rateLimit.getStartDate() == null)?rateLimit.getRateFrame()+1:(curDate.getTime() - rateLimit.getStartDate().getTime()) /1000 % 60;
            if (diff < rateLimit.getRateFrame()) {
                if (rateLimit.getRequestCount() + 1 <= rateLimit.getRatesize()) {
                    rateLimit.incrRequestCount();
                  //  rateLimit.setLastDate(curDate);
                    logger.debug("[DEBUG]"+rateLimit.getRateUrlname() +"Start "+rateLimit.getStartDate() +"Accepted"+"Count rest"+rateLimit.getRequestCount()+"diff -> "+ diff);
                    return true;
                } else {
                    Date sleepDate = new Date(curDate.toInstant().plusSeconds(rateLimit.getRateSleep()).toEpochMilli());
                     rateLimit.setSleepTime(sleepDate);
                     //rateLimit.resetRequestCount();
                     // need to decide
                     //rateLimit.setStartDate(sleepDate);
                    logger.debug("[DEBUG]"+rateLimit.getRateUrlname() +"Start "+rateLimit.getStartDate()+"sleep time "
                            + rateLimit.getSleepTime() +"Exhausted"+"diff -> "+ diff);
                    return false;
                }
            } else {
                rateLimit.setStartDate(curDate);
                rateLimit.resetRequestCount();
               // rateLimit.setLastDate(curDate);
                logger.debug("[DEBUG]"+rateLimit.getRateUrlname() +"Start "+rateLimit.getStartDate()+"new slot"+"diff -> "+ diff);
                return true;
            }
        }
        return false;

    }


}
