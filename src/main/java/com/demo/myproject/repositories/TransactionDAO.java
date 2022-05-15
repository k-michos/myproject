package com.demo.myproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.myproject.entities.Transaction;

public interface TransactionDAO extends JpaRepository<Transaction, Long> {

	public List<Transaction> findAllTransactionsByCustomerID (Long customerID);
}
