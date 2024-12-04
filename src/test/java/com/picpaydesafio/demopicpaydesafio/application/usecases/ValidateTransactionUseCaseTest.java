package com.picpaydesafio.demopicpaydesafio.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateTransactionUseCaseTest {

  public static final String LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES = "Lojistas não podem realizar transações.";
  public static final String USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO = "Usuário não pode realizar transação consigo mesmo.";

  private ValidateTransactionUseCase validateTransactionUseCase;

  private User mockSender;
  private User mockReceiver;
  private Transaction mockTransaction;

  @BeforeEach
  void setUp() {
    validateTransactionUseCase = new ValidateTransactionUseCase();

    mockSender = mock(User.class);
    mockReceiver = mock(User.class);
    mockTransaction = mock(Transaction.class);
  }

  @Test
  void validate_ShouldNotThrowExceptionWithValidTransaction() {
    // Arrange
    when(mockTransaction.getSender()).thenReturn(mockSender);
    when(mockTransaction.getReceiver()).thenReturn(mockReceiver);
    when(mockSender.isMerchant()).thenReturn(false);

    // Act & Assert
    assertDoesNotThrow(() -> validateTransactionUseCase.validate(mockTransaction));
  }

  @Test
  void validate_ShouldThrowExceptionWhenSenderAndReceiverAreSameUser() {
    // Arrange
    User sameUser = mock(User.class);
    when(mockTransaction.getSender()).thenReturn(sameUser);
    when(mockTransaction.getReceiver()).thenReturn(sameUser);

    // Act & Assert
    UnauthorizedTransactionException exception = assertThrows(
        UnauthorizedTransactionException.class,
        () -> validateTransactionUseCase.validate(mockTransaction)
    );

    assertEquals(USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO, exception.getMessage());
  }

  @Test
  void validate_ShouldThrowExceptionWhenSenderIsMerchant() {
    // Arrange
    when(mockTransaction.getSender()).thenReturn(mockSender);
    when(mockTransaction.getReceiver()).thenReturn(mockReceiver);
    when(mockSender.isMerchant()).thenReturn(true);

    // Act & Assert
    UnauthorizedTransactionException exception = assertThrows(
        UnauthorizedTransactionException.class,
        () -> validateTransactionUseCase.validate(mockTransaction)
    );

    assertEquals(LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES, exception.getMessage());
  }
}