package com.dss.data.jpa.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		PasswordEncoder encoder = passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		auth.inMemoryAuthentication()
		.withUser(users.username("admin").password("123456").roles("ADMIN", "USER"))
		.withUser(users.username("dscifo").password("123456").roles("USER"));
	}

}
