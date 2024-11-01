package com.picpaydesafio.demopicpaydesafio.domain.transction;

import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DefaultTransactionFactory implements TransactionFactory {

  @Override
  public Transaction createTransaction(User sender, User receiver, BigDecimal value) {
    Transaction transaction = new Transaction();

    transaction.setSender(sender);
    transaction.setReceiver(receiver);
    transaction.setAmount(value);
    transaction.setTimestamp(LocalDateTime.now());
    return transaction;
  }

}
