package com.demo.myproject.services;

import java.util.List;

import com.demo.myproject.entities.Transaction;
import com.demo.myproject.utils.CustomerNotFoundException;
import com.demo.myproject.utils.TransactionNotFoundException;
import com.demo.myproject.utils.Constants.TransactionType;

public interface Transactions {

	public List<Transaction> getAllTransactions();
	
	public Transaction getTransactionById(Long id) throws TransactionNotFoundException;
	
	public Transaction addNewTransaction (Long customerID, int accountID, TransactionType transactionType, double amount, double newBalance);
	
	public List<Transaction> getAllTransactionsByCustomerID (Long customerID) throws CustomerNotFoundException; 
	
	public List<Transaction> getAllTransactionsPerAccount(int accountID, List<Transaction> listOfAllCustomerTransactions);
}
