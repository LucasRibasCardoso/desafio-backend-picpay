package com.picpaydesafio.demopicpaydesafio.application.email.service;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;

public interface EmailService {

  void sendTransactionEmail(Transaction transaction);
}
