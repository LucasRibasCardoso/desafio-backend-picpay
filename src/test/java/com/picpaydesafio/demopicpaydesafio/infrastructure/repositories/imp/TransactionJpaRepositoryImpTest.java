package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.imp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.TransactionMapper;
import com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.TransactionJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionJpaRepositoryImpTest {

  // User
  public static final long ID = 1L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final UserType USER_TYPE = UserType.COMMON;
  public static final String PASSWORD = "password";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final LocalDateTime TIMESTAMP = LocalDateTime.now();
  public static final UserRole USER_ROLE = UserRole.USER;


  private TransactionEntity mockTransactionEntity;
  private Transaction mockTransactionDomain;

  private UserEntity mockSenderEntity;
  private User mockSenderDomain;

  private User mockReceiverDomain;
  private UserEntity mockReceiverEntity;

  @InjectMocks
  private TransactionJpaRepositoryImp repository;

  @Mock
  private TransactionJpaRepository jpaRepository;

  @Mock
  private TransactionMapper mapper;

  @BeforeEach
  void setUp() {
    mockSenderDomain = new User(
        ID,
        FIRSTNAME,
        LASTNAME,
        DOCUMENT,
        EMAIL,
        PASSWORD,
        BALANCE,
        USER_TYPE,
        USER_ROLE
    );

    mockSenderEntity = new UserEntity(
        ID,
        FIRSTNAME,
        LASTNAME,
        DOCUMENT,
        EMAIL,
        PASSWORD,
        BALANCE,
        USER_TYPE,
        USER_ROLE
    );

    mockReceiverDomain = new User(
        ID,
        FIRSTNAME,
        LASTNAME,
        DOCUMENT,
        EMAIL, PASSWORD,
        BALANCE,
        USER_TYPE,
        USER_ROLE
    );

    mockReceiverEntity = new UserEntity(
        ID,
        FIRSTNAME,
        LASTNAME,
        DOCUMENT,
        EMAIL,
        PASSWORD,
        BALANCE,
        USER_TYPE,
        USER_ROLE
    );

    mockTransactionDomain = new Transaction(ID, BALANCE, mockSenderDomain, mockReceiverDomain, TIMESTAMP);
    mockTransactionEntity = new TransactionEntity(ID, BALANCE, mockSenderEntity, mockReceiverEntity, TIMESTAMP);
  }

  @Test
  void save_ShouldReturnSavedTransaction_WhenTransactionIsValid() {
    // Arrange
    when(mapper.toEntity(mockTransactionDomain)).thenReturn(mockTransactionEntity);
    when(jpaRepository.save(mockTransactionEntity)).thenReturn(mockTransactionEntity);
    when(mapper.toDomain(mockTransactionEntity)).thenReturn(mockTransactionDomain);

    // Act
    Transaction savedTransaction = repository.save(mockTransactionDomain);

    // Assert
    assertAll(
        () ->  assertEquals(Transaction.class, savedTransaction.getClass()),
        () ->  assertEquals(mockTransactionDomain.getId(), savedTransaction.getId()),
        () ->  assertEquals(mockTransactionDomain.getAmount(), savedTransaction.getAmount()),
        () ->  assertEquals(mockTransactionDomain.getTimestamp(), savedTransaction.getTimestamp()),
        () ->  assertEquals(mockTransactionDomain.getSender().getId(), savedTransaction.getSender().getId()),
        () ->  assertEquals(mockTransactionDomain.getReceiver().getId(), savedTransaction.getReceiver().getId())
    );

    verify(mapper).toEntity(mockTransactionDomain);
    verify(jpaRepository).save(mockTransactionEntity);
    verify(mapper).toDomain(mockTransactionEntity);
  }

  @Test
  void findAll_ShouldReturnListOfTransactions_WhenTransactionsExist() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(List.of(mockTransactionEntity));
    when(mapper.toDomain(mockTransactionEntity)).thenReturn(mockTransactionDomain);

    // Act
    List<Transaction> transactions = repository.findAll();

    // Assert
    assertAll(
        () -> assertInstanceOf(List.class, transactions),
        () -> assertEquals(1, transactions.size()),
        () -> assertEquals(Transaction.class, transactions.get(0).getClass()),
        () -> assertEquals(mockTransactionDomain.getId(), transactions.get(0).getId()),
        () -> assertEquals(mockTransactionDomain.getAmount(), transactions.get(0).getAmount()),
        () -> assertEquals(mockTransactionDomain.getTimestamp(), transactions.get(0).getTimestamp()),
        () -> assertEquals(mockTransactionDomain.getSender().getId(), transactions.get(0).getSender().getId()),
        () -> assertEquals(mockTransactionDomain.getReceiver().getId(), transactions.get(0).getReceiver().getId())
    );

    verify(jpaRepository).findAll();
    verify(mapper).toDomain(mockTransactionEntity);
  }

  @Test
  void findAll_ShouldReturnEmptyList_WhenNoTransactionsExist() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(List.of());

    // Act
    List<Transaction> transactions = repository.findAll();

    // Assert
    assertAll(
        () -> assertInstanceOf(List.class, transactions),
        () -> assertTrue(transactions.isEmpty())
    );

    verify(jpaRepository).findAll();
    verify(mapper, never()).toDomain(any());
  }

}
