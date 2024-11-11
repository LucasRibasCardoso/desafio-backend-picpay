package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;

public interface EmailSendingService {

  void sendEmail(Transaction transaction);
}
