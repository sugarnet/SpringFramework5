package com.dss.data.jpa.app.dao;

import java.util.List;
import java.util.Objects;

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

	@Override
	@Transactional
	public void save(Customer customer) {
		if(Objects.nonNull(customer.getId()) && customer.getId() > 0) {
			em.merge(customer);
		} else {
			em.persist(customer);
		}
	}

	@Override
	@Transactional
	public Customer findById(Long id) {
		return em.find(Customer.class, id);
	}

}
