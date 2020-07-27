package com.dss.form.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.dss.form.app.domain.Role;
import com.dss.form.app.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private List<Role> roles;

	@PostConstruct
	private void init() {
		roles = new ArrayList<>();
		roles.addAll(Arrays.asList(new Role(1, "Administrator", "ROLE_ADMIN"), new Role(2, "User", "ROLE_USER"),
				new Role(3, "Moderator", "ROLE_MODERATOR")));
	}

	@Override
	public List<Role> getAll() {
		return roles;
	}

	@Override
	public Role getById(Integer id) {
		return roles.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
	}

}
