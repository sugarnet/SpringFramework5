package com.dss.data.jpa.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.service.CustomerService;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("title", "Customers List");
		model.addAttribute("customers", customerService.findAll());
		return "customer/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", new Customer());
		return "customer/form";
	}
	
	@GetMapping("/form/{id}")
	public String edit(@PathVariable Long id, Model model) {

		Customer customer = null;
		if(id > 0) {
			customer = customerService.findById(id);
		} else {
			return "redirect:/customers/list";
		}
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", customer);
		return "customer/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Customer customer, BindingResult result, Model model, SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Customer Form");
			return "customer/form";
		}
		customerService.save(customer);
		sessionStatus.setComplete();
		
		return "redirect:/customers/list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		if(id > 0) {
			customerService.delete(id);
		}
		return "redirect:/customers/list";
	}
}
