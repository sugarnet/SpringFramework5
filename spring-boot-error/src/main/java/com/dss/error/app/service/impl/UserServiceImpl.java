package com.dss.error.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dss.error.app.domain.User;
import com.dss.error.app.exception.UserNotFoundException;
import com.dss.error.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private List<User> users;

	public UserServiceImpl() {
		users = new ArrayList<>();
		users.addAll(Arrays.asList(new User(1, "Diego", "Scifo"), new User(2, "Sol", "Mauna"),
				new User(3, "Alma", "Scifo Mauna")));
	}

	@Override
	public List<User> list() {
		return users;
	}

	@Override
	public User getById(Integer id) {
		final User user = users.stream().filter(u -> u.getId().equals(id)).findFirst()
				.orElseThrow(() -> new UserNotFoundException(id.toString()));

		/*
		 * if (Objects.isNull(user)) { throw new UserNotFoundException(id.toString()); }
		 */

		return user;
	}

	@Override
	public Optional<User> getOne(Integer id) {
		return users.stream().filter(u -> u.getId().equals(id)).findFirst();
	}
}
