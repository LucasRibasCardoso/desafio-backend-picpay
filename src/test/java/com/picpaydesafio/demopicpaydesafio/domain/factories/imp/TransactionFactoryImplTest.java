package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFoundException;
import com.picpaydesafio.demopicpaydesafio.application.services.imp.UserServiceImp;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
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

  // Transaction
  public static final long SENDER_ID = 1L;
  public static final long RECEIVER_ID = 2L;
  public static final BigDecimal AMOUNT = new BigDecimal(50);

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
  public static final UserRole USER_ROLE = UserRole.USER;


  @InjectMocks
  private TransactionFactoryImpl transactionFactory;

  @Mock
  private UserServiceImp userService;

  private User mockSender;
  private User mockReceiver;
  private TransactionRequestDTO mockTransactionRequestDTO;

  @BeforeEach
  void setUp() {
    mockSender = new User(ID_SENDER, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE, USER_ROLE);
    mockReceiver = new User(ID_RECEIVER, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE, USER_ROLE);
    mockTransactionRequestDTO = new TransactionRequestDTO(SENDER_ID, RECEIVER_ID, AMOUNT);
  }

  @Test
  void createDomain_ShouldCreateObjectTransaction_WhenUsersExist() {
    // Arrange
    when(userService.findUserById(SENDER_ID)).thenReturn(mockSender);
    when(userService.findUserById(RECEIVER_ID)).thenReturn(mockReceiver);

    // Act
    Transaction result = transactionFactory.createDomain(mockTransactionRequestDTO);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(mockSender, result.getSender()),
        () -> assertEquals(mockReceiver, result.getReceiver()),
        () -> assertEquals(mockTransactionRequestDTO.amount(), result.getAmount()),
        () -> assertNotNull(result.getTimestamp())
    );

    verify(userService).findUserById(SENDER_ID);
    verify(userService).findUserById(RECEIVER_ID);
  }

  @Test
  void createDomain_ShouldThrowException_WhenUserDoesNotExist() {
    // Arrange
    when(userService.findUserById(SENDER_ID)).thenThrow(
        new UserNotFoundException("Usuário com id " + SENDER_ID + " não encontrado.")
    );

    // Act Assert
    UserNotFoundException exception = assertThrows(
        UserNotFoundException.class,
        () -> transactionFactory.createDomain(mockTransactionRequestDTO)
    );

    assertNotNull(exception);
    assertEquals("Usuário com id " + SENDER_ID + " não encontrado.", exception.getMessage());
  }

}