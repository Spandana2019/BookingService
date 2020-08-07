package com.example.bookingapplication.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class GenderValidator implements ConstraintValidator<Gender, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value) || value.equalsIgnoreCase("female") || value.equalsIgnoreCase("male")) {
			return true;
		} else {
			return false;
		}
	}

}
