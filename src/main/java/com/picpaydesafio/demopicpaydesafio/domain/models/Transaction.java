package com.picpaydesafio.demopicpaydesafio.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Value;


@Value
public class Transaction {

  Long id;
  BigDecimal amount;
  User sender;
  User receiver;
  LocalDateTime timestamp;

  public Transaction process() {
    User updatedSender = this.sender.debit(this.amount);
    User updatedReceiver = this.receiver.credit(this.amount);

    return new Transaction(this.id, this.amount, updatedSender, updatedReceiver, this.timestamp);
  }
}
