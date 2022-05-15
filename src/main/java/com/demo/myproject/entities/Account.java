package com.demo.myproject.entities;

import java.io.Serializable;


public class Account implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int accountId;
	
    private double balance;

    private String accountType;
    
	public Account() {
		
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	


	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", accountType=" + accountType + "]";
	}

	
}
