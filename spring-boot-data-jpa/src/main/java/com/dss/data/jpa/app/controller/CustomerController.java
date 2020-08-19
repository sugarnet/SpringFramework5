package com.dss.data.jpa.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.service.CustomerService;
import com.dss.data.jpa.app.util.paginator.PageRender;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model) {

		Pageable pageable = PageRequest.of(page, 4);
		Page<Customer> customers = customerService.findAll(pageable);
		PageRender<Customer> pageRender = new PageRender<>("/customers/list", customers);

		model.addAttribute("title", "Customers List");
		model.addAttribute("customers", customers);
		model.addAttribute("page", pageRender);
		return "modules/customer/list";
	}

	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("title", "Customer Form");
		model.addAttribute("customer", new Customer());
		return "modules/customer/form";
	}

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

	@PostMapping("/form")
	public String save(@Valid Customer customer, BindingResult result, Model model,
			@RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus sessionStatus) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Customer Form");
			return "customer/form";
		}

		if (!photo.isEmpty()) {
			// Path photosFolder =
			// Paths.get("src//main//resources//static//uploads//photos");
			// String rootPath = photosFolder.toFile().getAbsolutePath();
			// String rootPath = "/home/dscifo/uploads";
			// byte[] bytes = rootPath.getBytes();
			// Path path = Paths.get(rootPath + "//" + photo.getOriginalFilename());
			String filename = UUID.randomUUID().toString().concat("_").concat(photo.getOriginalFilename());
			Path rootPath = Paths.get("uploads").resolve(filename);
			Path absolutePath = rootPath.toAbsolutePath();

			LOGGER.info("rootPath: {}", rootPath);
			LOGGER.info("absolutePath: {}", absolutePath);
			try {
				// Files.write(path, bytes);
				Files.copy(photo.getInputStream(), absolutePath);
				flash.addAttribute("info", "The photo was uploaded!!!");
				customer.setPhoto(filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		customerService.save(customer);
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", "Customer saved!");

		return "redirect:/customers/list";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			customerService.delete(id);
		}
		flash.addFlashAttribute("error", "The id can't be less or equals than zero");
		return "redirect:/customers/list";
	}

	@GetMapping("/details/{id}")
	public String details(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Customer customer = customerService.findById(id);

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

	@GetMapping("/photo/{filename:.+}")
	public ResponseEntity<Resource> seePhoto(@PathVariable String filename) {
		Path path = Paths.get("uploads").resolve(filename).toAbsolutePath();
		LOGGER.info("Path Photo: {}", path.toString());

		try {
			Resource resource = new UrlResource(path.toUri());

			if (!resource.exists() || !resource.isReadable()) {
				return ResponseEntity.badRequest().build();
			}

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
}
