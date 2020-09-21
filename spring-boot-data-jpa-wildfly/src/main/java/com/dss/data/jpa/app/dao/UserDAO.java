package com.dss.data.jpa.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.dss.data.jpa.app.entity.User;

public interface UserDAO extends CrudRepository<User, Long>{

	User findByUsername(String username);
}
