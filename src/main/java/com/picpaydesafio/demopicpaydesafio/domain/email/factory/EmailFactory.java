package com.picpaydesafio.demopicpaydesafio.domain.email.factory;

import com.picpaydesafio.demopicpaydesafio.domain.email.model.Email;
import java.time.LocalDateTime;

public interface EmailFactory {

  Email createDomain(
      Long id, String ownerRef, String emailTo, String subject, String text, LocalDateTime sendDateEmail
  );

}
