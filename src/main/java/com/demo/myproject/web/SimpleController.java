package com.demo.myproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class SimpleController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
		
    @Value("${spring.application.name}")
    String appName;

    @GetMapping
    public String homePage() {
    	String s = "Hello! This is a " + appName;
    	System.out.println(s);
    	
    	logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
        
        return s;
    	
    }
}