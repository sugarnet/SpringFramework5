package com.dss.data.jpa.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dss.data.jpa.app.service.CustomerService;
import com.dss.data.jpa.app.view.xml.CustomerList;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public CustomerList list() {
		return new CustomerList(customerService.findAll());
	}
}
