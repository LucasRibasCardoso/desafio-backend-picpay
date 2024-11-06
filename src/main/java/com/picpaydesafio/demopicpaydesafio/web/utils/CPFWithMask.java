package com.picpaydesafio.demopicpaydesafio.web.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Constraint(validatedBy = CPFWithMaskValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFWithMask {
  String message() default "CPF deve estar no formato 999.999.999-99";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}