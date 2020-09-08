package com.dss.data.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dss.data.jpa.app.auth.handler.LoginSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		PasswordEncoder encoder = passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);

		auth.inMemoryAuthentication().withUser(users.username("admin").password("123456").roles("ADMIN", "USER"))
				.withUser(users.username("dscifo").password("123456").roles("USER"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/customers/list").permitAll()
				.antMatchers("/customers/details").hasAnyRole("USER")
				.antMatchers("/customers/uploads/**").hasAnyRole("USER")
				.antMatchers("/customers/form/**").hasAnyRole("ADMIN")
				.antMatchers("/customers/delete/**").hasAnyRole("ADMIN")
				.antMatchers("/bills/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().successHandler(loginSuccessHandler).loginPage("/login").permitAll() // .defaultSuccessUrl("/customers/list")
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");
	}

}
