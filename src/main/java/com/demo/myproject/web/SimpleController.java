package com.demo.myproject.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class SimpleController {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping
    public String homePage() {
    	String s = "Hello! This is a " + appName;
    	System.out.println(s);
        return s;
    	
    }
}