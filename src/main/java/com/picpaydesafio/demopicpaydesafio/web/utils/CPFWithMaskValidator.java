package com.picpaydesafio.demopicpaydesafio.web.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFWithMaskValidator implements ConstraintValidator<CPFWithMask, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true;
    }

    // Expressão regular para verificar o formato 999.999.999-99
    String regex = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}";
    return value.matches(regex);
  }
}
