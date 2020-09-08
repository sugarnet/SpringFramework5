package com.dss.data.jpa.app.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.data.jpa.app.entity.Bill;
import com.dss.data.jpa.app.entity.BillItem;
import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.entity.Product;
import com.dss.data.jpa.app.service.CustomerService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/bills")
@SessionAttributes("bill")
public class BillController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/details/{id}")
	public String details(@PathVariable Long id, Model model, RedirectAttributes flash) {

		final Bill bill = customerService.fetchByIdWithCustomerWithBillItemWithProduct(id); // customerService.findBillById(id);
		
		if (Objects.isNull(bill)) {
			flash.addFlashAttribute("error", "Bill not found!");
			return "redirect:/customers/list";
		}

		model.addAttribute("bill", bill);
		model.addAttribute("title", "Bill ".concat(bill.getDescription()));

		return "modules/bill/details";
	}
	
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

	@GetMapping(value = "/findProduct/{term}", produces = { "application/json" })
	public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
		List<Product> products = customerService.findByName(term);
		return products;
	}

	@PostMapping("/form")
	public String saveBill(@Valid Bill bill, BindingResult result, Model model,  @RequestParam(name = "item_id[]", required = false) Long[] itemsId,
			@RequestParam(name = "quantity[]", required = false) Integer[] quantities, RedirectAttributes flash, SessionStatus status) {
		
		if (result.hasErrors()) {
			model.addAttribute("title", "Create Bill");
			return "modules/bill/form";
		}
		
		if (Objects.isNull(itemsId) || itemsId.length < 0) {
			model.addAttribute("error", "The bill has no items!");
			model.addAttribute("title", "Create Bill");
			return "modules/bill/form";
		}

		for (int i = 0; i < itemsId.length; i++) {
			Product product = customerService.findProductById(itemsId[i]);
			
			BillItem billItem = new BillItem();
			billItem.setProduct(product);
			billItem.setQuantity(quantities[i]);
			
			bill.addBillItem(billItem);
		}
		
		customerService.saveBill(bill);
		
		status.setComplete();
		
		flash.addFlashAttribute("success", "Bill created!");
		
		return "redirect:/customers/details/" + bill.getCustomer().getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {
		
		Bill bill = customerService.findBillById(id);
		
		if (Objects.nonNull(bill)) {
			customerService.deleteBill(id);
			flash.addFlashAttribute("success", "Bill deleted!");
			return "redirect:/customers/details/" + bill.getCustomer().getId();
		}
		
		flash.addFlashAttribute("error", "Bill not found!");
		return "redirect:/customers/list";
	}

}
