package com.dss.data.jpa.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dss.data.jpa.app.dao.CustomerDAO;
import com.dss.data.jpa.app.entity.Customer;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerDAO customerDAO;

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("title", "Customers List");
		model.addAttribute("customers", customerDAO.findAll());
		return "customer/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", new Customer());
		return "customer/form";
	}
	
	@PostMapping("/form")
	public String save(Customer customer) {
		customerDAO.save(customer);
		return "redirect:/customers/list";
	}
}
