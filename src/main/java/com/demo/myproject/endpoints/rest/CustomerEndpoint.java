package com.demo.myproject.endpoints.rest;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.myproject.entities.Account;
import com.demo.myproject.entities.Customer;
import com.demo.myproject.services.Customers;
import com.demo.myproject.utils.CustomerNotFoundException;


@RestController
@RequestMapping("/customers")
public class CustomerEndpoint {
	
		Logger logger = LoggerFactory.getLogger(this.getClass());

		@Autowired
		private Customers customers;
				
		@GetMapping("/all")
		public List<Customer> getAllCustomerss(){
			logger.info("CustomerRestEndpoint::getAllCustomers method revoked.");
			return customers.getAllCustomers();
		}
		
		@GetMapping("/{id}")
		public Customer getCustomerById(@PathVariable(required = true) Long id){
			logger.info("CustomerRestEndpoint::getCustomerById method revoked.");
			try {
				return customers.getCustomerById(id);}
			catch (CustomerNotFoundException ex) {
				logger.error(ex.getMessage(), ex);
				return null;
			}
		}
		
		@PostMapping("/add")
		public Customer addNewCustomer(@RequestParam(required = true) String name, @RequestParam(required = true) String surname) {
			logger.info("CustomerRestEndpoint::addNewCustomer method revoked.");
			return customers.addNewCustomer(name, surname);
		}
		
		@PutMapping("/update")
		public Customer updateCustomer(@RequestParam(required = true) Long id, @RequestParam(required = false) String newName, @RequestParam(required = false) String newSurname) {
			logger.info("CustomerRestEndpoint::updateCustomer method revoked.");
			try {
				return customers.updateCustomer(id, newName, newSurname);
			}
			catch (CustomerNotFoundException ex) {
				logger.error(ex.getMessage(), ex);
				return null;
			}
			
		}
		
		@DeleteMapping("/delete")
		public boolean deleteCustomer(@RequestParam(required = true) Long id) {
			logger.info("CustomerRestEndpoint::deleteCustomer method revoked.");
			try {
				customers.deleteCustomer(id);
				return true;
			}
			catch (CustomerNotFoundException ex) {
				logger.error(ex.getMessage(), ex);
				return false;
			}
		}
		
		@PutMapping("/newCurrentAccount")
		public Customer newCurrentAccount (@RequestParam(required = true) Long customerID, @RequestParam(required = true)double initialCredit) {
			logger.info("CustomerRestEndpoint::newCurrentAccount method revoked.");
			try {
				return customers.newCurrentAccount(customerID, initialCredit);
			}
			catch (CustomerNotFoundException ex) {
				logger.error(ex.getMessage(), ex);
				return null;
			}
		}
		
		@GetMapping("/getCustomerInfo/{customerID}")
		public Customer getCustomerInfoById (@PathVariable(required = true) Long customerID) {
			logger.info("CustomerRestEndpoint::getCustomerInfoById method revoked.");
			try {
				return customers.getCustomerInfoById(customerID);
			}
			catch(CustomerNotFoundException ex) {
				logger.error(ex.getMessage(), ex);
				return null;
			}	
		}
		
		@PutMapping("/deposit")
		public Account deposit (@RequestParam(required = true) Long customerID, @RequestParam(required = true) int accountId, @RequestParam(required = true) double amount) {
			logger.info("CustomerRestEndpoint::deposit method revoked.");
			try {
				return customers.deposit(customerID, accountId, amount);
			}
			catch (CustomerNotFoundException ex) {
				logger.error(ex.getMessage(), ex);
				return null;
			}
		}
		
		@PutMapping("/withdraw")
		public Account withdraw (@RequestParam(required = true) Long customerID, @RequestParam(required = true) int accountId, @RequestParam(required = true) double amount) {
			logger.info("CustomerRestEndpoint::withdraw method revoked.");
			try {
				
				return customers.withdraw(customerID, accountId, amount);
			}
			catch (CustomerNotFoundException ex) {
				System.out.println(ex);
				return null;
			}
		}	
}
