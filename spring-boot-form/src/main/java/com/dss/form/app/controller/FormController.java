package com.dss.form.app.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dss.form.app.domain.Country;
import com.dss.form.app.domain.User;
import com.dss.form.app.editor.UppercaseEditor;
import com.dss.form.app.service.CountryService;
import com.dss.form.app.validator.UserValidator;

@Controller
@SessionAttributes("user") // we use it when need keep not used object values in the form
public class FormController {

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CountryService countryService;

	@InitBinder
	public void initDataBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		// validate all date fields
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));

		// validate only birthDate field in form and allow empty (parameter in
		// CustomDateEditor)
		binder.registerCustomEditor(Date.class, "birthDate", new CustomDateEditor(sdf, true));
		binder.registerCustomEditor(String.class, "name", new UppercaseEditor());
	}

	@ModelAttribute("countries")
	public List<String> countries() {
		return Arrays.asList("Argentina", "Bolivia", "Brasil", "Chile", "Paraguay", "Uruguay");
	}

	@ModelAttribute("countriesList")
	public List<Country> countriesList() {
		return countryService.getAll();
	}

	@ModelAttribute("countriesMap")
	public Map<String, String> countriesMap() {
		Map<String, String> countries = new HashMap<>();

		countries.put("AR", "Argentina");
		countries.put("BO", "Bolivia");
		countries.put("BR", "Brasil");
		countries.put("CL", "Chile");
		countries.put("PA", "Paraguay");
		countries.put("UR", "Uruguay");

		return countries;
	}

	@GetMapping("/form")
	public String form(Model model) {
		User user = new User();
		user.setName("Diego");
		user.setSurname("Scifo");
		user.setIdentifier("30.029.756-DDS");
		model.addAttribute("title", "User Form");
		model.addAttribute("user", user);
		return "form";
	}

	@GetMapping("/index")
	public String index(Model model) {
		return "index";
	}

	@PostMapping("/form")
	public String process(@Valid User user, BindingResult bindingResult, Model model, SessionStatus sessionStatus) {
		// we user ModelAttribute when need change the name of attribute used in the
		// template against the controller
		// public String process(@Valid @ModelAttribute("usuario") User user,
		// BindingResult bindingResult, Model model) {
		/*
		 * public String process(Model model,
		 * 
		 * @RequestParam(name="username") String username,
		 * 
		 * @RequestParam String password,
		 * 
		 * @RequestParam String email) {
		 */

		// if we receive request params we can set to the fields manually. But we can
		// use directly an user object.
		// It's necessary that the fields in the form have the same name of attributes
		// of the class
		/*
		 * User user = new User(); user.setUsername(username);
		 * user.setPassword(password); user.setEmail(email);
		 */

		// we comment this line because use InitBinder
		model.addAttribute("title", "Result Form");
		/*
		 * model.addAttribute("username", username); model.addAttribute("password",
		 * password); model.addAttribute("email", email);
		 */

		if (bindingResult.hasErrors()) {
			// using th:object mapping errors too, it isn't necessary send the errors
			/*
			 * Map<String, String> errors = new HashMap<>();
			 * bindingResult.getFieldErrors().forEach(e -> errors.put(e.getField(),
			 * e.getDefaultMessage())); model.addAttribute("errors", errors);
			 */
			return "form";
		}

		model.addAttribute("user", user);

		// clean the session - we use it when define SessionAttributes
		sessionStatus.setComplete();

		return "result";
	}

}
