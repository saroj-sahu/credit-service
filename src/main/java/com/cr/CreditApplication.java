package com.cr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cr")
public class CreditApplication {

    public static void main(String args[]){
        SpringApplication.run(CreditApplication.class, args);
    }
}
