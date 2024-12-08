package com.picpaydesafio.demopicpaydesafio.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

  // User
  public static final long ID_NORMAL = 1L;
  public static final long ID_MERCHANT = 2L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final UserType USER_TYPE_NORMAL = UserType.COMMON;
  public static final UserType USER_TYPE_MERCHANT = UserType.MERCHANT;
  public static final String PASSWORD = "password";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final UserRole USER_ROLE = UserRole.USER;

  private User mockRegularUser;
  private User mockMerchantUser;

  @BeforeEach
  void setUp() {
    mockRegularUser = new User(
        ID_NORMAL, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE_NORMAL, USER_ROLE
    );

    mockMerchantUser = new User(
        ID_MERCHANT, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE_MERCHANT, USER_ROLE);
  }

  @Test
  void fullName_ShouldReturnFullName() {
    // Arrange
    String expectedFullName = FIRSTNAME + " " + LASTNAME;

    // Act
    String actualFullName = mockRegularUser.fullName();

    // Assert
    assertAll(
        () -> assertNotNull(actualFullName),
        () -> assertEquals(expectedFullName, actualFullName)
    );
  }

  @Test
  void isMerchant_ShouldReturnTrue_WhenUserTypeIsMerchant() {
    assertTrue(mockMerchantUser.isMerchant());
  }

  @Test
  void isMerchant_ShouldReturnFalse_WhenUserTypeIsNotMerchant() {
    assertFalse(mockRegularUser.isMerchant());
  }

  @Test
  void credit_ShouldAddToTheBalance_WhenUserIsCredit() {
    // Arrange
    BigDecimal creditAmount = new BigDecimal("50.00");
    BigDecimal expectedBalance = new BigDecimal("150.00");

    // Act
    User updatedUser = mockRegularUser.credit(creditAmount);

    // Assert
    assertAll(
        () -> assertEquals(expectedBalance, updatedUser.getBalance()),
        () -> assertNotSame(mockRegularUser, updatedUser)
    );
  }

  @Test
  void debit_ShouldDebitFromTheAccount_WhenUserHaveSufficientBalance() {
    // Arrange
    BigDecimal debitAmount = new BigDecimal("80.00");
    BigDecimal expectedBalance = new BigDecimal("20.00");

    // Act
    User updatedUser = mockRegularUser.debit(debitAmount);

    // Assert
    assertAll(
        () -> assertEquals(expectedBalance, updatedUser.getBalance()),
        () -> assertNotSame(mockRegularUser, updatedUser)
    );
  }

  @Test
  void debit_ShouldThrowException_WhenUserNotHaveSufficientBalance() {
    // Arrange
    BigDecimal debitAmount = new BigDecimal("200.00");

    // Act & Assert
    InsufficientFoundsException exception = assertThrows(InsufficientFoundsException.class,
        () -> mockRegularUser.debit(debitAmount)
    );

    assertEquals("Saldo insuficiente para realizar a transação.", exception.getMessage());
  }

}