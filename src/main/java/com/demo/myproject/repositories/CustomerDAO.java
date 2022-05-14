package com.demo.myproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.myproject.entities.Customer;

public interface CustomerDAO extends JpaRepository<Customer, Long> {

}
