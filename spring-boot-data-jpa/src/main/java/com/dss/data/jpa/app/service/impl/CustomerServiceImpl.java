package com.dss.data.jpa.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dss.data.jpa.app.dao.BillDAO;
import com.dss.data.jpa.app.dao.CustomerDAO;
import com.dss.data.jpa.app.dao.ProductDAO;
import com.dss.data.jpa.app.entity.Bill;
import com.dss.data.jpa.app.entity.Customer;
import com.dss.data.jpa.app.entity.Product;
import com.dss.data.jpa.app.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private BillDAO billDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return (List<Customer>) customerDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Customer findById(Long id) {
		return customerDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Customer customer) {
		customerDAO.save(customer);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		customerDAO.deleteById(id);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerDAO.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findByName(String term) {
		return productDAO.findByDescriptionContainingIgnoreCase(term);
	}

	@Override
	@Transactional
	public void saveBill(Bill bill) {
		billDAO.save(bill);
	}

	@Override
	@Transactional(readOnly = true)
	public Product findProductById(Long id) {
		return productDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Bill findBillById(Long id) {
		return billDAO.findById(id).orElse(null);
	}

}
