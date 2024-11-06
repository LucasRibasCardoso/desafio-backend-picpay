package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class ValidateTransactionUseCase {

  public void validate(Transaction transaction) {
    validateTransactionForYourself(transaction.getSender(), transaction.getReceiver());
    validateTransactionSenderType(transaction.getSender());
  }

  private void validateTransactionForYourself(User sender, User receiver) {
    if (sender.equals(receiver)) {
      throw new UnauthorizedTransactionException("Usuário não pode realizar transação consigo mesmo.");
    }
  }

  private void validateTransactionSenderType(User user) {
    if (user.isMerchant()) {
      throw new UnauthorizedTransactionException("Lojistas não podem realizar transações.");
    }
  }

}
