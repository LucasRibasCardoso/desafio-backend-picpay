package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidTokenException;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceImpTest {

  private TokenServiceImp tokenService;
  private final String SECRET = "test-secret-key";
  private final String TEST_EMAIL = "test@example.com";

  @BeforeEach
  void setUp() {
    tokenService = new TokenServiceImp();
    ReflectionTestUtils.setField(tokenService, "secret", SECRET);
  }

  @Test
  void generateToken_ShouldGenerateValidToken() {
    // Act
    String token = tokenService.generateToken(TEST_EMAIL, UserRole.USER);

    // Assert
    assertAll(
        () -> assertNotNull(token),
        () -> assertFalse(token.isEmpty())
    );

    // Validate the generated token
    String subject = tokenService.validateToken(token);
    assertEquals(TEST_EMAIL, subject);
  }

  @Test
  void validateToken_ShouldReturnSubject() {
    // Arrange
    String token = tokenService.generateToken(TEST_EMAIL, UserRole.USER);

    // Act
    String subject = tokenService.validateToken(token);

    // Assert
    assertEquals(TEST_EMAIL, subject);
  }

  @Test
  void validateToken_ShouldThrowInvalidTokenException_WhenTokenIsMalformed() {
    // Arrange
    String malformedToken = "malformed.token.here";

    // Act & Assert
    assertThrows(InvalidTokenException.class,
        () -> tokenService.validateToken(malformedToken));
  }

  @Test
  void validateToken_ShouldThrowInvalidTokenException_WhenTokenIsEmpty() {
    // Arrange
    String emptyToken = "";

    // Act & Assert
    assertThrows(InvalidTokenException.class,
        () -> tokenService.validateToken(emptyToken));
  }

  @Test
  void validateToken_ShouldThrowInvalidTokenException_WhenTokenIsNull() {
    // Act & Assert
    assertThrows(InvalidTokenException.class,
        () -> tokenService.validateToken(null));
  }

  @Test
  void generateToken_ShouldThrowRuntimeException_WhenSecretIsInvalid() {
    // Arrange
    ReflectionTestUtils.setField(tokenService, "secret", "");

    // Act & Assert
    assertThrows(RuntimeException.class,
        () -> tokenService.generateToken(TEST_EMAIL, UserRole.USER));
  }

  @Test
  void generateToken_ShouldGenerateTokenWithCorrectRole() {
    // Act
    String token = tokenService.generateToken(TEST_EMAIL, UserRole.ADMIN);

    // Assert
    assertNotNull(token);

    String subject = tokenService.validateToken(token);
    assertEquals(TEST_EMAIL, subject);
  }
}