package com.picpaydesafio.demopicpaydesafio.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateTransactionUseCaseTest {

  public static final String LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES = "Lojistas não podem realizar transações.";
  public static final String USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO = "Usuário não pode realizar transação consigo mesmo.";

  private ValidateTransactionUseCase validateTransactionUseCase = new ValidateTransactionUseCase();

  @Test
  void validate_ShouldNotThrowExceptionWithValidTransaction() {
    // Arrange
    User sender = mock(User.class);
    User receiver = mock(User.class);
    Transaction transaction = mock(Transaction.class);

    when(transaction.getSender()).thenReturn(sender);
    when(transaction.getReceiver()).thenReturn(receiver);
    when(sender.isMerchant()).thenReturn(false);

    // Act & Assert
    assertDoesNotThrow(() -> validateTransactionUseCase.validate(transaction));
  }

  @Test
  void validate_ShouldThrowExceptionWhenSenderAndReceiverAreSameUser() {
    // Arrange
    User user = mock(User.class);
    Transaction transaction = mock(Transaction.class);

    when(transaction.getSender()).thenReturn(user);
    when(transaction.getReceiver()).thenReturn(user);

    // Act & Assert
    UnauthorizedTransactionException exception = assertThrows(
        UnauthorizedTransactionException.class,
        () -> validateTransactionUseCase.validate(transaction)
    );

    assertEquals(USUARIO_NAO_PODE_REALIZAR_TRANSACAO_CONSIGO_MESMO, exception.getMessage());
  }

  @Test
  void validate_ShouldThrowExceptionWhenSenderIsMerchant() {
    // Arrange
    User sender = mock(User.class);
    User receiver = mock(User.class);
    Transaction transaction = mock(Transaction.class);

    when(transaction.getSender()).thenReturn(sender);
    when(transaction.getReceiver()).thenReturn(receiver);
    when(sender.isMerchant()).thenReturn(true);

    // Act & Assert
    UnauthorizedTransactionException exception = assertThrows(
        UnauthorizedTransactionException.class,
        () -> validateTransactionUseCase.validate(transaction)
    );

    assertEquals(LOJISTAS_NAO_PODEM_REALIZAR_TRANSACOES, exception.getMessage());
  }
}