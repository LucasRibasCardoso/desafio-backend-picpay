package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import com.picpaydesafio.demopicpaydesafio.application.services.imp.UserServiceImp;
import com.picpaydesafio.demopicpaydesafio.domain.factories.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionFactoryImpl implements TransactionFactory {

  private final UserServiceImp userService;

  @Override
  public Transaction createDomain(TransactionRequestDTO request) {
    User sender = userService.findUserById(request.senderId());
    User receiver = userService.findUserById(request.receiverId());

    return new Transaction(null, request.amount(), sender, receiver, LocalDateTime.now());
  }

}
