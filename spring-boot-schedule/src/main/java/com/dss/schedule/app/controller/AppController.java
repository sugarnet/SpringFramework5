package com.dss.schedule.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@Value("${config.schedule.open}")
	private Integer open;
	
	@Value("${config.schedule.close}")
	private Integer close;

	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("title", "Welcome to Client Support");
		return "index";
	}
	
	@GetMapping("/closed")
	public String closed(Model model) {
		model.addAttribute("title", "Closed!!!");
		
		StringBuilder sb = new StringBuilder();
		sb.append("Closed! Please visit us from ");
		sb.append(open);
		sb.append(" to ");
		sb.append(close);
		sb.append(". Thanks!");
		model.addAttribute("message", sb);
		
		return "closed";
	}
}
