package com.picpaydesafio.demopicpaydesafio.domain.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

  private User regularUser;
  private User merchantUser;

  @BeforeEach
  void setUp() {
    regularUser = new User(1L, "John", "Doe", "123456789", "john.doe@example.com", "password123",
        new BigDecimal("100.00"), UserType.COMMON
    );

    merchantUser = new User(2L, "Jane", "Smith", "987654321", "jane.smith@example.com", "password456",
        new BigDecimal("500.00"), UserType.MERCHANT
    );
  }

  @Test
  void fullName_ShouldReturnFullName() {
    // Arrange
    String expectedFullName = "John Doe";

    // Act
    String actualFullName = regularUser.fullName();

    // Assert
    assertNotNull(actualFullName);
    assertEquals(expectedFullName, actualFullName);
  }

  @Test
  void isMerchant_ShouldReturnTrue_WhenUserIsMerchant() {
    assertTrue(merchantUser.isMerchant());
  }

  @Test
  void isMerchant_ShouldReturnFalse_WhenUserIsNotMerchant() {
    assertFalse(regularUser.isMerchant());
  }

  @Test
  void credit_ShouldAddToTheBalance_WhenUserIsCredit() {
    // Arrange
    BigDecimal creditAmount = new BigDecimal("50.00");
    BigDecimal expectedBalance = new BigDecimal("150.00");

    // Act
    User updatedUser = regularUser.credit(creditAmount);

    // Assert
    assertEquals(expectedBalance, updatedUser.getBalance());
    assertNotSame(regularUser, updatedUser);
  }

  @Test
  void debit_ShouldDebitFromTheAccount_WhenUserHaveSufficientBalance() {
    // Arrange
    BigDecimal debitAmount = new BigDecimal("80.00");
    BigDecimal expectedBalance = new BigDecimal("20.00");

    // Act
    User updatedUser = regularUser.debit(debitAmount);

    // Assert
    assertEquals(expectedBalance, updatedUser.getBalance());
    assertNotSame(regularUser, updatedUser);
  }

  @Test
  void debit_ShouldThrowException_WhenUserNotHaveSufficientBalance() {
    // Arrange
    BigDecimal debitAmount = new BigDecimal("200.00");

    // Act & Assert
    InsufficientFoundsException exception = assertThrows(InsufficientFoundsException.class,
        () -> regularUser.debit(debitAmount)
    );

    assertEquals("Saldo insuficiente para realizar a transação.", exception.getMessage());
  }
}