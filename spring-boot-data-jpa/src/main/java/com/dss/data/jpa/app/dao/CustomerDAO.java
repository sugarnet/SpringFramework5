package com.dss.data.jpa.app.dao;

import java.util.List;

import com.dss.data.jpa.app.entity.Customer;

public interface CustomerDAO {
	List<Customer> findAll();
}
