package com.dss.error.app.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2129516324726723411L;

	public UserNotFoundException(String id) {
		super("User with id ".concat(id).concat(" was not found!!!"));
	}
	
	

}
