package com.dss.spring.boot.backend.apirest.models.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dss.spring.boot.backend.apirest.models.entity.Customer;
import com.dss.spring.boot.backend.apirest.models.service.CustomerService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.findAll();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable Long id) {

		Map<String, Object> response;
		try {
			Customer customer = customerService.findById(id);

			response = new HashMap<>();
			if (Objects.isNull(customer)) {
				response.put("message", "Customer with id ".concat(id.toString()).concat(" not found"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			response.put("customer", customer);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (DataAccessException dae) {
			response = new HashMap<>();
			response.put("message", "Database error");
			response.put("error", "Error: ".concat(dae.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/customers")
	public ResponseEntity<?> create(@Valid @RequestBody Customer customer, BindingResult result) {
		Map<String, Object> response;
		try {

			response = new HashMap<>();

			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream()
						.map(error -> error.getField().concat(": ").concat(error.getDefaultMessage()))
						.collect(Collectors.toList());
				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			Customer c = customerService.save(customer);

			response.put("message", "Customer created succefully");
			response.put("customer", c);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

		} catch (DataAccessException dae) {
			response = new HashMap<>();
			response.put("message", "Database error");
			response.put("error", "Error: ".concat(dae.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customers")
	public ResponseEntity<?> update(@Valid @RequestBody Customer customer, BindingResult result) {

		Map<String, Object> response = new HashMap<>();
		try {

			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream()
						.map(error -> error.getField().concat(": ").concat(error.getDefaultMessage()))
						.collect(Collectors.toList());
				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			Customer c = customerService.findById(customer.getId());

			if (Objects.isNull(c)) {
				response.put("message", "Customer with id ".concat(customer.getId().toString()).concat(" not found"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			c = customerService.save(customer);
			response.put("message", "Customer updated succefully");
			response.put("customer", c);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

		} catch (DataAccessException dae) {
			response = new HashMap<>();
			response.put("message", "Database error");
			response.put("error", "Error: ".concat(dae.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response;
		try {
			customerService.delete(id);
			response = new HashMap<>();
			response.put("message", "Customer deleted succefully");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException dae) {
			response = new HashMap<>();
			response.put("message", "Database error");
			response.put("error", "Error: ".concat(dae.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
