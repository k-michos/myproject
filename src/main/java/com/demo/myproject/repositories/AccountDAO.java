package com.demo.myproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.myproject.entities.Account;


public interface AccountDAO extends JpaRepository<Account, Long>{

}
