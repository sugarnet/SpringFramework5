package com.dss.data.jpa.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dss.data.jpa.app.entity.Customer;

public interface CustomerDAO extends PagingAndSortingRepository<Customer, Long> {
}
