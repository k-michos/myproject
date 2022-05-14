package com.demo.myproject.web.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.myproject.entities.Customer;
import com.demo.myproject.services.Customers;
import com.demo.myproject.utils.CustomerNotFoundException;


@RestController
@RequestMapping("/customers")
public class CustomerRestEndpoint {

		@Autowired
		private Customers customers;
		
		@GetMapping("/all")
		public List<Customer> getAllCustomerss(){
			return customers.getAllCustomers();
		}
		
		@GetMapping("/{id}")
		public Customer getCustomerById(@PathVariable(required = true) Long id){
			try {
				return customers.getCustomerById(id);}
			catch (CustomerNotFoundException ex) {
				System.out.println(ex);
				return null;
			}
		}
		
		@PostMapping("/add")
		public Customer addNewCustomer(@RequestParam(required = true) String name, @RequestParam(required = true) String surname) {
			return customers.addNewCustomer(name, surname);
		}
		
		@PutMapping("/update")
		public Customer updateCustomer(@RequestParam(required = true) Long id, @RequestParam(required = false) String newName, @RequestParam(required = false) String newSurname) {
			try {
				return customers.updateCustomer(id, newName, newSurname);
			}
			catch (CustomerNotFoundException ex) {
				System.out.println(ex);
				return null;
			}
			
		}
		
		@DeleteMapping("/delete")
		public boolean deleteCustomer(@RequestParam(required = true) Long id) {
			try {
				customers.deleteCustomer(id);
				return true;
			}
			catch (CustomerNotFoundException ex) {
				System.out.println(ex);
				return false;
			}
		}
		
		@PutMapping("/newCurrentAccount")
		public Customer newCurrentAccount (@RequestParam(required = true) Long customerID, @RequestParam(required = true)double initialCredit) {
			try {
				return customers.newCurrentAccount(customerID, initialCredit);
			}
			catch (CustomerNotFoundException ex) {
				System.out.println(ex);
				return null;
			}
		}
		
	
}
