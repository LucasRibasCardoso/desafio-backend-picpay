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

  @BeforeEach
  void setUp() {

  }

  @Test
  void execute_ShouldSuccessfullyCreateTransaction() {
    // Arrange
    TransactionRequestDTO request = mock(TransactionRequestDTO.class);
    Transaction transaction = mock(Transaction.class);
    Transaction processedTransaction = mock(Transaction.class);

    when(transactionFactory.createDomain(request)).thenReturn(transaction);
    when(transaction.process()).thenReturn(processedTransaction);
    when(transactionRepository.save(processedTransaction)).thenReturn(processedTransaction);

    // Act
    Transaction result =  createTransactionUseCase.execute(request);

    // Assert
    assertNotNull(result);
    verify(transactionFactory).createDomain(request);
    verify(transactionRepository).save(processedTransaction);
    verify(validateTransactionUseCase).validate(transaction);
    verify(transaction).process();
    verify(updateUserBalancesAfterTransactionUseCase).execute(
        processedTransaction.getSender(),
        processedTransaction.getReceiver()
    );
  }

  @Test
  void execute_ShouldThrowException_WhenUserTriesToCarryOutTransactionWithHimself() {
    // Arrange
    TransactionRequestDTO request = mock(TransactionRequestDTO.class);
    Transaction transaction = mock(Transaction.class);

    when(transactionFactory.createDomain(request)).thenReturn(transaction);
    doThrow(new UnauthorizedTransactionException(USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO))
        .when(validateTransactionUseCase).validate(transaction);

    // act, assert
    UnauthorizedTransactionException exception = assertThrows(
        UnauthorizedTransactionException.class,
        () -> createTransactionUseCase.execute(request)
    );

    assertEquals(USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO, exception.getMessage());
  }

  @Test
  void execute_ShouldThrowException_WhenShopkeeperTriesToCarryOutTransaction() {
    // Arrange
    TransactionRequestDTO request = mock(TransactionRequestDTO.class);
    Transaction transaction = mock(Transaction.class);

    when(transactionFactory.createDomain(request)).thenReturn(transaction);
    doThrow(new UnauthorizedTransactionException(LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES))
        .when(validateTransactionUseCase).validate(transaction);

    UnauthorizedTransactionException exception = assertThrows(UnauthorizedTransactionException.class,
        () -> createTransactionUseCase.execute(request)
    );

    assertEquals(LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES, exception.getMessage());
  }
}