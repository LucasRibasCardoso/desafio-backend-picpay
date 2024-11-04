package com.picpaydesafio.demopicpaydesafio.domain.transction.model;

import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.TransactionFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Value;



/**
 * Represents a financial transaction between two users.
 *
 * The `Transaction` class encapsulates the details of a financial transaction, including the amount,
 * the sender, the receiver, and the timestamp of the transaction. It also provides a `process()` method
 * that updates the balances of the sender and receiver, and returns a new `Transaction` instance with
 * the updated information.
 *
 * @param id The unique identifier of the transaction.
 * @param amount The amount of the transaction.
 * @param sender The user who is sending the funds.
 * @param receiver The user who is receiving the funds.
 * @param timestamp The timestamp of the transaction.
 */
@Value
public class Transaction {

  Long id;
  BigDecimal amount;
  User sender;
  User receiver;
  LocalDateTime timestamp;


  public Transaction process(TransactionFactory transactionFactory) {
    User updatedSender = this.sender.debit(this.amount);
    User updateReceiver = this.receiver.credit(this.amount);

    return transactionFactory.craeteDomain(id, amount, updatedSender, updateReceiver, timestamp);
  }
}
