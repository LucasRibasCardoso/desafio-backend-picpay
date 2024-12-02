package com.picpaydesafio.demopicpaydesafio.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {

  // User
  private static final long ID = 1L;
  private static final String FIRSTNAME = "teste";
  private static final String LASTNAME = "example";
  private static final String DOCUMENT = "12312312312";
  private static final String EMAIL = "example@gmail.com";
  private static final UserType USER_TYPE = UserType.COMMON;
  private static final String PASSWORD = "password";
  private static final BigDecimal BALANCE = new BigDecimal("100.00");

  // Transaction
  private static final BigDecimal AMOUNT = new BigDecimal("100");
  private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
  private static final String FULLNAME = FIRSTNAME + " " + LASTNAME;

  @InjectMocks
  private TransactionMapper transactionMapper;

  @Mock
  private UserMapper userMapper;

  private User mockSenderDomain;
  private User mockReceiverDomain;
  private UserEntity mockSenderEntity;
  private UserEntity mockReceiverEntity;

  @BeforeEach
  void setUp() {
    mockSenderDomain = new User(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);
    mockReceiverDomain = new User(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);

    mockSenderEntity = new UserEntity(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);
    mockReceiverEntity = new UserEntity(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);
  }

  @Test
  void toDomain_ShouldReturnObjectTransactionDomain() {
    // Arrange
    TransactionEntity transactionEntity = new TransactionEntity(ID, AMOUNT, mockSenderEntity,
        mockReceiverEntity, TIMESTAMP);
    when(userMapper.toDomain(mockSenderEntity)).thenReturn(mockSenderDomain);
    when(userMapper.toDomain(mockReceiverEntity)).thenReturn(mockReceiverDomain);

    // Act
    Transaction result = transactionMapper.toDomain(transactionEntity);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(ID, result.getId()),
        () -> assertEquals(AMOUNT, result.getAmount()),
        () -> assertEquals(mockSenderDomain, result.getSender()),
        () -> assertEquals(mockReceiverDomain, result.getReceiver()),
        () -> assertEquals(TIMESTAMP, result.getTimestamp())
    );
  }

  @Test
  void toResponseDTO_ShouldReturnObjectTransactionResponseDTO() {
    // Arrange
    Transaction transaction = new Transaction(ID, AMOUNT, mockSenderDomain, mockReceiverDomain, TIMESTAMP);

    // Act
    TransactionResponseDTO result = transactionMapper.toResponseDTO(transaction);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(ID, result.id()),
        () -> assertEquals(ID, result.senderId()),
        () -> assertEquals(FULLNAME, result.senderName()),
        () -> assertEquals(ID, result.receiverId()),
        () -> assertEquals(FULLNAME, result.receiverName()),
        () -> assertEquals(AMOUNT, result.amount()),
        () -> assertEquals(TIMESTAMP, result.timestamp())
    );
  }

  @Test
  void toEntity_ShouldReturnObjectTransactionEntity() {
    // Arrange
    Transaction transaction = new Transaction(ID, AMOUNT, mockSenderDomain, mockReceiverDomain, TIMESTAMP);
    when(userMapper.toEntity(mockSenderDomain)).thenReturn(mockSenderEntity);
    when(userMapper.toEntity(mockReceiverDomain)).thenReturn(mockReceiverEntity);

    // Act
    TransactionEntity result = transactionMapper.toEntity(transaction);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(ID, result.getId()),
        () -> assertEquals(AMOUNT, result.getAmount()),
        () -> assertEquals(mockSenderEntity, result.getSender()),
        () -> assertEquals(mockReceiverEntity, result.getReceiver()),
        () -> assertEquals(TIMESTAMP, result.getTimestamp())
    );
  }
}
