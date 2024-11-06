package com.picpaydesafio.demopicpaydesafio.domain.transction.factory;

import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionFactoryImpl implements TransactionFactory {

  private final UserService userService;

  @Override
  public Transaction createDomain(TransactionRequestDTO request) {
    User sender = userService.findUserById(request.senderId());
    User receiver = userService.findUserById(request.receiverId());

    return new Transaction(null, request.amount(), sender, receiver, LocalDateTime.now());
  }

}
