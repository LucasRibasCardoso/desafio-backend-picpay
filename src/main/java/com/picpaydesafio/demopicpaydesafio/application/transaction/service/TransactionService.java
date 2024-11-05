package com.picpaydesafio.demopicpaydesafio.application.transaction.service;

import com.picpaydesafio.demopicpaydesafio.application.email.service.EmailService;
import com.picpaydesafio.demopicpaydesafio.application.transaction.validators.TransactionValidator;
import com.picpaydesafio.demopicpaydesafio.application.user.service.UserService;
import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.transction.repository.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.transaction.mapper.TransactionDTOMapper;
import java.util.List;
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
  private final TransactionDTOMapper mapper;

  private final UserService userService;
  private final EmailService emailService;

    /**
   * Creates a new transaction and processes it, updating the balances of the sender and receiver, saving the transaction, and sending a transaction email.
   *
   * @param request the {@link TransactionRequestDTO} containing the details of the transaction to be created
   * @return the saved {@link Transaction} object
   */
  public Transaction createTransaction(TransactionRequestDTO request) {
    Transaction transaction = transactionFactory.createDomain(request);
    validator.validate(transaction);

    Transaction transactionProcessed = transaction.process(transactionFactory);
    userService.saveUsersWithNewBalances(transactionProcessed.getSender(), transactionProcessed.getReceiver());

    Transaction savedTransaction = transactionRepository.save(transactionProcessed);
    emailService.sendTransactionEmail(transactionProcessed);

    return savedTransaction;
  }


  /**
   * Retrieves a list of all transactions.
   *
   * @return a list of {@link TransactionResponseDTO} representing all transactions
   */
  public List<TransactionResponseDTO> getAllTransactions() {
    return transactionRepository.findAll().stream()
        .map(mapper::toDTO)
        .toList();
  }

}
