package com.picpaydesafio.demopicpaydesafio.application.services.interfaces;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;

public interface EmailService {

  void sendEmail(Transaction transaction);
}
