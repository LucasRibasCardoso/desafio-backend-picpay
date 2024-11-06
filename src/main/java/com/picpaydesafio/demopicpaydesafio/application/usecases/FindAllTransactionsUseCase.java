package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces.TransactionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAllTransactionsUseCase {

  private final TransactionRepository transactionRepository;

  public List<Transaction> execute() {
    return transactionRepository.findAll();
  }
}
