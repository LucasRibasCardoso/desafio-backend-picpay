package com.picpaydesafio.demopicpaydesafio.domain.transction.repository;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import java.util.List;

public interface TransactionRepository {
  Transaction save(Transaction transaction);
  List<Transaction> findAll();
}
