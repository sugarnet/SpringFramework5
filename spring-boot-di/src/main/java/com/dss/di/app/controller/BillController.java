package com.dss.di.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dss.di.app.model.domain.Bill;

@Controller
@RequestMapping("/bill")
public class BillController {
	
	@Autowired
	private Bill bill;

	@GetMapping("/see")
	public String see(Model model) {
		model.addAttribute("title", "Test Dependency Injection");
		model.addAttribute("bill", bill);
		return "bill/see";
	}
	
}
