package com.dss.data.jpa.app.dao;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dss.data.jpa.app.entity.Customer;

// @Repository
public class CustomerDAOImpl /* implements CustomerDAO */ {

	@PersistenceContext
	private EntityManager em;

	// @Override
	public List<Customer> findAll() {
		return em.createQuery("from Customer", Customer.class).getResultList();
	}

	// @Override
	public void save(Customer customer) {
		if (Objects.nonNull(customer.getId()) && customer.getId() > 0) {
			em.merge(customer);
		} else {
			em.persist(customer);
		}
	}

	// @Override
	public Customer findById(Long id) {
		return em.find(Customer.class, id);
	}

	// @Override
	public void delete(Long id) {
		em.remove(findById(id));
	}

}
