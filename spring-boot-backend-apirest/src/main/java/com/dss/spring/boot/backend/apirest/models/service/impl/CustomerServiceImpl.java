package com.dss.spring.boot.backend.apirest.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dss.spring.boot.backend.apirest.models.dao.CustomerDAO;
import com.dss.spring.boot.backend.apirest.models.entity.Customer;
import com.dss.spring.boot.backend.apirest.models.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return (List<Customer>) customerDAO.findAll();
	}

}
