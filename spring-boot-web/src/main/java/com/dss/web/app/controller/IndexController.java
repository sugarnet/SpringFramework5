package com.dss.web.app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dss.web.app.model.User;

@Controller
@RequestMapping("/app")
public class IndexController {
	
	@Value("${text.indexcontroller.index.title}")
 	private String  titleIndex;
	
	@Value("${text.indexcontroller.profile.title}")
 	private String  titleProfile;
	
	@Value("${text.indexcontroller.list.title}")
 	private String  titleList;

	// @RequestMapping(value = {"/index", "/", "/home"}, method = RequestMethod.GET) // method GET is default. So, we can remove. Attribute value is default
	@GetMapping({"", "/", "/home", "/index"})
	public String index(Model model) {
		model.addAttribute("title", titleIndex);
		return "index";
	}
	
	/*public String index(ModelMap model) {
		model.addAttribute("title", "Hola con ModelMap");
		return "index";
	}*/
	
	/*public String index(Map<String, Object> model) {
		model.put("title", "Hola con Map");
		return "index";
	}*/
	
	/*public ModelAndView index(ModelAndView model) {
		model.addObject("title", "Hola con ModelAndView");
		model.setViewName("index");
		return model;
	}*/
	
	@RequestMapping("/profile")
	public String profile(Model model) {
		User user = new User();
		user.setName("Diego");
		user.setSurname("Scifo");
		user.setEmail("dscifo@email.com");
		model.addAttribute("title", titleProfile.concat(" ").concat(user.getName()));
		model.addAttribute("user", user);
		return "profile";
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("title", titleList);
		return "list";
	}
	
	/*
	 *ModelAttibute: It's allow share the user array with all handler methods
	 *This list will be available in all views of this controller
	 */
	@ModelAttribute("users")
	public List<User> fillUsers() {
		return  Arrays.asList(
				new User("Diego", "Scifo", "diego@mail.com"),
				new User("Sol", "Mauna", "sol@mail.com"),
				new User("Alma", "Scifo", "alma@mail.com")
				);
	}
}
