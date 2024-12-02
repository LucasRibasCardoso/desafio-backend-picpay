package com.picpaydesafio.demopicpaydesafio.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DefaultErrorTest {


  @Test
  void testConstructorWithFieldErrors() {
    // Arrange
    String message = "Validation error";
    int statusCode = 422;
    Map<String, String> fieldErrors = new HashMap<>();
    fieldErrors.put("username", "Invalid username");

    // Act
    DefaultError error = new DefaultError(message, statusCode, fieldErrors);

    // Assert
    assertEquals(message, error.getMessage());
    assertEquals(statusCode, error.getCode());
    assertEquals(fieldErrors, error.getFieldErrors());
  }

  @Test
  void testConstructorWithMessageAndStatusCode() {
    // Arrange
    String message = "Test error";
    int statusCode = 400;

    // Act
    DefaultError error = new DefaultError(message, statusCode);

    // Assert
    assertEquals(message, error.getMessage());
    assertEquals(statusCode, error.getCode());
    assertNotNull(error.getFieldErrors());
    assertTrue(error.getFieldErrors().isEmpty());
  }

}