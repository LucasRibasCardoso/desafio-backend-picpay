package com.picpaydesafio.demopicpaydesafio.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionTest {

  private Transaction transaction;
  private User sender;
  private User receiver;
  private LocalDateTime timestamp;

  @BeforeEach
  void setUp() {
    sender = new User(1L, "John", "Doe", "123456789", "john.doe@example.com", "password123",
        new BigDecimal("100.00"), UserType.COMMON
    );

    receiver = new User(2L, "Jane", "Smith", "987654321", "jane.smith@example.com", "password456",
        new BigDecimal("50.00"), UserType.COMMON
    );

    timestamp = LocalDateTime.now();
    transaction = new Transaction(1L, new BigDecimal("30.00"), sender, receiver, timestamp);
  }

  @Test
  void process_ShouldReturnTransaction_WhenSuccessful() {
    // Act
    Transaction processedTransaction = transaction.process();

    // Assert
    assertEquals(new BigDecimal("70.00"), processedTransaction.getSender().getBalance());
    assertEquals(new BigDecimal("80.00"), processedTransaction.getReceiver().getBalance());

    assertEquals(transaction.getId(), processedTransaction.getId());
    assertEquals(transaction.getAmount(), processedTransaction.getAmount());
    assertEquals(transaction.getTimestamp(), processedTransaction.getTimestamp());

    assertNotSame(sender, processedTransaction.getSender());
    assertNotSame(receiver, processedTransaction.getReceiver());
  }

}