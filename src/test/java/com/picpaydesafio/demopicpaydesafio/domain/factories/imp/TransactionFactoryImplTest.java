package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFound;
import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
import com.picpaydesafio.demopicpaydesafio.application.services.imp.UserServiceImp;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionFactoryImplTest {

  public static final long SENDER_ID = 1L;
  public static final long RECEIVER_ID = 2L;
  public static final BigDecimal AMOUNT = new BigDecimal(50);

  private TransactionFactoryImpl transactionFactory;

  @Mock
  private UserServiceImp userService;

  @BeforeEach
  void setUp() {
    transactionFactory = new TransactionFactoryImpl(userService);
  }

  @Test
  void createDomain_ShouldCreateObjectTransaction_WhenUsersExist() {
    User sender = mock(User.class);
    User receiver = mock(User.class);
    TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(SENDER_ID, RECEIVER_ID, AMOUNT);

    // Arrange
    when(userService.findUserById(SENDER_ID)).thenReturn(sender);
    when(userService.findUserById(RECEIVER_ID)).thenReturn(receiver);

    // Act
    Transaction result = transactionFactory.createDomain(transactionRequestDTO);

    // Assert
    assertNotNull(result);
    assertEquals(sender, result.getSender());
    assertEquals(receiver, result.getReceiver());
    assertEquals(transactionRequestDTO.amount(), result.getAmount());
    assertNotNull(result.getTimestamp());

    verify(userService).findUserById(SENDER_ID);
    verify(userService).findUserById(RECEIVER_ID);
  }

  @Test
  void createDomain_ShouldThrowException_WhenUserDoesNotExist() {
    // Arrange
    TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(SENDER_ID, RECEIVER_ID, AMOUNT);
    when(userService.findUserById(SENDER_ID)).thenThrow(
        new UserNotFound("Usuário com id " + SENDER_ID + " não encontrado."));

    // Act Assert
    UserNotFound exception = assertThrows(
        UserNotFound.class, () -> transactionFactory.createDomain(transactionRequestDTO));

    assertNotNull(exception);
    assertEquals("Usuário com id " + SENDER_ID + " não encontrado.", exception.getMessage());
  }

}