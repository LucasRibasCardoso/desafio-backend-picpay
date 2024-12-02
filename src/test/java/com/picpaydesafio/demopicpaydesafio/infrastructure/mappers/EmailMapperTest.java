package com.picpaydesafio.demopicpaydesafio.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.*;

import com.picpaydesafio.demopicpaydesafio.domain.models.Email;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;

class EmailMapperTest {

  // Email
  public static final long ID = 1L;
  public static final String OWNER = "teste";
  public static final String EMAIL_FROM = "teste_from@example.com";
  public static final String EMAIL_TO = "teste_to@example.com";
  public static final String SUBJECT = "subject";
  public static final String EMAIL_BODY = "email body";
  public static final LocalDateTime SEND_DATE_EMAIL = LocalDateTime.now();

  private EmailMapper emailMapper;
  private Email mockEmail;

  @BeforeEach
  void setUp() {
    emailMapper = new EmailMapper();
    mockEmail = new Email(ID, OWNER, EMAIL_FROM, EMAIL_TO, SUBJECT, EMAIL_BODY, SEND_DATE_EMAIL);
  }

  @Test
  void toMailMessage_ShouldReturnObjectEmail() {
    // Act
    SimpleMailMessage result = emailMapper.toMailMessage(mockEmail);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(EMAIL_FROM, result.getFrom()),
        () -> assertEquals(EMAIL_TO, result.getTo()[0]),
        () -> assertEquals(SUBJECT, result.getSubject()),
        () -> assertEquals(EMAIL_BODY, result.getText())
    );
  }

}