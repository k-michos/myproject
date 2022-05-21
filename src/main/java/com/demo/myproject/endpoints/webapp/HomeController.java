package com.demo.myproject.endpoints.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/webapp/home")
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 @Value("${spring.application.name}")
	    String appName;

	    @GetMapping
	    public ModelAndView homePage() {
	    	String s = "This is a " + appName;
	    	System.out.println(s);
	    	 logger.info(s);
	    	 ModelAndView mav = new ModelAndView("home");
	    	 mav.addObject("s",s);
	    	 mav.addObject("appName", appName);
	    	//model.addAttribute("appName", appName);
	        return mav;
	    	
	    }

}
