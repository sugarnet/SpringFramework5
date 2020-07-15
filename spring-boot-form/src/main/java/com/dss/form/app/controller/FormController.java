package com.dss.form.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dss.form.app.domain.User;

@Controller
public class FormController {
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "User Form");
		return "form";
	}
	
	@PostMapping("/form")
	public String process(@Valid User user, BindingResult bindingResult, Model model) {
		/*public String process(Model model,
				@RequestParam(name="username") String username,
				@RequestParam String password,
				@RequestParam String email) {*/
		
		// if we receive request params we can set to the fields manually. But we can use directly an user object.
		// It's necessary that the fields in the form have the same name of attributes of the class
		/*User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);*/
		
		model.addAttribute("title", "Result Form");
		/*model.addAttribute("username", username);
		model.addAttribute("password", password);
		model.addAttribute("email", email);*/
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
		}
		
		model.addAttribute("user", user);
		
		
		return "result";
	}

}
