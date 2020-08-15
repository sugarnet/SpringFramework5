package com.dss.data.jpa.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dss.data.jpa.app.entity.Customer;

public interface CustomerService {
	List<Customer> findAll();
	Page<Customer> findAll(Pageable pageable);

	Customer findById(Long id);

	void save(Customer customer);

	void delete(Long id);
}
