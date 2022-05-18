package com.demo.myproject.services;

import java.util.List;

import com.demo.myproject.entities.Account;
import com.demo.myproject.entities.Customer;
import com.demo.myproject.utils.CustomerNotFoundException;

public interface Customers {

	public List<Customer> getAllCustomers();
	
	public Customer getCustomerById(Long id) throws CustomerNotFoundException;
	
	public Customer addNewCustomer(String name, String surname);
	
	public Customer updateCustomer(Long id, String newName, String newSurname) throws CustomerNotFoundException;
	
	public void deleteCustomer (Long id) throws CustomerNotFoundException;
	
	public Customer newCurrentAccount (Long customerID, double initialCredit) throws CustomerNotFoundException;
	
	 public Account deposit (Long customerID, int accountId, double amount) throws CustomerNotFoundException;
	 
	 public Account withdraw (Long customerID, int accountId, double amount) throws CustomerNotFoundException;
	 
	 public Customer getCustomerInfoById (Long customerID) throws CustomerNotFoundException;
		
}
