package com.cr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoggingIntercepter implements HandlerInterceptor {

   private static final String START_TIME = "startTime";
   private static final String REQUEST_TIME_STAMP = "requestTimestamp";
   private static final String TRACE_ID = "traceId";
   private static final String ORIGINATOR_UUID = "orgTraceId";
   @Override
   public boolean preHandle(
           HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         String traceId = null;
         String orgTraceId = null;
         request.setAttribute(START_TIME, System.currentTimeMillis());
         request.setAttribute(REQUEST_TIME_STAMP, LocalDateTime.now());
         if(request.getHeader(ORIGINATOR_UUID) != null && !request.getHeader(ORIGINATOR_UUID).isEmpty()){
            traceId = request.getHeader(ORIGINATOR_UUID);
            orgTraceId = UUID.randomUUID().toString();

         }
         if(traceId == null){
            traceId=UUID.randomUUID().toString();
            orgTraceId=traceId;
         }
         request.setAttribute(TRACE_ID, traceId);
         request.setAttribute(ORIGINATOR_UUID, orgTraceId);
      return true;
   }
   @Override
   public void postHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler, 
      ModelAndView modelAndView) throws Exception {}
   
   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
      Object handler, Exception exception) throws Exception {}
}