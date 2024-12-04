package com.picpaydesafio.demopicpaydesafio.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DefaultErrorTest {

  // DefaultError
  public static final String MESSAGE_ERROR_TEST = "Validation error test";
  public static final int STATUS_CODE = 422;

  @Test
  void testConstructorWithFieldErrors() {
    // Arrange
    Map<String, String> fieldsErrors = new HashMap<>();
    fieldsErrors.put("validation", MESSAGE_ERROR_TEST);

    // Act
    DefaultError mockDefaultErrorWithFieldErrors = new DefaultError(MESSAGE_ERROR_TEST, STATUS_CODE, fieldsErrors);

    // Assert
    assertAll(
        () -> assertEquals(MESSAGE_ERROR_TEST, mockDefaultErrorWithFieldErrors.getMessage()),
        () -> assertEquals(STATUS_CODE, mockDefaultErrorWithFieldErrors.getCode()),
        () -> assertNotNull(mockDefaultErrorWithFieldErrors.getFieldErrors()),
        () -> assertEquals(1, mockDefaultErrorWithFieldErrors.getFieldErrors().size()),
        () -> assertEquals(MESSAGE_ERROR_TEST, mockDefaultErrorWithFieldErrors.getFieldErrors().get("validation"))
    );
  }

  @Test
  void testConstructorWithMessageAndStatusCode() {
    // Act
    DefaultError mockDefaultError = new DefaultError(MESSAGE_ERROR_TEST, STATUS_CODE);

    // Assert
   assertAll(
       () -> assertEquals(MESSAGE_ERROR_TEST, mockDefaultError.getMessage()),
       () -> assertEquals(STATUS_CODE, mockDefaultError.getCode()),
       () -> assertNotNull(mockDefaultError.getFieldErrors()),
       () -> assertTrue(mockDefaultError.getFieldErrors().isEmpty())
   );
  }

}