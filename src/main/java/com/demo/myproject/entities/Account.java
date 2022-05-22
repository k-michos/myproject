package com.demo.myproject.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.demo.myproject.utils.Constants.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account { 
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int accountId;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "customerID", nullable = false)
	private Customer customer;
	
	@Column(nullable = false)
    private double balance;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private AccountType accountType;
    
	public Account() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountId=" + accountId + ", balance=" + balance
				+ ", accountType=" + accountType + "]";
	}




}
