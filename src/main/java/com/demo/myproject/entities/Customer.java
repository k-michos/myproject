package com.demo.myproject.entities;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Customer { 

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerID;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	
	//@Column
	//@ElementCollection
    //@CollectionTable
	//@OneToMany(targetEntity = Account.class)
	@OneToMany(targetEntity = Account.class, mappedBy="customer")
	@Cascade(CascadeType.ALL)
	private List<Account> accounts = new ArrayList<Account>();
	
	public Customer() {
		
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
		account.setCustomer(this);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
		account.setCustomer(null);
	}

	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", name=" + name + ", surname=" + surname + ", accounts="
				+ accounts + "]";
	}
		
}
