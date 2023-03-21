package com.shopme.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/register")
	public String showRegisterCustomer(Model model) {
		List<Country> listCountries = customerService.listAllCountry();
		
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "Customer Registration");
		model.addAttribute("customer", new Customer());
		
		return "register/register_form";
	}
	
	// create customer
	@PostMapping("/create_customer") 
	public String createCustomer(Customer customer, Model model) {
		customerService.registerCustomer(customer);
		model.addAttribute("pageTitle", "Registration Succeeded");
		return "register/register_success";
	}

}
