package com.dss.di.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dss.di.app.model.service.GenericService;

@Controller
public class IndexController {
	
	// dependency injection by attribute
	// @Autowired
	// I need to define Qualifier because there are two classes implementing GenericService.
	// Avoid if we user @Primary in the class definition
	// @Qualifier("myService")
	// @Qualifier("myOtherService")
	private GenericService genericService;
	
	// dependency injection by constructor
	@Autowired
	public IndexController(/*@Qualifier("myOtherService")*/ GenericService genericService) {
		super();
		this.genericService = genericService;
	}



	@GetMapping({"", "/", "/index"})
	public String index(Model model) {
		model.addAttribute("data", genericService.operation());
		return "index";
	}

	// dependency injection by setter method
	/*@Autowired
	@Qualifier("myOtherService")
	public void setGenericService(GenericService genericService) {
		this.genericService = genericService;
	}*/
	
	
}
