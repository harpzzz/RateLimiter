package com.agoda.poc.interceptor;


import com.agoda.poc.service.RateService;
import com.agoda.poc.utility.Helper;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(RateLimitingInterceptor.class);


    @Autowired
    RateService rateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      //  System.out.println(request.getRequestURI());


        String uri = request.getRequestURI();



        if(Helper.isValidUrl(uri)) {
                String urlPath = Helper.getSubPath(uri);
            logger.debug("[DEBUG]"+"URL path"+urlPath);
          if(rateService.checkRateLimit(urlPath)){
              return true;
          }else{
              response.getWriter().write("Api limit exhausted");
              response.setStatus(503);
              return false;
          }
        }

      /*
        */
        return true;
    }
}
