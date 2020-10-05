package com.dss.spring.boot.backend.apirest.models.service.impl;

import java.util.List;
import java.util.Objects;

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

	@Override
	public Customer findById(Long id) {
		return customerDAO.findById(id).orElse(null);
	}

	@Override
	public Customer save(Customer customer) {

		if (Objects.nonNull(customer.getId())) {
			final Customer currentCustomer = findById(customer.getId());

			currentCustomer.setName(customer.getName());
			currentCustomer.setLastname(customer.getLastname());
			currentCustomer.setEmail(customer.getEmail());

			return customerDAO.save(currentCustomer);

		} else {

			return customerDAO.save(customer);
		}
	}

	@Override
	public void delete(Long id) {
		customerDAO.deleteById(id);
	}

}
