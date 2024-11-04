package com.picpaydesafio.demopicpaydesafio.domain.email.factory;

import com.picpaydesafio.demopicpaydesafio.domain.email.model.Email;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailFactoryImp implements EmailFactory {

  @Value("${spring.mail.username}")
  private String emailFrom;

  @Override
  public Email createDomain(
      Long id, String ownerRef, String emailTo, String subject, String text, LocalDateTime sendDateEmail
  ) {
    return new Email(id, ownerRef, emailFrom, emailTo, subject, text, sendDateEmail);
  }

}
