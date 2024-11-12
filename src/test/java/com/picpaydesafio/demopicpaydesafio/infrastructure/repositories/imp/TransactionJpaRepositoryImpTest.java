package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
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
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionJpaRepositoryImpTest {

  private static final BigDecimal BALANCE = BigDecimal.valueOf(500);
  private static final UserType USER_TYPE = UserType.COMMON;
  private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
  private static final String MARIA = "Maria";
  private static final String SILVA = "Silva";
  private static final String DOCUMENT_MARIA = "98765432130";
  private static final String EMAIL_MARIA = "maria@gmail.com";
  private static final String PASSWORD_MARIA = "maria123";
  private static final String JOAO = "João";
  private static final String CARVALHO = "Carvalho";
  private static final String DOCUMENT_JOAO = "98515432130";
  private static final String EMAIL_JOAO = "joao@gmail.com";
  private static final String PASSWORD_JOAO = "joao123";
  private static final long ID1 = 1L;
  private static final long ID2 = 2L;

  private Transaction transaction;
  private TransactionEntity transactionEntity;

  @InjectMocks
  private TransactionJpaRepositoryImp repository;

  @Mock
  private TransactionJpaRepository jpaRepository;

  @Mock
  private TransactionMapper mapper;

  @BeforeEach
  void setUp() {
    initializeTestObjects();
  }

  @Test
  void save_ShouldReturnSavedTransaction_WhenTransactionIsValid() {
    // Arrange
    when(mapper.toEntity(transaction)).thenReturn(transactionEntity);
    when(jpaRepository.save(transactionEntity)).thenReturn(transactionEntity);
    when(mapper.toDomain(transactionEntity)).thenReturn(transaction);

    // Act
    Transaction savedTransaction = repository.save(transaction);

    // Assert
    assertEquals(Transaction.class, savedTransaction.getClass());

    assertEquals(transaction.getId(), savedTransaction.getId());
    assertEquals(transaction.getAmount(), savedTransaction.getAmount());
    assertEquals(transaction.getTimestamp(), savedTransaction.getTimestamp());
    assertEquals(transaction.getSender().getId(), savedTransaction.getSender().getId());
    assertEquals(transaction.getReceiver().getId(), savedTransaction.getReceiver().getId());

    verify(mapper).toEntity(transaction);
    verify(jpaRepository).save(transactionEntity);
    verify(mapper).toDomain(transactionEntity);

  }

  @Test
  void findAll_ShouldReturnListOfTransactions_WhenTransactionsIsSaved() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(Collections.singletonList(transactionEntity));
    when(mapper.toDomain(transactionEntity)).thenReturn(transaction);

    // Act
    List<Transaction> transactions = repository.findAll();

    // Assert
    assertEquals(Transaction.class, transactions.get(0).getClass());
    assertEquals(1, transactions.size());

    assertEquals(transaction.getId(), transactions.get(0).getId());
    assertEquals(transaction.getAmount(), transactions.get(0).getAmount());
    assertEquals(transaction.getTimestamp(), transactions.get(0).getTimestamp());
    assertEquals(transaction.getSender().getId(), transactions.get(0).getSender().getId());
    assertEquals(transaction.getReceiver().getId(), transactions.get(0).getReceiver().getId());

    verify(jpaRepository).findAll();
    verify(mapper).toDomain(transactionEntity);

  }

  @Test
  void findAll_ShouldReturnEmptyList_WhenNoTransactionsExist() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    List<Transaction> transactions = repository.findAll();

    // Assert
    assertTrue(transactions.isEmpty());

    verify(jpaRepository).findAll();
    verify(mapper, never()).toDomain(any());
  }


  private void initializeTestObjects() {
    User senderDomain = new User(
        ID1, MARIA, SILVA, DOCUMENT_MARIA, EMAIL_MARIA, PASSWORD_MARIA, BALANCE, USER_TYPE
    );
    UserEntity senderEntity = new UserEntity(
        ID1, MARIA, SILVA, DOCUMENT_MARIA, EMAIL_MARIA, PASSWORD_MARIA, BALANCE, USER_TYPE
    );

    User receiverDomain = new User(
        ID2, JOAO, CARVALHO, DOCUMENT_JOAO, EMAIL_JOAO, PASSWORD_JOAO, BALANCE, USER_TYPE
    );
    UserEntity receiverEntity = new UserEntity(
        ID2, JOAO, CARVALHO, DOCUMENT_JOAO, EMAIL_JOAO, PASSWORD_JOAO, BALANCE, USER_TYPE
    );

    transaction = new Transaction(ID1, BALANCE, senderDomain, receiverDomain, TIMESTAMP);
    transactionEntity = new TransactionEntity(ID1, BALANCE, senderEntity, receiverEntity, TIMESTAMP);
  }
}
