package com.dss.web.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/params")
public class ParamsController {
	
	@GetMapping("/")
	public String index() {
		
		return "/params/index";
	}
	
	@GetMapping("/string")
	public String param(
			@RequestParam(
					name = "text",
					required = false,
					defaultValue = "Default value") String text, 
			Model model) {
		
		model.addAttribute("result", "The received value is: " + text);
		
		return "/params/see";
	}
	
	@GetMapping("/mix-params")
	public String param(
			@RequestParam(
					name = "text",
					required = false,
					defaultValue = "Default value") String text,
			@RequestParam Integer number,
			Model model) {
		
		model.addAttribute("result", "The received text value is: '" + text 
				+ "' and the number value is '" + number + "'");
		
		return "/params/see";
	}
	
	@GetMapping("/mix-params-request")
	public String param(HttpServletRequest request, Model model) {
		
		String text = request.getParameter("text");
		
		Integer number = null;
		try {
			number = Integer.valueOf(request.getParameter("number"));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			number = 0;
		}
		
		model.addAttribute("result", "The received text value is: '" + text 
				+ "' and the number value is '" + number + "'");
		
		return "/params/see";
	}

}
