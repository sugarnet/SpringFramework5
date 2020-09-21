package com.dss.data.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dss.data.jpa.app.auth.handler.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	// @Autowired
	// private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired 
	private UserDetailsService userDetailsService; // call JpaUserDetailsService implemented

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
		/*String bCryptPassword = "123456";
		
		for (int i = 0; i < 2; i++) {
			System.out.println(passwordEncoder.encode(bCryptPassword));
		}*/
		/*
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select username, password, enabled from user where username = ?")
		.authoritiesByUsernameQuery("select u.username, r.role from role r inner join user u on u.id=r.user_id where u.username=?");*/

		/*PasswordEncoder encoder = passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);

		auth.inMemoryAuthentication().withUser(users.username("admin").password("123456").roles("ADMIN", "USER"))
				.withUser(users.username("dscifo").password("123456").roles("USER"));*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/customers/list**", "/locale", "/api/customers/**").permitAll()
				// .antMatchers("/customers/details").hasAnyRole("USER")
				// .antMatchers("/customers/uploads/**").hasAnyRole("USER")
				// .antMatchers("/customers/form/**").hasAnyRole("ADMIN")
				// .antMatchers("/customers/delete/**").hasAnyRole("ADMIN")
				// .antMatchers("/bills/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().successHandler(loginSuccessHandler).loginPage("/login").permitAll() // .defaultSuccessUrl("/customers/list")
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");
	}

}
