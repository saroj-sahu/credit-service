package com.cr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping(path = "/")
    public ResponseEntity defaultRequest(){
        return ResponseEntity.ok("Application Loaded");
    }

}
