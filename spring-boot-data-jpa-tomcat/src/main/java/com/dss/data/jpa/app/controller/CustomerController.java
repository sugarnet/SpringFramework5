package com.dss.data.jpa.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.service.CustomerService;
import com.dss.data.jpa.app.service.UploadFileService;
import com.dss.data.jpa.app.util.paginator.PageRender;
import com.dss.data.jpa.app.view.xml.CustomerList;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController {

	protected static final Log LOGGER = LogFactory.getLog(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private MessageSource messageSource;

	
	@GetMapping({ "/", "/list" })
	public String list(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model,
			Authentication authentication, HttpServletRequest request, Locale locale) {

		if (Objects.nonNull(authentication)) {
			LOGGER.info("Hello user! Your username is ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (Objects.nonNull(auth)) {
			LOGGER.info("Using ecurityContextHolder.getContext().getAuthentication(). Your username is "
					.concat(auth.getName()));
		}

		if (hasRole("ROLE_ADMIN")) {
			LOGGER.info("Hello ".concat(auth.getName()).concat(". You have access!"));
		} else {
			LOGGER.info("Hello ".concat(auth.getName()).concat(". You DON'T have access!"));

		}

		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
				"ROLE_"); // If we use "ROLE_", we don't need put it in the method isUserInRole()

		if (securityContext.isUserInRole("ADMIN")) {
			LOGGER.info("Using SecurityContextHolderAwareRequestWrapper. Hello ".concat(auth.getName()).concat(". You have access!"));
		} else {
			LOGGER.info("Using SecurityContextHolderAwareRequestWrapper. Hello ".concat(auth.getName()).concat(". You DON'T have access!"));

		}
		
		if (request.isUserInRole("ROLE_ADMIN")) { // It's necessary put the complete ROLE_ADMIN 
			LOGGER.info("Using HttpServletRequest. Hello ".concat(auth.getName()).concat(". You have access!"));
		} else {
			LOGGER.info("Using HttpServletRequest. Hello ".concat(auth.getName()).concat(". You DON'T have access!"));
			
		}

		Pageable pageable = PageRequest.of(page, 4);
		Page<Customer> customers = customerService.findAll(pageable);
		PageRender<Customer> pageRender = new PageRender<>("/customers/list", customers);

		model.addAttribute("title", messageSource.getMessage("text.customer.list.title", null, locale));
		model.addAttribute("customers", customers);
		model.addAttribute("page", pageRender);
		return "modules/customer/list";
	}
	
	@GetMapping("/list-rest")
	public @ResponseBody CustomerList listRest() {
		return new CustomerList(customerService.findAll());
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", new Customer());
		return "modules/customer/form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')") // see documentation
	// @Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String edit(@PathVariable Long id, RedirectAttributes flash, Model model) {

		Customer customer = null;
		if (id > 0) {
			customer = customerService.findById(id);
		} else {
			flash.addFlashAttribute("error", "The id can't be less or equals than zero");
			return "redirect:/customers/list";
		}
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", customer);
		return "modules/customer/form";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String save(@Valid Customer customer, BindingResult result, Model model,
			@RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus sessionStatus) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Customer Form");
			return "modules/customer/form";
		}

		if (!photo.isEmpty()) {

			if (Objects.nonNull(customer.getId()) && customer.getId() > 0 && Objects.nonNull(customer.getPhoto())
					&& customer.getPhoto().length() > 0) {

				uploadFileService.delete(customer.getPhoto());

			}

			String filename = null;
			try {
				filename = uploadFileService.copy(photo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addAttribute("info", "The photo was uploaded!!!");
			customer.setPhoto(filename);

		}

		customerService.save(customer);
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", "Customer saved!");

		return "redirect:/customers/list";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Customer customer = customerService.findById(id);

			uploadFileService.delete(customer.getPhoto());
			customerService.delete(id);
		} else {
			flash.addFlashAttribute("error", "The id can't be less or equals than zero");
		}
		flash.addFlashAttribute("success", "The customer was deleted!");
		return "redirect:/customers/list";
	}

	// @Secured("ROLE_USER")
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/details/{id}")
	public String details(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Customer customer = customerService.fetchByIdWithBills(id); // customerService.findById(id);

		if (Objects.isNull(customer)) {
			flash.addFlashAttribute("error", "The customer doesn't exists in database...");
			return "redirect:/customers/list";

		}

		StringBuilder sb = new StringBuilder();
		sb.append("Customer ").append(customer.getName()).append(" ").append(customer.getLastname()).append(" details");

		model.addAttribute("customer", customer);
		model.addAttribute("title", sb);
		return "modules/customer/details";
	}

	/**
	 * With this method I need to change the path in the details.html and comment
	 * the method in the MvcConfig class
	 * 
	 * @param filename
	 * @return
	 */
	@Secured("ROLE_USER")
	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> seePhoto(@PathVariable String filename) {

		try {
			Resource resource = uploadFileService.load(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}

	}

	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();

		if (Objects.isNull(context)) {
			return false;
		}

		Authentication auth = context.getAuthentication();

		if (Objects.isNull(auth)) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		// return authorities.stream().filter(a ->
		// a.getAuthority().equals(role)).findAny().isPresent();
		return authorities.contains(new SimpleGrantedAuthority(role));
	}

}
