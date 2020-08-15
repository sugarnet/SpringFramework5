package com.dss.data.jpa.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.service.CustomerService;
import com.dss.data.jpa.app.util.paginator.PageRender;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model) {
		
		Pageable pageable = PageRequest.of(page, 4);
		Page<Customer> customers = customerService.findAll(pageable);
		PageRender<Customer> pageRender = new PageRender<>("/customers/list", customers);
		
		model.addAttribute("title", "Customers List");
		model.addAttribute("customers", customers);
		model.addAttribute("page", pageRender);
		return "modules/customer/list";
	}

	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", new Customer());
		return "modules/customer/form";
	}

	@GetMapping("/form/{id}")
	public String edit(@PathVariable Long id, RedirectAttributes flash, Model model) {

		Customer customer = null;
		if (id > 0) {
			customer = customerService.findById(id);
		} else {
			flash.addFlashAttribute("error", "The id can't be less or equals than zero");
			return "redirect:/customers/list";
		}
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", customer);
		return "modules/customer/form";
	}

	@PostMapping("/form")
	public String save(@Valid Customer customer, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Customer Form");
			return "customer/form";
		}
		customerService.save(customer);
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", "Customer saved!");

		return "redirect:/customers/list";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			customerService.delete(id);
		}
		flash.addFlashAttribute("error", "The id can't be less or equals than zero");
		return "redirect:/customers/list";
	}
}
