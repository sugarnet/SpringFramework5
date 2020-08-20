package com.dss.data.jpa.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import com.dss.data.jpa.app.service.UploadFileService;
import com.dss.data.jpa.app.util.paginator.PageRender;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UploadFileService uploadFileService;

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

	/**
	 * With this method I need to change the path in the details.html and comment
	 * the method in the MvcConfig class
	 * 
	 * @param filename
	 * @return
	 */
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
}
