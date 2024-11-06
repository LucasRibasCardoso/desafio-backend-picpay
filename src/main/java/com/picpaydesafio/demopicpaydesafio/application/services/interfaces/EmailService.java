package com.picpaydesafio.demopicpaydesafio.application.services.interfaces;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;

public interface EmailService {

  void sendEmail(Transaction transaction);
}
