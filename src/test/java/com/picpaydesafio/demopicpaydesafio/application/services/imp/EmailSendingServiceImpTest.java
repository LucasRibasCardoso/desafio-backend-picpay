package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidSendEmail;
import com.picpaydesafio.demopicpaydesafio.domain.factories.EmailFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.Email;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.EmailMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailSendingServiceImpTest {

  @Mock
  private JavaMailSender emailSender;

  @Mock
  private EmailFactory emailFactory;

  @Mock
  private EmailMapper emailMapper;

  private EmailSendingServiceImp emailSendingService;

  @BeforeEach
  void setUp() {
    emailSendingService = new EmailSendingServiceImp(emailSender, emailFactory, emailMapper);
  }

  @Test
  void shouldSendEmailsToSenderAndReceiver() {
    // Arrange
    Transaction transaction = createTransaction();
    mockEmailFactory();
    mockEmailMapper();

    // Act
    emailSendingService.sendEmail(transaction);

    // Assert
    verify(emailSender, times(2)).send(any(SimpleMailMessage.class));
    verify(emailMapper, times(2)).toMailMessage(any(Email.class));
  }

  @Test
  void shouldThrowInvalidSendEmailExceptionWhenEmailSendingFails() {
    // Arrange
    Transaction transaction = createTransactionForFailureTest();
    mockEmailFactory();
    SimpleMailMessage senderMailMessage = mock(SimpleMailMessage.class);

    when(emailMapper.toMailMessage(any(Email.class))).thenReturn(senderMailMessage);

    doThrow(new MailException("Erro ao enviar email. Tente realizar a transação novamente mais tarde.") {})
        .when(emailSender).send(senderMailMessage);

    // Act & Assert
    InvalidSendEmail exception = assertThrows(
        InvalidSendEmail.class,
        () -> emailSendingService.sendEmail(transaction)
    );

    assertEquals("Erro ao enviar email. Tente realizar a transação novamente mais tarde.", exception.getMessage());
  }

  private Transaction createTransaction() {
    User sender = mock(User.class);
    User receiver = mock(User.class);

    when(sender.fullName()).thenReturn("João Silva");
    when(sender.getEmail()).thenReturn("joao.silva@example.com");
    when(receiver.fullName()).thenReturn("Maria Santos");
    when(receiver.getEmail()).thenReturn("maria.santos@example.com");

    Transaction transaction = mock(Transaction.class);
    when(transaction.getSender()).thenReturn(sender);
    when(transaction.getReceiver()).thenReturn(receiver);
    when(transaction.getAmount()).thenReturn(BigDecimal.valueOf(100.00));

    return transaction;
  }

  private Transaction createTransactionForFailureTest() {
    User sender = mock(User.class);

    when(sender.fullName()).thenReturn("João Silva");
    when(sender.getEmail()).thenReturn("joao.silva@example.com");

    Transaction transaction = mock(Transaction.class);
    when(transaction.getSender()).thenReturn(sender);
    when(transaction.getReceiver()).thenReturn(mock(User.class)); // Receiver não é utilizado no segundo teste

    return transaction;
  }

  private void mockEmailFactory() {
    Email senderEmail = mock(Email.class);
    Email receiverEmail = mock(Email.class);

    when(emailFactory.createDomain(
        isNull(),
        anyString(),
        anyString(),
        anyString(),
        anyString(),
        any(LocalDateTime.class)
    )).thenReturn(senderEmail, receiverEmail);
  }

  private void mockEmailMapper() {
    SimpleMailMessage senderMailMessage = new SimpleMailMessage();
    SimpleMailMessage receiverMailMessage = new SimpleMailMessage();

    when(emailMapper.toMailMessage(any(Email.class)))
        .thenReturn(senderMailMessage, receiverMailMessage);
  }
}
