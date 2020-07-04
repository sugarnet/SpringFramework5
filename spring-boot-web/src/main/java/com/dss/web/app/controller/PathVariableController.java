package com.dss.web.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vars")
public class PathVariableController {
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title", "Send values from @PathVariable");
		return "/vars/index";
	}

	@GetMapping("/string/{text}")
	public String vars(@PathVariable String text, Model model) {
		
		model.addAttribute("title", "View received value from @PathVariable");
		model.addAttribute("result", "Received value: " + text);
		
		return "/vars/see";
	}
	
	@GetMapping("/string/{text}/{number}")
	public String vars(@PathVariable String text, 
			@PathVariable Integer number, Model model) {
		
		model.addAttribute("title", "View received value from @PathVariable");
		model.addAttribute("result", "Received text value: " + text 
				+ ", number value: " + number);
		
		return "vars/see";
	}
}
