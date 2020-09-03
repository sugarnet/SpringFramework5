package com.dss.data.jpa.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dss.data.jpa.app.entity.Customer;

public interface CustomerDAO extends PagingAndSortingRepository<Customer, Long> {
	
	@Query("select c from Customer c left join fetch c.bills b where c.id = ?1")
	Customer fetchByIdWithBills(Long id);
}
