package com.demo.myproject.web.endpoints;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;


import com.demo.myproject.entities.Account;
import com.demo.myproject.entities.Customer;
import com.demo.myproject.services.Customers;
import com.demo.myproject.utils.Constants.AccountType;

import com.demo.myproject.utils.DemoApplication;

@SpringBootTest
@ContextConfiguration(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class CustomerRestEndpointIT {

    @MockBean
    Customers mockCustomers;
   
    @Autowired
    private MockMvc mockMvc;
    
    @Test
	public void getAllCustomerss() throws Exception{	
    	ArrayList<Customer> list = new ArrayList<Customer>();
    	
    	list.add(createDummyCustomer());
    	
    	when(mockCustomers.getAllCustomers()).thenReturn(list);
    	this.mockMvc.perform(get("/customers/all")).andDo(print()).andExpect(status().isOk());

    }

	@Test
	public void getCustomerById() throws Exception{
		
		Customer customer = createDummyCustomer();
		
		when(mockCustomers.getCustomerById(customer.getCustomerID())).thenReturn(customer);
		
		this.mockMvc.perform(get("/customers/" + customer.getCustomerID().toString())).andDo(print()).andExpect(status().isOk());
		
	}
    
    @Test
    public void addNewCustomer() throws Exception{
    	
    	Customer customer = createDummyCustomer();
    	
    	when(mockCustomers.addNewCustomer(customer.getName(), customer.getSurname())).thenReturn(customer);
    	
    	LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("name", customer.getName());
    	params.add("surname",  customer.getSurname());
    	
    	this.mockMvc.perform(post("/customers/add").params(params)).andDo(print()).andExpect(status().isOk());
      
    }
    
    @Test
    public void updateCustomer() throws Exception {
    	
    	Customer customer = createDummyCustomer();
    	Customer updatedCustomer = createDummyCustomer();
    	updatedCustomer.setName("Testing2");
    	updatedCustomer.setSurname("Testing2");
    	
    	when(mockCustomers.updateCustomer(customer.getCustomerID(), updatedCustomer.getName(), updatedCustomer.getSurname())).thenReturn(updatedCustomer);
    	
    	LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("id", updatedCustomer.getCustomerID().toString());
    	params.add("newName", updatedCustomer.getName());
    	params.add("newSurname",  updatedCustomer.getSurname());
    	
    	this.mockMvc.perform(put("/customers/update").params(params)).andDo(print()).andExpect(status().isOk());
    	
}
    
    @Test
    public void deleteCustomer() throws Exception  {
    	
    	Customer customer = createDummyCustomer();
    	
    	doNothing().when(mockCustomers).deleteCustomer(customer.getCustomerID());
    	
    	LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("id", customer.getCustomerID().toString());
    	
    	this.mockMvc.perform(delete("/customers/delete").params(params)).andDo(print()).andExpect(status().isOk());
	  	
    }
    
    @Test
    public void getCustomerInfoById () throws Exception {
    	
    	Customer customer = createDummyCustomer();
    	
    	when(mockCustomers.getCustomerInfoById(customer.getCustomerID())).thenReturn(customer);;
    	
    	//when(endpoint).getCustomerInfoById(customer.getCustomerID());

		//when(mockCustomers.getCustomerById(customer.getCustomerID())).thenReturn(customer);
		
		this.mockMvc.perform(get("/customers/getCustomerInfo/" + customer.getCustomerID().toString())).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void newCurrentAccount() throws Exception {
    	
    	Customer customer = createDummyCustomer();
    	List<Account> list = customer.getAccounts();
    	Account account = new Account();
    	account.setAccountId(list.size());
		account.setAccountType(AccountType.CURRENT.toString());
		account.setBalance(1000);
		
		list.add(account);
		customer.setAccounts(list);
    	
		when(mockCustomers.newCurrentAccount(customer.getCustomerID(), account.getBalance())).thenReturn(customer);
		
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("customerID", customer.getCustomerID().toString());
    	params.add("initialCredit", Double.valueOf(account.getBalance()).toString() );
    	
    	this.mockMvc.perform(put("/customers/newCurrentAccount").params(params)).andDo(print()).andExpect(status().isOk());
    	
    }
    
    @Test
    public void deposit() throws Exception {
    	Customer customer = createDummyCustomer();
    	Account account = customer.getAccounts().get(0);
    	double amount = 1;
    	account.setBalance(account.getBalance() + amount);
    	when(mockCustomers.deposit(customer.getCustomerID(), account.getAccountId(), amount)).thenReturn(account);
    	
    	LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("customerID", customer.getCustomerID().toString());
    	params.add("accountId", Integer.toString(account.getAccountId()));
    	params.add("amount", Double.valueOf(amount).toString());
   	
    	this.mockMvc.perform(put("/customers/deposit").params(params)).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void withdraw() throws Exception {
    	Customer customer = createDummyCustomer();
    	Account account = customer.getAccounts().get(0);    	
    	double amount = 1;
    	account.setBalance(account.getBalance() - amount);
    	when(mockCustomers.withdraw(customer.getCustomerID(), account.getAccountId(),amount)).thenReturn(account);
    	
    	LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("customerID", customer.getCustomerID().toString());
    	params.add("accountId", Integer.toString(account.getAccountId()));
    	params.add("amount", Double.valueOf(amount).toString());
    	
    	this.mockMvc.perform(put("/customers/withdraw").params(params)).andDo(print()).andExpect(status().isOk());
    }
    
    private Customer createDummyCustomer() {
    	Customer customer = new Customer();
    	customer.setCustomerID(1L);
    	customer.setName("Testing");
    	customer.setSurname("Testing");
    	customer.getAccounts().add(new Account(0,10.0,AccountType.CURRENT.toString()));
    	return customer;
    }
   
}