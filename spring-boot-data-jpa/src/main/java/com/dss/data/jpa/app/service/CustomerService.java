package com.dss.data.jpa.app.service;

import java.util.List;

import com.dss.data.jpa.app.entity.Customer;

public interface CustomerService {
	List<Customer> findAll();

	Customer findById(Long id);

	void save(Customer customer);

	void delete(Long id);
}
