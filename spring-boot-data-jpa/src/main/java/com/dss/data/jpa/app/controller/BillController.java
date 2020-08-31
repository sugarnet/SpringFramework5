package com.dss.data.jpa.app.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.data.jpa.app.entity.Bill;
import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.entity.Product;
import com.dss.data.jpa.app.service.CustomerService;

@Controller
@RequestMapping("/bills")
@SessionAttributes("bill")
public class BillController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/form/{customerId}")
	public String create(@PathVariable("customerId") Long customerId, Model model, RedirectAttributes flash) {

		final Customer customer = customerService.findById(customerId);

		if (Objects.isNull(customer)) {
			return "redirect:/customers/list";
		}

		final Bill bill = new Bill();
		bill.setCustomer(customer);

		model.addAttribute("bill", bill);
		model.addAttribute("title", "Create Bill");

		return "modules/bill/form";
	}
	
	@GetMapping(value = "/findProduct/{term}", produces = {"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
		List<Product> products = customerService.findByName(term);  
		return products;
	}

}
