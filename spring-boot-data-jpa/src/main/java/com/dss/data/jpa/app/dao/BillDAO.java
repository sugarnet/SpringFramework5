package com.dss.data.jpa.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.dss.data.jpa.app.entity.Bill;

public interface BillDAO extends CrudRepository<Bill, Long> {

}
