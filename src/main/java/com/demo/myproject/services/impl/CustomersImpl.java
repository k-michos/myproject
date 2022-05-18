package com.demo.myproject.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.myproject.entities.Account;
import com.demo.myproject.entities.Customer;
import com.demo.myproject.entities.Transaction;
import com.demo.myproject.repositories.CustomerDAO;
import com.demo.myproject.services.Customers;
import com.demo.myproject.services.Transactions;
import com.demo.myproject.utils.Constants.AccountType;
import com.demo.myproject.utils.Constants.TransactionType;
import com.demo.myproject.utils.CustomerNotFoundException;

@Service
public class CustomersImpl implements Customers {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
		logger.debug("New customer = " + customer + " added.");
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
			logger.debug("Customer = " + customer + " updated.");
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
			logger.debug("Customer with id= " + id + " deleted.");
			customerDAO.delete(opt.get());
		}
	}

	@Override
	public Customer getCustomerInfoById (Long customerID) throws CustomerNotFoundException{
		
		Customer customer = getCustomerById(customerID);
		
		List<Transaction> list = transactions.getAllTransactionsByCustomerID(customerID);
					
		HashMap<Integer, List<Transaction>> transactionsPerAccount = new HashMap<>();
		
		HashMap<Integer, Double> balancePerAccount = new HashMap<>();
		
		double totalBalance =0;
		
		int numberOfAccounts = customer.getAccounts().size();
		
		for (int i=0; i < numberOfAccounts ;i++) { 
			transactionsPerAccount.put(i, transactions.getAllTransactionsPerAccount(i, list));
			
			double balance = customer.getAccounts().get(i).getBalance();
			totalBalance = totalBalance + balance;
			balancePerAccount.put(i, balance);
			
		}
		
		StringBuilder log = new StringBuilder();
		log.append("\n");
		log.append("The customerID you gave belongs to: " + customer.getName() + " " + customer.getSurname() + 
				" and the total balance of all his accounts is: " + totalBalance + ". Per account: \n");
		
//		logger.debug("The customerID you gave belongs to: " + customer.getName() + " " + customer.getSurname() + 
//				" and the total balance of all his accounts is: " + totalBalance + ". Per account: ");
		
		for (int i=0; i < numberOfAccounts ;i++) {
			
			log.append("\t" + "Account number: " + i + " has balance: " + balancePerAccount.get(i) + " with the following transactions: \n");
			for (Transaction tr: transactionsPerAccount.get(i)) {
				log.append("\t \t" + tr.toString() + "\n");
			}
			//log.append("\t \t" + transactionsPerAccount.get(i).toString() + "\n");
			//System.out.println("\t" + "Account number: " + i + " has balance: " + balancePerAccount.get(i) + " with the following transactions: ");
			//System.out.println("\t \t" + transactionsPerAccount.get(i).toString());
		}
		log.append("End of customer \n");
		logger.debug(log.toString());
		//System.out.println("End of customer");
		return customer;
		
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
			
			account = deposit(customer.getCustomerID(),account.getAccountId(), initialCredit);
			//account.setBalance(initialCredit);
			
			list.add(account);
			
			customer.setAccounts(list);
			customerDAO.saveAndFlush(customer);
			logger.debug("New account added for customer = " + customer);
			return customer;
			
		}
		
	}
	
	@Override
	public Account deposit (Long customerID, int accountId, double amount) throws CustomerNotFoundException {
		Account account = getCustomerById(customerID).getAccounts().get(accountId);
		 double newBlanace = account.getBalance() + amount;
		 account.setBalance(newBlanace);
		 
		 transactions.addNewTransaction(customerID,account.getAccountId(), TransactionType.DEPOSIT, amount, newBlanace);
		 logger.debug(amount + " deposit for accountId = " + account.getAccountId() + " of customerID = " + customerID);
		 
		 return account;
	 }

	@Override
	public Account withdraw (Long customerID, int accountId, double amount) throws CustomerNotFoundException{
			Account account = getCustomerById(customerID).getAccounts().get(accountId);
	    	
	    	if (account.getBalance() >= amount) {
	    		 double newBlanace = account.getBalance() - amount;
	    		 account.setBalance(newBlanace);
	    		 
	    		 transactions.addNewTransaction(customerID, account.getAccountId(), TransactionType.WITHDRAWAL, amount, newBlanace);
	    		 
	    		 logger.debug(amount + " withdrawal for accountId = " + account.getAccountId() + " of customerID = " + customerID);
	    		return account;
	    	}
	    	else {
	    		logger.info("customerID = " + customerID + " has no balance to perform this withdrawal");
	    		System.out.println("Your balance is not enough to perform this withdrawal");
	    		return null; //as a possible check that the withdrawal was  never completed. (or custom exception)
	    	}
	    	
	    }

}
