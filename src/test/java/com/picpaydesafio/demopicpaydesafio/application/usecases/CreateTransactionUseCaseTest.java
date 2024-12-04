package com.picpaydesafio.demopicpaydesafio.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.domain.factories.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateTransactionUseCaseTest {

  public static final String USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO = "Usuário não pode realizar transação consigo mesmo.";
  public static final String LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES = "Lojistas não podem realizar transações.";

  @InjectMocks
  private CreateTransactionUseCase createTransactionUseCase;

  @Mock
  private TransactionFactory transactionFactory;

  @Mock
  private ValidateTransactionUseCase validateTransactionUseCase;

  @Mock
  private TransactionRepository transactionRepository;

  @Mock
  private UpdateUserBalancesAfterTransactionUseCase updateUserBalancesAfterTransactionUseCase;

  TransactionRequestDTO mockTransactionRequestDTO;
  Transaction mockTransaction;
  Transaction mockProcessedTransaction;

  @BeforeEach
  void setUp() {
    mockTransactionRequestDTO = mock(TransactionRequestDTO.class);
    mockTransaction = mock(Transaction.class);
    mockProcessedTransaction = mock(Transaction.class);
  }

  @Test
  void execute_ShouldSuccessfullyCreateTransaction() {
    // Arrange
    when(transactionFactory.createDomain(mockTransactionRequestDTO)).thenReturn(mockTransaction);
    when(mockTransaction.process()).thenReturn(mockProcessedTransaction);
    when(transactionRepository.save(mockProcessedTransaction)).thenReturn(mockProcessedTransaction);

    // Act
    Transaction result =  createTransactionUseCase.execute(mockTransactionRequestDTO);

    // Assert
    assertNotNull(result);
    verify(transactionFactory).createDomain(mockTransactionRequestDTO);
    verify(transactionRepository).save(mockProcessedTransaction);
    verify(validateTransactionUseCase).validate(mockTransaction);
    verify(mockTransaction).process();
    verify(updateUserBalancesAfterTransactionUseCase).execute(
        mockProcessedTransaction.getSender(),
        mockProcessedTransaction.getReceiver()
    );
  }

  @Test
  void execute_ShouldThrowException_WhenUserTriesToCarryOutTransactionWithHimself() {
    // Arrange
    when(transactionFactory.createDomain(mockTransactionRequestDTO)).thenReturn(mockTransaction);
    doThrow(new UnauthorizedTransactionException(USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO))
        .when(validateTransactionUseCase).validate(mockTransaction);

    // Act & Assert
    UnauthorizedTransactionException exception = assertThrows(
        UnauthorizedTransactionException.class,
        () -> createTransactionUseCase.execute(mockTransactionRequestDTO)
    );

    assertEquals(USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO, exception.getMessage());
  }

  @Test
  void execute_ShouldThrowException_WhenShopkeeperTriesToCarryOutTransaction() {
    // Arrange
    when(transactionFactory.createDomain(mockTransactionRequestDTO)).thenReturn(mockTransaction);
    doThrow(new UnauthorizedTransactionException(LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES))
        .when(validateTransactionUseCase).validate(mockTransaction);

    // Act & Assert
    UnauthorizedTransactionException exception = assertThrows(UnauthorizedTransactionException.class,
        () -> createTransactionUseCase.execute(mockTransactionRequestDTO)
    );

    assertEquals(LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES, exception.getMessage());
  }
}