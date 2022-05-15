package com.demo.myproject.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.myproject.entities.Account;
import com.demo.myproject.entities.Customer;
import com.demo.myproject.repositories.CustomerDAO;
import com.demo.myproject.repositories.TransactionDAO;
import com.demo.myproject.services.Customers;
import com.demo.myproject.services.Transactions;
import com.demo.myproject.utils.Constants.AccountType;
import com.demo.myproject.utils.Constants.TransactionType;
import com.demo.myproject.utils.CustomerNotFoundException;

@Service
public class CustomersImpl implements Customers {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private Transactions transactions;

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
		
		if (initialCredit < 0) {
			System.out.println("You cannot create a new current account with negative initial credit. Please try again with 0 or positive initial credit.");
		}
		
		Optional<Customer> opt = customerDAO.findById(customerID);
		
		if (!opt.isPresent()) {
			throw new CustomerNotFoundException(
	                "Could not find customer with customerId = " + customerID);
		}
		else {
			Customer customer = opt.get();
			
			List<Account> list = customer.getAccounts();
			
			Account account = new Account();
			account.setAccountId(list.size());
			account.setAccountType(AccountType.CURRENT.toString());
			
			account = deposit(customer.getCustomerID(),account, initialCredit);
			//account.setBalance(initialCredit);
			
			list.add(account);
			
			customer.setAccounts(list);
			customerDAO.saveAndFlush(customer);
			return customer;
			
		}
		
	}
	
	@Override
	public Account deposit (Long customerID, Account account, double amount) {
		 double newBlanace = account.getBalance() + amount;
		 account.setBalance(newBlanace);
		 
		 transactions.addNewTransaction(customerID,account.getAccountId(), TransactionType.DEPOSIT, amount, newBlanace);
		 
		 return account;
	 }

	@Override
	public Account withdraw (Long customerID, Account account, double amount) {
	    	
	    	if (account.getBalance() >= amount) {
	    		 double newBlanace = account.getBalance() - amount;
	    		 account.setBalance(newBlanace);
	    		 
	    		 transactions.addNewTransaction(customerID, account.getAccountId(), TransactionType.WITHDRAWAL, amount, newBlanace);
	    		return account;
	    	}
	    	else {
	    		System.out.println("Your balance is not enough to perform this withdrawal");
	    		return null; //as a possible check that the withdrawal was  never completed. (or custom exception)
	    	}
	    	
	    }

}
