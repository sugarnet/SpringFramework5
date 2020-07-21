package com.dss.form.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dss.form.app.domain.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.user.name");
		
		/*if (!user.getIdentifier().matches("[\\d]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{3}")) {
			errors.rejectValue("identifier", "Pattern.user.identifier");
		}*/

	}

}
