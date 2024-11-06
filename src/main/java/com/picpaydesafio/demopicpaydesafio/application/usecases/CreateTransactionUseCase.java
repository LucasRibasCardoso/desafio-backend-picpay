package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.services.TransactionValidatorService;
import com.picpaydesafio.demopicpaydesafio.domain.factories.interfaces.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTransactionUseCase {

  private final TransactionFactory transactionFactory;
  private final TransactionValidatorService validator;
  private final TransactionRepository transactionRepository;
  private final UpdateUserBalancesAfterTransactionUseCase updateUserBalanceUseCase;

  public Transaction execute(TransactionRequestDTO request) {
    Transaction transaction = transactionFactory.createDomain(request);
    validator.validate(transaction);

    Transaction processedTransaction = transaction.process();
    updateUserBalanceUseCase.execute(processedTransaction.getSender(), processedTransaction.getReceiver());

    return transactionRepository.save(processedTransaction);

  }
}
