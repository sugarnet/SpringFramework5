package com.dss.form.app.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class ItIsRequiredValidator implements ConstraintValidator<ItIsRequired, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Objects.isNull(value) || !StringUtils.hasText(value)) {
			return false;
		}
		return true;
	}

}
