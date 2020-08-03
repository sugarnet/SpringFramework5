package com.dss.error.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dss.error.app.domain.User;
import com.dss.error.app.exception.UserNotFoundException;
import com.dss.error.app.service.UserService;

@Controller
public class AppController {

	@Autowired
	private UserService userService;

	@GetMapping("/index")
	public String index() {
		// float div = 23 / 0;
		Integer value = Integer.parseInt("10x");
		System.out.println("Result: " + value);
		return "index";
	}

	@GetMapping("/see/{id}")
	public String see(@PathVariable Integer id, Model model) {
		// final User user = userService.getById(id);
		final User user = userService.getOne(id).orElseThrow(() -> new UserNotFoundException(id.toString()));

		model.addAttribute("user", user);
		model.addAttribute("title", "Details: " + user.getName());

		return "see";
	}
}
