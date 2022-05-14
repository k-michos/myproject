package com.demo.myproject.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.myproject.entities.Account;
import com.demo.myproject.entities.Customer;
import com.demo.myproject.repositories.CustomerDAO;
import com.demo.myproject.services.Customers;
import com.demo.myproject.utils.Constants.AccountType;
import com.demo.myproject.utils.CustomerNotFoundException;

@Service
public class CustomersImpl implements Customers {
	
	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public List<Customer> getAllCustomers() {
		return customerDAO.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerDAO.findById(id);
		
		if (!opt.isPresent()) {
			throw new CustomerNotFoundException(
	                "Could not find customer with customerId = " + id);
		}
		else {
			return opt.get();
		}
		
	}

	@Override
	public Customer addNewCustomer(String name, String surname) {
		
		Customer customer = new Customer(); 
		customer.setName(name);
		customer.setSurname(surname);
		
//		List<Account> list = customer.getAccounts();
//		
//		if (list.isEmpty()) {
//			Account account = new Account();
//			account.setAccountId(0);
//			account.setBalance(100);
//			account.setAccountType(AccountType.CURRENT.toString());
//			list.add(account);
//		}
//		customer.setAccounts(list);
		customerDAO.saveAndFlush(customer);
		return customer;
	}
	
	@Override
	public Customer updateCustomer(Long id, String newName, String newSurname) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerDAO.findById(id);
		
		if (!opt.isPresent()) {
			throw new CustomerNotFoundException(
	                "Could not find customer with customerId = " + id);
		}
		else {
			Customer customer = opt.get();
			customer.setName(newName);
			customer.setSurname(newSurname);
			customerDAO.saveAndFlush(customer);
			return customer;
		}
	}
	
	@Override
	public void deleteCustomer (Long id) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerDAO.findById(id);
		
		if (!opt.isPresent()) {
			throw new CustomerNotFoundException(
	                "Could not find customer with customerId = " + id);
		}
		else {
			customerDAO.delete(opt.get());
		}
	}
	
	@Override
	public Customer newCurrentAccount (Long customerID, double initialCredit) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerDAO.findById(customerID);
		
		if (!opt.isPresent()) {
			throw new CustomerNotFoundException(
	                "Could not find customer with customerId = " + customerID);
		}
		else {
			Customer customer = opt.get();
			
			List<Account> list = customer.getAccounts();
			
			Account account = new Account();
			account.setAccountId(list.size()+1);
			account.setAccountType(AccountType.CURRENT.toString());
			account.setBalance(initialCredit);
			
			list.add(account);
			
			customer.setAccounts(list);
			customerDAO.saveAndFlush(customer);
			return customer;
			
		}
		
	}

}
