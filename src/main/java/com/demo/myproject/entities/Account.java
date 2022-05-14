package com.demo.myproject.entities;

import java.io.Serializable;

import com.demo.myproject.utils.Constants.AccountType;

public class Account implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long accountId;
	
    private double balance;

    private String accountType;
    
	public Account() {
		
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
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
	
	 public void deposit (double amount) {
		 this.balance = this.balance + amount;
	 }

	    public void withdraw (double amount) {
	    	
	    	if (balance >= amount) {
	    		this.balance = this.balance - amount;
	    	}
	    	else {
	    		System.out.println("Your balance is not enough to perform this withdrawal");
	    	}
	    	
	    }

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", accountType=" + accountType + "]";
	}

	
}
