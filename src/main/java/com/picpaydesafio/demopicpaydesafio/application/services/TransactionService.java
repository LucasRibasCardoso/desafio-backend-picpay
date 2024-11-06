package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.application.usecases.CreateTransactionUseCase;
import com.picpaydesafio.demopicpaydesafio.application.usecases.FindAllTransactionsUseCase;
import com.picpaydesafio.demopicpaydesafio.application.usecases.SendEmailUseCase;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.mapper.TransactionMapper;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionMapper transactionMapper;

  private final CreateTransactionUseCase createTransactionUseCase;
  private final FindAllTransactionsUseCase findAllTransactionsUseCase;
  private final SendEmailUseCase sendEmailUseCase;

  @Transactional
  public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {
    Transaction transaction = createTransactionUseCase.execute(request);

    sendEmailUseCase.execute(transaction);

    return transactionMapper.toResponseDTO(transaction);
  }

  public List<TransactionResponseDTO> getAllTransactions() {
    List<Transaction> transactions = findAllTransactionsUseCase.execute();
    return transactions.stream().map(transactionMapper::toResponseDTO).toList();
  }

}
