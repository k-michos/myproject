package com.demo.myproject.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.demo.myproject.utils.Constants.TransactionType;

@Entity
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Long customerID;
	
	@Column(nullable = false)
	private int accountID;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column(nullable = false)
	private double amount;
	
	@Column(nullable = false)
	private double newBalance;
	
	@Column(nullable = false)
	private Timestamp timestamp;

	public Transaction() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	

	public double getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", customerID=" + customerID + ", accountID=" + accountID
				+ ", transactionType=" + transactionType + ", amount=" + amount + ", newBalance=" + newBalance
				+ ", timestamp=" + timestamp + "]";
	}

	
	
	
}
