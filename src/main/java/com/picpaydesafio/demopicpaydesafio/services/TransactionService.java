package com.picpaydesafio.demopicpaydesafio.services;

import com.picpaydesafio.demopicpaydesafio.domain.transction.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import com.picpaydesafio.demopicpaydesafio.dtos.TransactionDTO;
import com.picpaydesafio.demopicpaydesafio.repositories.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UnauthorizedTransaction;
import com.picpaydesafio.demopicpaydesafio.services.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

  private final UserService userService;
  private final TransactionRepository repository;
  private final NotificationService notificationService;
  private final RestTemplate restTemplate;
  private final TransactionFactory transactionFactory;

  @Autowired
  public TransactionService(
      UserService userService,
      TransactionRepository repository,
      NotificationService notificationService,
      RestTemplate restTemplate,
      TransactionFactory transactionFactory
  ) {
    this.userService = userService;
    this.repository = repository;
    this.notificationService = notificationService;
    this.restTemplate = restTemplate;
    this.transactionFactory = transactionFactory;
  }

  @Transactional
  public Transaction createTransaction(TransactionDTO transactionDTO) {
    User sender = userService.findUserById(transactionDTO.senderId());
    User receiver = userService.findUserById(transactionDTO.receiverId());

    validateTransaction(sender, transactionDTO.value());
    checkAuthorization(sender, transactionDTO.value());

    Transaction newTransaction = transactionFactory.createTransaction(sender, receiver, transactionDTO.value());
    processTransfer(sender, receiver, transactionDTO.value());

    persistTransactionAndUsers(newTransaction, sender, receiver);
    notifyUsers(sender, receiver);

    return newTransaction;
  }

  private void validateTransaction(User sender, BigDecimal amount) {
    userService.validateTransaction(sender, amount);
  }

  private void checkAuthorization(User sender, BigDecimal amount) {
    ResponseEntity<Map> response = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
    if (!(response.getStatusCode() == HttpStatus.OK)) {
      throw new UnauthorizedTransaction("Transação não autorizada.");

    }
  }

  private void processTransfer(User sender, User receiver, BigDecimal amount) {
    sender.setBalance(sender.getBalance().subtract(amount));
    receiver.setBalance(receiver.getBalance().add(amount));
  }

  private void persistTransactionAndUsers(Transaction transaction, User sender, User receiver) {
    repository.save(transaction);
    userService.saveUser(sender);
    userService.saveUser(receiver);
  }

  private void notifyUsers(User sender, User receiver) {
    notificationService.sendNotification(sender, "Transação realizada com sucesso.");
    notificationService.sendNotification(receiver, "Transação recebida com sucesso.");
  }
}
