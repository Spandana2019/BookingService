package com.example.bookingapplication.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = GenderValidator.class)
public @interface Gender {
public String message() default "Gender should be either Male or Female";
public Class<?>[] groups() default {};
public Class<? extends Payload>[] payload() default {};
  }
