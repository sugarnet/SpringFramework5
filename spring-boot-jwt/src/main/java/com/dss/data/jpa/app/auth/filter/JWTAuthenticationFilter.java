package com.dss.data.jpa.app.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);


		if (Objects.nonNull(username) && Objects.nonNull(password)) {
			logger.info("Username from request parameter (form-data): " + username);
			logger.info("Password from request parameter (form-data): " + password);
		} else {
			com.dss.data.jpa.app.entity.User user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), com.dss.data.jpa.app.entity.User.class);
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username from request parameter (raw json): " + username);
				logger.info("Password from request parameter (raw json): " + password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((User) authResult.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		SecretKey secretKey = new SecretKeySpec("someSecretKeyFromSpringFrameworkCourse".getBytes(), SignatureAlgorithm.HS256.getJcaName());

		String token = Jwts.builder().setSubject(username).setClaims(claims).signWith(secretKey)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 14000000)).compact();

		response.addHeader("Authorization", "Bearer ".concat(token));

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("message", "Login Success!!");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("message", "Authentication error!!");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
	}
	
	

}
