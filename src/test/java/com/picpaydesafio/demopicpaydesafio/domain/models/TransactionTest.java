package com.picpaydesafio.demopicpaydesafio.domain.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionTest {

  // User
  public static final long ID_SENDER = 1L;
  public static final long ID_RECEIVER = 2L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final UserType USER_TYPE = UserType.COMMON;
  public static final String PASSWORD = "password";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final LocalDateTime TIMESTAMP = LocalDateTime.now();

  // Transaction
  public static final long ID_TRANSACTION = 1L;
  public static final BigDecimal AMOUNT = new BigDecimal("50.00");

  private Transaction mockTransaction;
  private User mockSender;
  private User mockReceiver;

  @BeforeEach
  void setUp() {
    mockSender = new User(ID_SENDER, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);
    mockReceiver = new User(ID_RECEIVER, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);

    mockTransaction = new Transaction(ID_TRANSACTION, AMOUNT, mockSender, mockReceiver, TIMESTAMP);
  }

  @Test
  void process_ShouldReturnTransaction_WhenSuccessful() {
    // Act
    Transaction processedTransaction = mockTransaction.process();

    // Assert
    assertAll(
        () -> assertEquals(new BigDecimal("50.00"), processedTransaction.getSender().getBalance()),
        () -> assertEquals(new BigDecimal("150.00"), processedTransaction.getReceiver().getBalance()),
        () -> assertEquals(mockTransaction.getId(), processedTransaction.getId()),
        () -> assertEquals(mockTransaction.getAmount(), processedTransaction.getAmount()),
        () -> assertEquals(mockTransaction.getTimestamp(), processedTransaction.getTimestamp()),
        () -> assertNotSame(mockSender, processedTransaction.getSender()),
        () -> assertNotSame(mockReceiver, processedTransaction.getReceiver())
    );
  }

}