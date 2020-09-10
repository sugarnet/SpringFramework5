package com.dss.data.jpa.app.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dss.data.jpa.app.dao.UserDAO;
import com.dss.data.jpa.app.entity.User;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDetailsService.class);

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userDAO.findByUsername(username);
		
		if (Objects.isNull(user)) {
			LOGGER.error("User {} doesn't exists", username);
			throw new UsernameNotFoundException("User doesn't exists!");
		}

		List<GrantedAuthority> authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
				.collect(Collectors.toList());
		
		LOGGER.info("Assigned Roles to {}: {}", username, authorities);

		if (Objects.isNull(authorities)) {
			LOGGER.error("User {} doesn't have assigned roles", user.getUsername());
			throw new UsernameNotFoundException("User doesn't have assigned roles!");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getEnabled(), true, true, true, authorities);
	}

}
