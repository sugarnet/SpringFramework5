package com.dss.data.jpa.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.dss.data.jpa.app.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public List<Customer> findAll() {
		return em.createQuery("from Customer", Customer.class).getResultList();
	}

}
