package com.demo.myproject.utils;


public class CustomerNotFoundException extends Exception {
	
	public CustomerNotFoundException(String message) {
		 super(message);
	}
	
	  public CustomerNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }

}