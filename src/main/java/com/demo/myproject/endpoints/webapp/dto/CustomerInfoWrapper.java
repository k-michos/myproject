package com.demo.myproject.endpoints.webapp.dto;

import com.demo.myproject.entities.Customer;

public class CustomerInfoWrapper {
	
	private Customer customer;
	
	private double initialCredit;
	
	

	public CustomerInfoWrapper() {
		
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getInitialCredit() {
		return initialCredit;
	}

	public void setInitialCredit(double initialCredit) {
		this.initialCredit = initialCredit;
	}

	@Override
	public String toString() {
		return "CustomerInfoWrapper [customer=" + customer + ", initialCredit=" + initialCredit + "]";
	}

}
