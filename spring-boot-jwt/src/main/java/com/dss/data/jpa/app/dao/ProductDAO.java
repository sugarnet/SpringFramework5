package com.dss.data.jpa.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dss.data.jpa.app.entity.Product;

public interface ProductDAO extends CrudRepository<Product, Long> {
	
	@Query("select p from Product p where p.description like %?1%")
	List<Product> findByDescription(String term);
	
	List<Product> findByDescriptionContainingIgnoreCase(String term);

}
