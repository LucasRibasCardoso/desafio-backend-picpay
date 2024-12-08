package com.picpaydesafio.demopicpaydesafio.web.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CPFWithMaskValidatorTest {

  private CPFWithMaskValidator validator;

  @BeforeEach
  void setUp() {
    validator = new CPFWithMaskValidator();
  }

  @Test
  void testValidCPF() {
    // Act
    boolean result = validator.isValid("123.456.789-09", null);

    // Assert
    assertTrue(result);
  }

  @Test
  void testInvalidCPFFormat() {
    // Act
    boolean result = validator.isValid("12345678909", null);

    // Assert
    assertFalse(result);
  }

  @Test
  void testNullCPF() {
    // Act
    boolean result = validator.isValid(null, null);

    // Assert
    assertTrue(result);
  }

  @Test
  void testEmptyCPF() {
    // Act
    boolean result = validator.isValid("", null);

    // Assert
    assertTrue(result);
  }

  @Test
  void testCPFWithInvalidMask() {
    // Act
    boolean result = validator.isValid("123.456.789-0", null);

    // Assert
    assertFalse(result);
  }
}