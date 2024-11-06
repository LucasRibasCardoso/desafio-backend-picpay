package com.picpaydesafio.demopicpaydesafio.infrastructure.mappers;

import com.picpaydesafio.demopicpaydesafio.domain.models.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {

  public SimpleMailMessage toMailMessage(Email email) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(email.getEmailFrom());
    message.setTo(email.getEmailTo());
    message.setSubject(email.getSubject());
    message.setText(email.getText());
    return message;
  }

}
