package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import static org.junit.jupiter.api.Assertions.*;

import com.picpaydesafio.demopicpaydesafio.domain.factories.StandardError;
import com.picpaydesafio.demopicpaydesafio.domain.models.DefaultError;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class StandardErrorFactoryImpTest {

  private StandardErrorFactoryImp STDErrorFactory;

  @BeforeEach
  void setUp() {
    STDErrorFactory = new StandardErrorFactoryImp();
  }

  @Test
  void create_ShouldCreatedObjectDefaultError() {
    // Arrange
    String message = "message of the default error";
    HttpStatus httpStatus = HttpStatus.OK;

    // Act
    StandardError result = STDErrorFactory.create(message, httpStatus);

    // Assert
    assertNotNull(result);
    assertEquals(message, result.getMessage());
    assertEquals(httpStatus.value(), result.getCode());
  }

  @Test
  void create_ShouldCreatedObjectDefaultErrorWithFieldErrors() {
    // Arrange
    String message = "message of the default error";
    HttpStatus httpStatus = HttpStatus.OK;
    Map<String, String> fieldErrors = new HashMap<>();

    // Act
    StandardError result = STDErrorFactory.create(message, httpStatus, fieldErrors);

    // Assert
    assertNotNull(result);
    assertEquals(message, result.getMessage());
    assertEquals(httpStatus.value(), result.getCode());
    assertTrue(fieldErrors.isEmpty());
  }

}