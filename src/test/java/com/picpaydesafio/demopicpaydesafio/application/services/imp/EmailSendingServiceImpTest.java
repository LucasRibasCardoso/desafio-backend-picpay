package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidSendEmailException;
import com.picpaydesafio.demopicpaydesafio.domain.factories.EmailFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.Email;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.EmailMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailSendingServiceImpTest {

  // User
  public static final long ID_SENDER = 1L;
  public static final long ID_RECEIVER = 2L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL_SENDER = "senderexample@gmail.com";
  public static final String EMAIL_RECEIVER = "receiverexample@gmail.com";
  public static final UserType USER_TYPE = UserType.COMMON;
  public static final String PASSWORD = "password";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final UserRole USER_ROLE = UserRole.USER;

  // Transaction
  public static final long ID_TRANSACTION = 1L;
  public static final BigDecimal AMOUNT = new BigDecimal("100.00");
  public static final LocalDateTime TIMESTAMP = LocalDateTime.now();

  @Mock
  private JavaMailSender emailSender;

  @Mock
  private EmailFactory emailFactory;

  @Mock
  private EmailMapper emailMapper;

  @InjectMocks
  private EmailSendingServiceImp emailSendingService;

  private SimpleMailMessage mockSenderMailMessage;
  private SimpleMailMessage mockReceiverMailMessage;

  private Email mockEmailSender;
  private Email mockEmailReceiver;

  private User mockSender;
  private User mockReceiver;

  private Transaction mockTransaction;

  @BeforeEach
  void setUp() {
    mockSenderMailMessage = new SimpleMailMessage();
    mockReceiverMailMessage = new SimpleMailMessage();

    mockEmailSender = mock(Email.class);
    mockEmailReceiver = mock(Email.class);

    mockSender = new User(ID_SENDER, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL_SENDER, PASSWORD, BALANCE, USER_TYPE, USER_ROLE);
    mockReceiver = new User(ID_RECEIVER, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL_RECEIVER, PASSWORD, BALANCE, USER_TYPE, USER_ROLE);

    mockTransaction = new Transaction(ID_TRANSACTION, AMOUNT, mockSender, mockReceiver, TIMESTAMP);
  }

  @Test
  void sendEmail_shouldSendEmailsToSenderAndReceiver() {
    // Arrange
    when(emailFactory.createDomain(
        isNull(),
        anyString(),
        anyString(),
        anyString(),
        anyString(),
        any(LocalDateTime.class)
    )).thenReturn(mockEmailSender, mockEmailReceiver);

    when(emailMapper.toMailMessage(any(Email.class))).thenReturn(mockSenderMailMessage, mockReceiverMailMessage);

    // Act
    emailSendingService.sendEmail(mockTransaction);

    // Assert
    verify(emailSender, times(2)).send(any(SimpleMailMessage.class));
    verify(emailMapper, times(2)).toMailMessage(any(Email.class));
  }

  @Test
  void sendEmail_shouldThrowInvalidSendEmailException_WhenEmailSendingFails() {
    // Arrange
    when(emailFactory.createDomain(
        isNull(),
        anyString(),
        anyString(),
        anyString(),
        anyString(),
        any(LocalDateTime.class)
    )).thenReturn(mockEmailSender, mockEmailReceiver);

    when(emailMapper.toMailMessage(any(Email.class))).thenReturn(mockSenderMailMessage);

    doThrow(new MailException("Erro ao enviar email. Tente realizar a transação novamente mais tarde.") {})
        .when(emailSender).send(mockSenderMailMessage);

    // Act & Assert
    InvalidSendEmailException exception = assertThrows(
        InvalidSendEmailException.class,
        () -> emailSendingService.sendEmail(mockTransaction)
    );

    assertEquals("Erro ao enviar email. Tente realizar a transação novamente mais tarde.", exception.getMessage());
  }

}
