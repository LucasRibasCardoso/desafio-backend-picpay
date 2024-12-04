package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import static org.junit.jupiter.api.Assertions.*;

import com.picpaydesafio.demopicpaydesafio.domain.factories.StandardError;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class StandardErrorFactoryImpTest {

  // Standard Error
  public static final String MESSAGE_OF_THE_DEFAULT_ERROR = "message of the default error";
  public static final HttpStatus HTTP_STATUS = HttpStatus.OK;
  public static final String VALIDATION_ERROR_TEST = "validation error test";

  private StandardErrorFactoryImp STDErrorFactory;

  @BeforeEach
  void setUp() {
    STDErrorFactory = new StandardErrorFactoryImp();
  }

  @Test
  void create_ShouldCreatedObjectDefaultError() {
    // Act
    StandardError result = STDErrorFactory.create(MESSAGE_OF_THE_DEFAULT_ERROR, HTTP_STATUS);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(MESSAGE_OF_THE_DEFAULT_ERROR, result.getMessage()),
        () -> assertEquals(HTTP_STATUS.value(), result.getCode())
    );
  }

  @Test
  void create_ShouldCreatedObjectDefaultErrorWithFieldErrors() {
    // Arrange
    Map<String, String> fieldErrors = new HashMap<>();
    fieldErrors.put("message", VALIDATION_ERROR_TEST);

    // Act
    StandardError result = STDErrorFactory.create(MESSAGE_OF_THE_DEFAULT_ERROR, HTTP_STATUS, fieldErrors);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(MESSAGE_OF_THE_DEFAULT_ERROR, result.getMessage()),
        () -> assertEquals(HTTP_STATUS.value(), result.getCode()),
        () -> assertTrue(result.getFieldErrors().containsKey("message")),
        () -> assertEquals(VALIDATION_ERROR_TEST, result.getFieldErrors().get("message"))
    );
  }

}