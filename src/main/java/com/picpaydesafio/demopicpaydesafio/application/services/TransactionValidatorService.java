package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidatorService {

  public void validate(Transaction transaction) {
    if (transaction.getSender().equals(transaction.getReceiver())) {
      throw new UnauthorizedTransactionException("Usuário não pode realizar transação consigo mesmo.");
    }

    if (transaction.getSender().isMerchant()) {
      throw new UnauthorizedTransactionException("Lojistas não podem realizar transações.");
    }
  }

}
