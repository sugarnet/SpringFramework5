package com.dss.spring.boot.backend.apirest.models.service;

import java.util.List;

import com.dss.spring.boot.backend.apirest.models.entity.Customer;

public interface CustomerService {
	
	List<Customer> findAll();

}
