package com.picpaydesafio.demopicpaydesafio.services;

import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.DefaultTransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.transction.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import com.picpaydesafio.demopicpaydesafio.dtos.TransactionDTO;
import com.picpaydesafio.demopicpaydesafio.repositories.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UnauthorizedTransaction;
import com.picpaydesafio.demopicpaydesafio.services.notification.NotificationService;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

  private final UserService userService;
  private final TransactionRepository repository;
  private final NotificationService notificationService;
  private final RestTemplate restTemplate;
  private TransactionFactory transactionFactor;

  @Autowired
  public TransactionService(
      NotificationService notificationService,
      TransactionRepository repository,
      RestTemplate restTemplate,
      DefaultTransactionFactory transactionFactor,
      UserService userService
  ) {
    this.notificationService = notificationService;
    this.repository = repository;
    this.restTemplate = restTemplate;
    this.transactionFactor = transactionFactor;
    this.userService = userService;
  }

  @Transactional
  public Transaction createTransction(TransactionDTO transaction) {
    User sender = this.userService.findUserById(transaction.senderId());
    User receiver = this.userService.findUserById(transaction.receiverId());

    userService.validateTransaction(sender, transaction.value());
    validadeIfSenderIsAuthorized(sender, transaction);

    Transaction newTransaction = transactionFactor.createTransaction(sender, receiver, transaction.value());

    sender.setBalance(sender.getBalance().subtract(transaction.value()));
    receiver.setBalance(receiver.getBalance().add(transaction.value()));

    this.repository.save(newTransaction);
    userService.saveUser(sender);
    userService.saveUser(receiver);

    this.notificationService.sendNotification(sender, "Transação realizada com sucesso.");
    this.notificationService.sendNotification(receiver, "Transação recebida com sucesso.");

    return newTransaction;
  }

  public boolean authorizedTransaction(User sender, BigDecimal value) {
    ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

     if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
       String message = (String) authorizationResponse.getBody().get("status");
       return "success".equals(message);
     }
     else {
       return false;
     }
  }

  private void validadeIfSenderIsAuthorized(User sender, TransactionDTO transaction) {
    boolean isAuthorized = this.authorizedTransaction(sender, transaction.value());
    if (!isAuthorized) {
      throw new UnauthorizedTransaction("Transação não autorizada.");
    }
  }
}
