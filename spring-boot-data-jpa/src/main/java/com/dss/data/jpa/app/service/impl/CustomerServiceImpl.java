package com.dss.data.jpa.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dss.data.jpa.app.dao.CustomerDAO;
import com.dss.data.jpa.app.dao.ProductDAO;
import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.entity.Product;
import com.dss.data.jpa.app.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private ProductDAO productDAO;

	@Transactional(readOnly = true)
	@Override
	public List<Customer> findAll() {
		return (List<Customer>) customerDAO.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Customer findById(Long id) {
		return customerDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Customer customer) {
		customerDAO.save(customer);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		customerDAO.deleteById(id);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerDAO.findAll(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Product> findByName(String term) {
		return productDAO.findByDescriptionContainingIgnoreCase(term);
	}

}
