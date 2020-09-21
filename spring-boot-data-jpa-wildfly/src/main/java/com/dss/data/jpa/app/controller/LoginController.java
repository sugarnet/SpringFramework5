package com.dss.data.jpa.app.controller;

import java.security.Principal;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping({ "/", "/login" })
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash) {

		if (Objects.nonNull(principal)) {
			flash.addFlashAttribute("info", "User logged!");
			return "redirect:/customers/";
		}

		if (Objects.nonNull(error)) {
			model.addAttribute("error", "User or Password incorrect. Try again!");
		}
		
		if (Objects.nonNull(logout)) {
			model.addAttribute("info", "Session closed!");
		}

		return "login";
	}

}
