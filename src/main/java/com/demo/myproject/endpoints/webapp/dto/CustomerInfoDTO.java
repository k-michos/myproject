package com.demo.myproject.endpoints.webapp.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.demo.myproject.entities.Customer;
import com.demo.myproject.entities.Transaction;

public class CustomerInfoDTO implements Serializable {
	
	private Customer customer;
	
	private double initialCredit;
	
	private List<Transaction> list;
	
	private HashMap<Integer, List<Transaction>> transactionsPerAccount;
	
	
	private double totalBalance =0;
	
	public CustomerInfoDTO() {
		
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

	public List<Transaction> getList() {
		return list;
	}

	public void setList(List<Transaction> list) {
		this.list = list;
	}

	public HashMap<Integer, List<Transaction>> getTransactionsPerAccount() {
		return transactionsPerAccount;
	}

	public void setTransactionsPerAccount(HashMap<Integer, List<Transaction>> transactionsPerAccount) {
		this.transactionsPerAccount = transactionsPerAccount;
	}

	public double getTotalBalance() {
		return totalBalance;
	}


	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	@Override
	public String toString() {
		return "CustomerInfoDTO [customer=" + customer + ", initialCredit=" + initialCredit + ", list=" + list
				+ ", transactionsPerAccount=" + transactionsPerAccount + ", totalBalance=" + totalBalance + "]";
	}

	



	


}
