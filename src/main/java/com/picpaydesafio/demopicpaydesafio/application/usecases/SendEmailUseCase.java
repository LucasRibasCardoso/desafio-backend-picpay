package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.services.interfaces.EmailSendingService;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailUseCase {

  private final EmailSendingService emailService;

  public void execute(Transaction transaction) {
    emailService.sendEmail(transaction);
  }
}
