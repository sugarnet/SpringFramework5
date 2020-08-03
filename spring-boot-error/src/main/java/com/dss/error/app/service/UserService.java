package com.dss.error.app.service;

import java.util.List;
import java.util.Optional;

import com.dss.error.app.domain.User;

public interface UserService {

	List<User> list();

	User getById(Integer id);

	Optional<User> getOne(Integer id);
}
