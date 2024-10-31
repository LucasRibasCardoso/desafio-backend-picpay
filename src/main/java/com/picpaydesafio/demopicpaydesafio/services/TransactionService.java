package com.picpaydesafio.demopicpaydesafio.services;

import com.picpaydesafio.demopicpaydesafio.domain.transction.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import com.picpaydesafio.demopicpaydesafio.dtos.TransactionDTO;
import com.picpaydesafio.demopicpaydesafio.repositories.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionRepository repository;

  @Autowired
  private RestTemplate restTemplate;

  public void createTransction(TransactionDTO transaction) throws Exception {
    User sender = this.userService.findUserById(transaction.senderId());
    User receiver = this.userService.findUserById(transaction.receiverId());

    userService.validateTransaction(sender, transaction.value());

    boolean isAuthorized = this.authorizedTransaction(sender, transaction.value());
    if (!isAuthorized) {
      throw new Exception("Transação não autorizada.");
    }

    Transaction newTransaction = new Transaction();
    newTransaction.setAmount(transaction.value());
    newTransaction.setSender(sender);
    newTransaction.setReceiver(receiver);
    newTransaction.setTimestamp(LocalDateTime.now());

    sender.setBalance(sender.getBalance().subtract(transaction.value()));
    receiver.setBalance(receiver.getBalance().add(transaction.value()));

    this.repository.save(newTransaction);
    userService.saveUser(sender);
    userService.saveUser(receiver);
  }

  public boolean authorizedTransaction(User sender, BigDecimal value) throws Exception {
    ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
      String message = (String) authorizationResponse.getBody().get("message");
      return "Autorizado".equals(message);
    }
    else {
      return false;
    }
  }
}