package com.dss.form.app.service;

import java.util.List;

import com.dss.form.app.domain.Role;

public interface RoleService {

	List<Role> getAll();
	Role getById(Integer id);
}
