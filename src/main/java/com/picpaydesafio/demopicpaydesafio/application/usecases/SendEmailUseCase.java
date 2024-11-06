package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.services.interfaces.EmailService;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailUseCase {

  private final EmailService emailService;

  public void execute(Transaction transaction) {
    emailService.sendEmail(transaction);
  }
}
