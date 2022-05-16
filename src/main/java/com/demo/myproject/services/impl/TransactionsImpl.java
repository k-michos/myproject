package com.demo.myproject.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.myproject.entities.Customer;
import com.demo.myproject.entities.Transaction;
import com.demo.myproject.repositories.CustomerDAO;
import com.demo.myproject.repositories.TransactionDAO;
import com.demo.myproject.services.Transactions;
import com.demo.myproject.utils.CustomerNotFoundException;
import com.demo.myproject.utils.TransactionNotFoundException;
import com.demo.myproject.utils.Constants.TransactionType;

@Service
public class TransactionsImpl implements Transactions {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;

	@Override
	public List<Transaction> getAllTransactions() {
		return 	transactionDAO.findAll();
	}

	@Override
	public Transaction getTransactionById(Long id) throws TransactionNotFoundException{

		Optional<Transaction> opt = transactionDAO.findById(id);
		
		if (!opt.isPresent()) {
			throw new TransactionNotFoundException(
	                "Could not find transaction with id = " + id);
		}
		else {
			return opt.get();
		}

	}

	@Override
	public List<Transaction> getAllTransactionsByCustomerID(Long customerID) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerDAO.findById(customerID);
		
		if (!opt.isPresent()) {
			throw new CustomerNotFoundException(
	                "Could not find customer with customerId = " + customerID);
		}
		else {
			List<Transaction> list = transactionDAO.findAllTransactionsByCustomerID(customerID);
			return list;
		}
		
//		try {
//			customers.getCustomerById(customerID);
//			List<Transaction> list = transactionDAO.findAllTransactionsByCustomerID(customerID);
//			return list;
//		}
//		catch(CustomerNotFoundException ex) {
//			System.out.println(ex);
//			return null;
//		}	
	}

	@Override
	public List<Transaction> getAllTransactionsPerAccount(int accountID, List<Transaction> listOfAllCustomerTransactions) {
		
		List<Transaction> listOfCustomerTransactionsPerAccount = new ArrayList<Transaction>();
		
		for (Transaction transaction : listOfAllCustomerTransactions) {
			if (transaction.getAccountID()==(accountID)) {
				listOfCustomerTransactionsPerAccount.add(transaction);
			}
		}
		
		return listOfCustomerTransactionsPerAccount;
	}
	
	@Override
	public Transaction addNewTransaction (Long customerID, int accountID, TransactionType transactionType, double amount, double newBalance) {
		
		Transaction transaction = new Transaction();
		transaction.setCustomerID(customerID);
		transaction.setAccountID(accountID);
		transaction.setTransactionType(transactionType);
		transaction.setAmount(amount);
		transaction.setNewBalance(newBalance);
		transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
		
		transactionDAO.saveAndFlush(transaction);
		return transaction;
	}
	
}
