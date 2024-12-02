package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.domain.factories.EmailFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.Email;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.util.ReflectionTestUtils;
import org.yaml.snakeyaml.events.Event.ID;

class EmailFactoryImpTest {

  private EmailFactoryImp emailFactory ;

  private final Long ID = 1L;
  private final String OWNER = "owner123";
  private final String EMAIL_FROM = "sender@example.com";
  private final String EMAIL_TO = "receiver@example.com";
  private final String SUBJECT = "PixPlay - Transação realizada";
  private final String TEXT = "Corpo do email de teste.";
  private final LocalDateTime SEND_DATE_EMAIL = LocalDateTime.now();

  @BeforeEach
  void setUp() {
    emailFactory = new EmailFactoryImp();

    // Configurar o email from usando ReflectionTestUtils para injetar o valor
    ReflectionTestUtils.setField(emailFactory, "emailFrom", EMAIL_FROM);
  }

  @Test
  void createDomain_ShouldCreateObjectEmail() {
    // Act
    Email result = emailFactory.createDomain(ID, OWNER, EMAIL_TO, SUBJECT, TEXT, SEND_DATE_EMAIL);

    // Assert
    assertNotNull(result);
    assertEquals(ID, result.getId());
    assertEquals(OWNER, result.getOwnerRef());
    assertEquals(EMAIL_FROM, result.getEmailFrom());
    assertEquals(EMAIL_TO, result.getEmailTo());
    assertEquals(SUBJECT, result.getSubject());
    assertEquals(TEXT, result.getText());
    assertEquals(SEND_DATE_EMAIL, result.getSendDateEmail());
  }

}