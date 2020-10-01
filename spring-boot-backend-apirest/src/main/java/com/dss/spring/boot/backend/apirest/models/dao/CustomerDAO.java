package com.dss.spring.boot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dss.spring.boot.backend.apirest.models.entity.Customer;

public interface CustomerDAO extends CrudRepository<Customer, Long> {

}
