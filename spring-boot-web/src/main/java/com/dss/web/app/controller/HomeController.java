package com.dss.web.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		// return "redirect:/app/index"; // reset the petition
		// return "redirect:http://www.google.com"; we use it with address like www.google.com
		return "forward:/app/index"; // we user only with address into the project. We can't use with address like www... don't change the address bar
	}
}
