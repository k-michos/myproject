package com.demo.myproject.endpoints.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.myproject.endpoints.webapp.dto.CustomerInfoWrapper;
import com.demo.myproject.entities.Customer;
import com.demo.myproject.services.Customers;
import com.demo.myproject.utils.CustomerNotFoundException;

@Controller
@RequestMapping("/webapp/customers")
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Customers customers;
			
	@GetMapping("/all")
	public String getAllCustomerss(Model model){
		logger.info("CustomerController::getAllCustomers method revoked.");
				
		model.addAttribute("allCustomers", customers.getAllCustomers());
		model.addAttribute("customer", new Customer());
		
		return "customers";
	}
	
	@GetMapping("/{customerID}")
	public String getCustomerById(Model model, RedirectAttributes redirectAttributes, @PathVariable Long customerID, @ModelAttribute Customer customer) throws CustomerNotFoundException {
		logger.info("CustomerController::getCustomerById method revoked.");
		
		Customer cust = customers.getCustomerById(customerID);
		model.addAttribute("customer", cust);
		System.out.println(model);
		//redirectAttributes.addFlashAttribute("customer", cust);
		return "customer";
	}
	
	@PostMapping("/add")
	public RedirectView  addNewCustomer(RedirectAttributes redirectAttributes, @ModelAttribute Customer customer){
		logger.info("CustomerController::addNewCustomer method revoked.");
		
		Customer newCustomer = customers.addNewCustomer(customer.getName(), customer.getSurname());
		String message="Customer <b>"+newCustomer.getName()+" "+newCustomer.getSurname()+"</b> added successfully!";
		RedirectView redirectView=new RedirectView("/webapp/customers/all",true);
		redirectAttributes.addFlashAttribute("userMessage",message);
		return redirectView;
	}
	
	@PostMapping("/update/")
	public RedirectView updateCustomer(RedirectAttributes redirectAttributes, @ModelAttribute Customer customer) throws CustomerNotFoundException{
		logger.info("CustomerController::updateCustomer method revoked.");
		System.out.println("HERE");
	
		customers.updateCustomer(customer.getCustomerID(), customer.getName(), customer.getSurname());
		
		String message="Customer <b>"+customer.getName()+" "+customer.getSurname()+"</b> updated successfully!";
		RedirectView redirectView=new RedirectView("/webapp/customers/all",true);
		redirectAttributes.addFlashAttribute("userMessage",message);
		return redirectView;
	}
	
	@PostMapping("/delete")
	public RedirectView deleteCustomer (RedirectAttributes redirectAttributes,  @ModelAttribute Customer customer) throws CustomerNotFoundException{
		//public RedirectView deleteCustomer (RedirectAttributes redirectAttributes, @PathVariable Long customerID, @ModelAttribute Customer customer) throws CustomerNotFoundException{
				
		logger.info("CustomerController::deleteCustomer method revoked.");
		
		//customer = customers.getCustomerById(customer.getCustomerID());
		//System.out.println("HERE2"+ customer);
		customers.deleteCustomer(customer.getCustomerID());

		
		String message="Deleted Customer with customerID <b>" + customer.getCustomerID() + "</b>.";
		RedirectView redirectView=new RedirectView("/webapp/customers/all",true);
		redirectAttributes.addFlashAttribute("userMessage",message);
		return redirectView;
	}
	
	@GetMapping("/getCustomerInfo/{customerID}")
	public String getCustomerInfoById (Model model, RedirectAttributes redirectAttributes, @PathVariable Long customerID) throws CustomerNotFoundException {
		logger.info("CustomerRestEndpoint::getCustomerInfoById method revoked.");
		
		Customer customer = customers.getCustomerInfoById(customerID);
		CustomerInfoWrapper customerInfoWrapper = new CustomerInfoWrapper();
		customerInfoWrapper.setCustomer(customer);
		model.addAttribute("customerInfoWrapper", customerInfoWrapper);
		//double initialCredit=1;
		
		//model.addAttribute("customer", customer);
		//model.addAttribute("listOfAccounts", customer.getAccounts());
		//model.addAttribute("initialCredit", new Double(initialCredit));
		//redirectAttributes.addAttribute("initialCredit", initialCredit);
		//redirectAttributes.addFlashAttribute("initialCredit", initialCredit);
		
		//model.addAttribute();
		System.out.println(model);
		return "customerInfo";
		
	}
	
	@PostMapping("/newCurrentAccount/{customerID}")
	public RedirectView newCurrentAccount (Model model, RedirectAttributes redirectAttributes, @PathVariable Long customerID, @ModelAttribute CustomerInfoWrapper customerInfoWrapper) throws CustomerNotFoundException {
		logger.info("CustomerRestEndpoint::newCurrentAccount method revoked.");
		model.addAttribute("customerInfoWrapper", customerInfoWrapper);
//		double initialCredit=0;
//		model.addAttribute("initialCredit", initialCredit);
		System.out.println(model);
		System.out.println(redirectAttributes);
		
		//double initialCredit= Double.parseDouble(customerInfoWrapper.getInitialCredit());
				
		customers.newCurrentAccount(customerID, customerInfoWrapper.getInitialCredit());
		
		String message="Current Account added!";
		RedirectView redirectView=new RedirectView("/webapp/customers/getCustomerInfo/" + customerID,true);
		redirectAttributes.addFlashAttribute("userMessage",message);
		return redirectView;
		

		//model.addAttribute("customer", customer);
		//model.addAttribute("initialCredit", initialCredit);
		//model.addAttribute();
		//return "customerInfo";
		
	}
	
//	@PostMapping("/transactions/{customerID}")
//	public String getTransactionsPerAccount (Model model, @PathVariable Long customerID) {
//		
//	}
}
