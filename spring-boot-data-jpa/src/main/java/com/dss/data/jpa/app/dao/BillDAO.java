package com.dss.data.jpa.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dss.data.jpa.app.entity.Bill;

public interface BillDAO extends CrudRepository<Bill, Long> {
	
	@Query("select b from Bill b join fetch b.customer c join fetch b.items i join fetch i.product p where b.id = ?1")
	Bill fetchByIdWithCustomerWithBillItemWithProduct(Long id);

}
