package com.picpaydesafio.demopicpaydesafio.application.transaction.validators;

import com.picpaydesafio.demopicpaydesafio.application.transaction.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {

  public void validate(Transaction transaction) {
    if (transaction.getSender().equals(transaction.getReceiver())) {
      throw new UnauthorizedTransactionException("Usuário não pode realizar transação consigo mesmo.");
    }

    if (transaction.getSender().isMerchant()) {
      throw new UnauthorizedTransactionException("Lojistas não podem realizar transações.");
    }
  }

}
