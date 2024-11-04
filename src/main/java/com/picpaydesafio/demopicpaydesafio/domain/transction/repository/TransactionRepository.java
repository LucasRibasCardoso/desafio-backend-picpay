package com.picpaydesafio.demopicpaydesafio.domain.transction.repository;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import java.util.Optional;

public interface TransactionRepository {
  Transaction save(Transaction transaction);
  Optional<Transaction> findById(Long id);
}
