package com.cr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingConfiguration implements WebMvcConfigurer {

    @Bean
    public LoggingIntercepter loggingIntercepter(){
        return new LoggingIntercepter();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry){
        registry.addInterceptor(loggingIntercepter());
    }
}
