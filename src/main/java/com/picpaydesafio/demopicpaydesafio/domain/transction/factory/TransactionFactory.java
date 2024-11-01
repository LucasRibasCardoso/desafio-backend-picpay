package com.picpaydesafio.demopicpaydesafio.domain.transction.factory;

import com.picpaydesafio.demopicpaydesafio.domain.transction.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import java.math.BigDecimal;

public interface TransactionFactory {

  Transaction createTransaction(User sender, User receiver, BigDecimal value);
}
