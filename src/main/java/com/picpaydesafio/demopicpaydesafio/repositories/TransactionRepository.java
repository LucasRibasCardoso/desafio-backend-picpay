package com.picpaydesafio.demopicpaydesafio.repositories;

import com.picpaydesafio.demopicpaydesafio.domain.transction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
