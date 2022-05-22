package com.demo.myproject.endpoints.webapp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.demo.myproject.entities.Transaction;

public class TransactionsPerAccountDTO implements Serializable {

	private int accountId;
	
	private List<Transaction> list = new ArrayList<Transaction>();

	public TransactionsPerAccountDTO() {
		
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public List<Transaction> getList() {
		return list;
	}

	public void setList(List<Transaction> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "TransactionsPerAccountDTO [accountId=" + accountId + ", list=" + list + "]";
	}
	
	
}
