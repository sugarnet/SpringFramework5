package com.dss.data.jpa.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.dss.data.jpa.app.entity.Customer;

public interface CustomerDAO extends CrudRepository<Customer, Long> {
}
