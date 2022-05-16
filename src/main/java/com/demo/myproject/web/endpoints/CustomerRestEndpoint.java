package com.demo.myproject.web.endpoints;

import java.util.HashMap;
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
import com.demo.myproject.entities.Transaction;
import com.demo.myproject.services.Customers;
import com.demo.myproject.services.Transactions;
import com.demo.myproject.utils.CustomerNotFoundException;


@RestController
@RequestMapping("/customers")
public class CustomerRestEndpoint {
	
		Logger logger = LoggerFactory.getLogger(this.getClass());

		@Autowired
		private Customers customers;
		
		@Autowired
		private Transactions transactions;
		
		@GetMapping("/all")
		public List<Customer> getAllCustomerss(){
			logger.info("CustomerRestEndpoint::getAllCustomerss method revoked.");
			return customers.getAllCustomers();
		}
		
		@GetMapping("/{id}")
		public Customer getCustomerById(@PathVariable(required = true) Long id){
			try {
				logger.info("CustomerRestEndpoint::getCustomerById method revoked.");
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
			try {
				logger.info("CustomerRestEndpoint::updateCustomer method revoked.");
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
		public void getCustomerInfoById (@PathVariable(required = true) Long customerID) {
			logger.info("CustomerRestEndpoint::getCustomerInfoById method revoked.");
			try {
				
			Customer customer = customers.getCustomerById(customerID);
			
			List<Transaction> list = transactions.getAllTransactionsByCustomerID(customerID);
						
			HashMap<Integer, List<Transaction>> transactionsPerAccount = new HashMap<>();
			
			HashMap<Integer, Double> balancePerAccount = new HashMap<>();
			
			double totalBalance =0;
			
			int numberOfAccounts = customer.getAccounts().size();
			
			for (int i=0; i < numberOfAccounts ;i++) { 
				transactionsPerAccount.put(i, transactions.getAllTransactionsPerAccount(i, list));
				
				double balance = customer.getAccounts().get(i).getBalance();
				totalBalance = totalBalance + balance;
				balancePerAccount.put(i, balance);
				
			}
			
			StringBuilder log = new StringBuilder();
			log.append("\n");
			log.append("The customerID you gave belongs to: " + customer.getName() + " " + customer.getSurname() + 
					" and the total balance of all his accounts is: " + totalBalance + ". Per account: \n");
			
			
			
//			logger.debug("The customerID you gave belongs to: " + customer.getName() + " " + customer.getSurname() + 
//					" and the total balance of all his accounts is: " + totalBalance + ". Per account: ");
			
			for (int i=0; i < numberOfAccounts ;i++) {
				
				log.append("\t" + "Account number: " + i + " has balance: " + balancePerAccount.get(i) + " with the following transactions: \n");
				log.append("\t \t" + transactionsPerAccount.get(i).toString() + "\n");
				//System.out.println("\t" + "Account number: " + i + " has balance: " + balancePerAccount.get(i) + " with the following transactions: ");
				//System.out.println("\t \t" + transactionsPerAccount.get(i).toString());
			}
			log.append("End of customer \n");
			logger.debug(log.toString());
			//System.out.println("End of customer");
			}
			catch(CustomerNotFoundException ex) {
				logger.error(ex.getMessage(), ex);
			}
			
		}
		
		@PutMapping("/deposit")
		public Account deposit (@RequestParam(required = true) Long customerID, @RequestParam(required = true) int accountId, @RequestParam(required = true) double amount) {
			logger.info("CustomerRestEndpoint::deposit method revoked.");
			try {
				//Customer customer = customers.getCustomerById(customerID);
				return customers.deposit(customerID, customers.getCustomerById(customerID).getAccounts().get(accountId), amount);
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
				//Customer customer = customers.getCustomerById(customerID);
				return customers.withdraw(customerID, customers.getCustomerById(customerID).getAccounts().get(accountId), amount);
			}
			catch (CustomerNotFoundException ex) {
				System.out.println(ex);
				return null;
			}
		}
		
	
}
