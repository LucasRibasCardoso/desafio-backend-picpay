package com.picpaydesafio.demopicpaydesafio.domain.factories;

import com.picpaydesafio.demopicpaydesafio.domain.models.Email;
import java.time.LocalDateTime;

public interface EmailFactory {

  Email createDomain(
      Long id, String ownerRef, String emailTo, String subject, String text, LocalDateTime sendDateEmail
  );

}
