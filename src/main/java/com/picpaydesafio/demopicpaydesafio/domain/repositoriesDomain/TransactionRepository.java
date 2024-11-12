package com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import java.util.List;

public interface  TransactionRepository {
  Transaction save(Transaction transaction);
  List<Transaction> findAll();
}
