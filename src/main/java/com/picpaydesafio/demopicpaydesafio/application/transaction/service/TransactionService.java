package com.picpaydesafio.demopicpaydesafio.application.transaction.service;

import com.picpaydesafio.demopicpaydesafio.application.email.service.EmailService;
import com.picpaydesafio.demopicpaydesafio.application.transaction.validators.TransactionValidator;
import com.picpaydesafio.demopicpaydesafio.application.user.service.UserService;
import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.transction.repository.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionFactory transactionFactory;
  private final TransactionRepository transactionRepository;
  private final TransactionValidator validator;
  private final UserService userService;

  private final EmailService emailService;


  public Transaction createTransaction(TransactionRequestDTO request) {
    Transaction transaction = transactionFactory.createDomain(request);
    validator.validate(transaction);

    Transaction transactionProcessed = transaction.process(transactionFactory);
    userService.updateBalances(transactionProcessed.getSender(), transactionProcessed.getReceiver());

    Transaction savedTransaction = transactionRepository.save(transactionProcessed);
    emailService.sendTransactionEmail(transactionProcessed);

    return savedTransaction;
  }

}
